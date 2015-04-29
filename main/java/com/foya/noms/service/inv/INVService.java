package com.foya.noms.service.inv;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbInvAsset;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvLot;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSiteTxn;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INVDao;
import com.foya.noms.service.BaseService;

import org.springframework.transaction.annotation.Transactional;

/**
 * 供站點呼叫之Service(順邏輯,還會再改程式碼)
 */
@Service
public class INVService extends BaseService {
	
	@Autowired
	private INVDao invDao;
	
	/**
	 * 站點執行安裝動作時會呼叫
	 * @param actType :String (P:一般驗收,T:統包驗收)	
	 * @param txnNo :String 拆裝單號
	 * --@param txnType :int 作業類型(1:安裝 2:拆下)
	 * @param locationId :String 站點代碼
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param matNo :String 料號
	 * @param qty :int 安裝數量
	 * @param faNo :String 資產編號
	 * @param srlNo :long 序號控管物件檔編號
	 * --@param poNo :String PO單號
	 * --@param poLineNbr :int PO單明細號碼
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人(TB_INV_ASSET)
	 * @return 是否成功 :Boolean
	 */
	@Transactional
	public boolean setup(String actType,String txnNo,String locationId,String siteId,String orderNo,String matNo,int qty,String faNo,long srlNo,String userId,String keepUserId)
		throws NomsException{
		
	  //宣告變數
	  boolean ifSuccess = true;		
	  int txtType = 1;//安裝
	  //int invAssetCount = 0;//查詢是否已存在TB_INV_ASSET
	  String assetType = "M";//物料
	  TbInvSrl tbInvSrl = null;
	  List<TbInvOnhand> tbInvOnhandList = null;
	  TbInvOnhand tbinvOnhand = null;
	  TbInvMaterial tbInvMaterial = null;
	  String ctrlType = null;
	  boolean isAsset = false;
	  List<TbInvAsset> invAssetList = null;//資產檔資料列	
		
	  try{
		  
		//1.新增站點交易檔
		invDao.insertInvSiteTxn(actType,txnNo,txtType,siteId,orderNo,matNo,qty,faNo,srlNo,userId);												
		
		//2.更新TB_INV_ONHAND,先扣發料的onhand_qty再扣拆下的onhand_qty
		tbInvOnhandList=invDao.selectInvOnhandOrderbyTxnType(matNo,orderNo,srlNo);//查詢發料收料TXN
		if(tbInvOnhandList!=null && tbInvOnhandList.size()>=1){
			Integer tmpLeftQty = qty;//安裝剩餘qty

			for(TbInvOnhand tmpTbinvOnhand:tbInvOnhandList){
				int sendOnhandQty = tmpTbinvOnhand.getOnhand_qty();//在途數量
				if(tmpLeftQty > sendOnhandQty){//在途數量不夠扣
					
					tmpTbinvOnhand.setOnhand_qty(0);//在途歸零
					tmpTbinvOnhand.setSite_id(siteId);//更新站台編號
					tmpTbinvOnhand.setMd_time(new Date());
					tmpTbinvOnhand.setMd_user(getLoginUser().getUsername());
					invDao.updateInvOnHand(tmpTbinvOnhand);
					
					tmpLeftQty = tmpLeftQty-sendOnhandQty;//重設安裝剩餘qty
				}else{
					
					tmpTbinvOnhand.setOnhand_qty(sendOnhandQty-tmpLeftQty);//扣掉安裝剩餘qty
					tmpTbinvOnhand.setSite_id(siteId);//更新站台編號
					tmpTbinvOnhand.setMd_time(new Date());
					tmpTbinvOnhand.setMd_user(getLoginUser().getUsername());
					invDao.updateInvOnHand(tmpTbinvOnhand);
					
					break;//扣完收工
				}
				
			}//end for
			
		}//end if(tbInvOnhandList!=null && tbInvOnhandList.size()>=1) 

		//透過傳入的SRL_NO去 tb_inv_srl找出FA_NO
		tbInvSrl=invDao.selectInvSrl(srlNo);
		if(tbInvSrl!=null){
			faNo=tbInvSrl.getFa_no();
		}
		
		//查詢TB_INV_MATERIAL (by mat_no)
		tbInvMaterial = invDao.getMaterialByMatNo(matNo);
		ctrlType = tbInvMaterial.getCtrl_type();
		isAsset = tbInvMaterial.getIs_asset();	
		
		//2015/4/10 Kevin Start=================================
		//判斷是否資產檔已存在
		invAssetList = invDao.selectInvAsset(siteId, matNo, assetType,srlNo);
		if(invAssetList.size()>0){	
			//4.已存在-更新TB_INV_ASSET資料(原始數量+本次安裝)
			invDao.updateInvAsset_Setup(locationId,srlNo,faNo,qty,siteId,assetType,matNo,new Date(),orderNo,qty,userId,keepUserId);
		}else{
			//4.不存在-新增一筆TB_INV_ASSET
			invDao.insertInvAsset(locationId,siteId,assetType,matNo,srlNo,faNo,qty,null,null,userId,keepUserId,new Date(),orderNo,qty,null);
		}
		//2015/4/10 Kevin =================================End

		//5.如果是安裝,則更新[序號控管物料檔](TB_INV_SRL)的site_id(有値),wh_id(無值)
		invDao.updateInvSrl(txtType,siteId,null,userId,srlNo);
			 
		 
		
		return ifSuccess;
	  } catch (Exception e) {
		log.error(ExceptionUtils.getFullStackTrace(e));
		throw new NomsException("安裝錯誤訊息:"+e.getMessage());
		
	  }
	  
	}
	

	
	



	/**
	 * 站點執行拆下動作時會呼叫
	 * @param actType :String (P:一般驗收,T:統包驗收)	
	 * @param txnNo :String 拆裝單號
	 * --@param txnType :int 作業類型(1:安裝 2:拆下)
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param matNo :String 料號
	 * --@param matStatus :int 物料狀態
	 * @param qty :int 拆下數量
	 * @param faNo :String 資產編號
	 * @param srlNo :long 序號控管物件檔編號
	 * --@param poNo :String PO單號
	 * --@param poLineNbr :int PO單明細號碼
	 * @param unloadQty :int 卸下數量	
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	@Transactional
	public boolean unload(String actType,String txnNo,String siteId,String orderNo,String matNo,int qty,String faNo,long srlNo,int unloadQty,String userId)
			throws NomsException{
		
	  //宣告變數
	  boolean ifSuccess = true;
      String assetType="M";//TB_INV_ASSET.資產類型(M:物料,W:作業)
	  int onhandTxnType = 3;//卸下動作時要去新增TB_INV_ONHAND[txn_type=3(拆下)]
      //String whId = null;//卸下動作新增TB_INV_ONHAND時,wh_id給null値
	  int txtType = 2;//卸下
	  int matStatus = 1;//(一定是1:可用品)
	  List<TbInvAsset> invAssetList = null;//資產檔資料列
	  TbInvMaterial tbInvMaterial = null;
	  String ctrlType = null;
	  boolean isAsset = false;

	  
	  try{		
		
		//1.新增站點交易檔
		invDao.insertInvSiteTxn(actType,txnNo,txtType,siteId,orderNo,matNo,qty,faNo,srlNo,userId);												
		//2.新增在途檔
		invDao.insertInvOnHand(txnNo,onhandTxnType,siteId,orderNo,matNo,matStatus,qty,faNo,srlNo,userId);	
		
		//查詢TB_INV_MATERIAL (by mat_no)
		tbInvMaterial = invDao.getMaterialByMatNo(matNo);
		ctrlType = tbInvMaterial.getCtrl_type();
		isAsset = tbInvMaterial.getIs_asset();
		
		//3.更新資產檔的qty,unload_qty,scrp_date,scrp_order_no
		invDao.updateInvAsset_Unload(siteId,assetType,matNo,unloadQty,new Date(),orderNo,userId,srlNo);
				 
		//4.如果是卸下,則更新[序號控管物料檔](TB_INV_SRL)的site_id(ON_HAND),wh_id(無値)			
		invDao.updateInvSrl(txtType,"ON_HAND",null,userId,srlNo); 
					
		return ifSuccess;
	  } catch (Exception e) {
		log.error(ExceptionUtils.getFullStackTrace(e));
		throw new NomsException("拆下錯誤訊息:"+e.getMessage());
	  }
	  
	}
	
	
	/**增加20150105
	 * 站點執行工單完工動作時會呼叫
	 * @param actType :String (P:一般驗收,T:統包驗收)	
	 * @param orderNo: String 工單號碼
	 * @param locationId :String 站點代碼
	 * @param siteId :String 基站代碼	 
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人(TB_INV_ASSET)
	 * @param apDate :Date 驗收日期(TB_INV_ASSET)
	 * @param apAmount :BigDecimal 驗收金額(TB_INV_ASSET,TB_INV_TO_ERP_MAT)	
	 * @param rcvNo :String 驗收單號 (TB_INV_TO_ERP_MAT)	
	 * @param osId :String 派工單號(TB_INV_TO_ERP_MAT)
	 * @return 是否成功 :Boolean
	 */
	@Transactional
	public boolean completion(String actType,String orderNo,String locationId,String siteId,String userId,String keepUserId,
			Date apDate,BigDecimal apAmount,String rcvNo,String osId) throws NomsException{
		//宣告變數
		boolean ifSuccess = true;
		int txnType = 1;//安裝
		try{
			//Step1.先做安裝		
			ifSuccess = this.innerCompletion(actType, txnType, orderNo, locationId, siteId, userId, keepUserId, apDate, apAmount, rcvNo, osId);
		
			//Step2.再進行拆下
			txnType = 2;//拆下
			ifSuccess = this.innerCompletion(actType,txnType,orderNo,locationId,siteId,userId,keepUserId,apDate,apAmount,rcvNo,osId);
		
			return ifSuccess;
		
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("站點執行工單完工-錯誤訊息:"+e.getMessage());
		}
		
	}

	/**改private-20150105
	 * 站點執行工單完工動作時會呼叫
	 * @param actType :String (P:一般驗收,T:統包驗收)
	 * @param txnType :int (1:安裝, 2:拆下)
	 * @param orderNo: String 工單號碼
	 * @param locationId :String 站點代碼
	 * @param siteId :String 基站代碼
	 ---* @param txnNo :String 拆裝單號	//20150105
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人(TB_INV_ASSET)
	 * @param apDate :Date 驗收日期(TB_INV_ASSET)
	 * @param apAmount :BigDecimal 驗收金額(TB_INV_ASSET,TB_INV_TO_ERP_MAT)	
	 * @param rcvNo :String 驗收單號 (TB_INV_TO_ERP_MAT)	
	 * @param osId :String 派工單號(TB_INV_TO_ERP_MAT)
	 * @return 是否成功 :Boolean
	 */
	@Transactional
	private boolean innerCompletion(String actType,int txnType,String orderNo,String locationId,String siteId,String userId,String keepUserId,
			Date apDate,BigDecimal apAmount,String rcvNo,String osId) throws NomsException{
		
	  //宣告變數
	  boolean ifSuccess = true;
	  List<TbInvSiteTxn> invSiteTxnList = null;//select TB_INV_SITE_TXN 回傳的資料列
	  List<TbInvTxn> invTxnList = null;//select TB_INV_TXN 回傳的資料列
	  List<TbInvLot> invLotList = null; //select TB_INV_LOT 回傳的資料列
	  TbInvSrl invSrl = null; //select TB_INV_SRL by PKY 回傳的物件
	  List<TbInvWarehouse> invWhList = null; //select TB_INV_WAREHOUSE 回傳的資料列		
	  int invAssetCount = 0;//select TB_INV_ASSET 筆數(用來判斷是否已存在TB_INV_ASSET)
	  String assetType="M";//TB_INV_ASSET.資產類型(M:物料,W:作業)
	  String replace = null; //目前保管地(TB_INV_TO_ERP_LOC用)
	  String source = null;//來源保管地(TB_INV_TO_ERP_LOC用)
	  String venSn = null;//廠商序號(從查詢TB_INV_SRL取得)
      String totalWhId = null;//總倉代碼(統包驗收會用到)
      String mroWhId = null;//發料倉
	  String rtnWhId = null;//收料倉
	  
		
	  try{
		  
		//1.查詢[站點交易檔]中屬於安裝或拆下的資料
		invSiteTxnList = invDao.selectInvSiteTxn(siteId, orderNo, txnType);
		for(TbInvSiteTxn theInvSiteTxn:invSiteTxnList){//站點交易檔list
			//宣告變數
			String txnNo = theInvSiteTxn.getTxn_no();//拆裝單號-20150105
			String faNo  = theInvSiteTxn.getFa_no();
			String matNo = theInvSiteTxn.getMat_no();	
			Integer qty  = theInvSiteTxn.getQty();
			Long srlNo   = theInvSiteTxn.getSrl_no();
			//String whId  = theInvSiteTxn.getWh_id();	
			List<String> lotFaNoList = new ArrayList<String>();//list for TB_INV_LOT.fa_no
			TbInvMaterial tbInvMaterial = null;
			String ctrlType = null;
			boolean isAsset = false;
			
			//2.查詢TB_INV_MATERIAL (by mat_no)
			tbInvMaterial = invDao.getMaterialByMatNo(matNo);
			ctrlType = tbInvMaterial.getCtrl_type();
			isAsset = tbInvMaterial.getIs_asset();	
			
			//3-1.查詢 發料倉
			int tmpInvTxnType = 1;
			List<TbInvTxn> tmpInvTxnList = invDao.getRtnWhIdBySiteIdAndOrderNo(tmpInvTxnType, siteId, orderNo, matNo, faNo);
			if(tmpInvTxnList!=null && tmpInvTxnList.size()>0){
				TbInvTxn invTxn = tmpInvTxnList.get(0);
				mroWhId = invTxn .getWh_id();
			}
			//3-2.查詢 收料倉
			tmpInvTxnType = 2;
			tmpInvTxnList = invDao.getRtnWhIdBySiteIdAndOrderNo(tmpInvTxnType, siteId, orderNo, matNo, faNo);
			if(tmpInvTxnList!=null && tmpInvTxnList.size()>0){
				TbInvTxn invTxn = tmpInvTxnList.get(0);
				rtnWhId = invTxn .getWh_id();
			}
			if(ctrlType.equals("V") && (isAsset==true)){//數量件
			
			
			  if(txnType==1){//安裝				
				
				//4.查詢[批號控管物料檔](TB_INV_LOT)
				invLotList = invDao.selectInvLot_Setup(matNo);
				
				Integer leftQty = 0;//剩餘qty
				boolean goFlag = true;
				for(TbInvLot theInvLot:invLotList){//批號控管物料檔list
					String theFaNo = theInvLot.getFa_no();
					int stdQty = theInvLot.getStd_qty();
					
					if(goFlag){
						if(leftQty==0){
							if(qty<=stdQty){
								//5.如果是安裝,則更新[批號控管物料檔](TB_INV_LOT)的安裝數(+),可用數(-)
								invDao.updateInvLot_SetUpQty_Setup(qty, theFaNo, matNo, userId);
								leftQty=0;
								goFlag = false;
							}else{//qty>stdQty
								
								//5.如果是安裝,則更新[批號控管物料檔](TB_INV_LOT)的安裝數(+),可用數(-)
								invDao.updateInvLot_SetUpQty_Setup(stdQty, theFaNo, matNo, userId);
								leftQty = (new BigDecimal(qty).subtract(new BigDecimal(stdQty))).intValue();
							}//end if
						
						}else{//leftQty>0
							if(leftQty<=stdQty){
								//5.如果是安裝,則更新[批號控管物料檔](TB_INV_LOT)的安裝數(+),可用數(-)
								invDao.updateInvLot_SetUpQty_Setup(leftQty, theFaNo, matNo, userId);
								leftQty=0;
								goFlag = false;
							}else{//leftQty>stdQty
								//5.如果是安裝,則更新[批號控管物料檔](TB_INV_LOT)的安裝數(+),可用數(-)
								invDao.updateInvLot_SetUpQty_Setup(stdQty, theFaNo, matNo, userId);
								leftQty = (new BigDecimal(leftQty).subtract(new BigDecimal(stdQty))).intValue();
							}
						}//end if (leftQty==0)
					}//end if goFLag
					
				}//end for
				
				
			  }else if(txnType==2){
				//4.查詢[批號控管物料檔](TB_INV_LOT)
				invLotList = invDao.selectInvLot_Unload(matNo);
				
				Integer leftQty = 0;//剩餘qty
				boolean goFlag = true;
				for(TbInvLot theInvLot:invLotList){//批號控管物料檔list
					String theFaNo = theInvLot.getFa_no();
					int stdQty = theInvLot.getStd_qty();
					int buyingQty = theInvLot.getBuying_qty();
					int theQty = (new BigDecimal(buyingQty).subtract(new BigDecimal(stdQty))).intValue();
					
					if(goFlag){
						if(leftQty==0){
							if(qty<=theQty){
								//5.如果是卸下,則更新[批號控管物料檔](TB_INV_LOT)的可用數(+)
								invDao.updateInvLot_StdQty_Unload(qty, theFaNo, matNo, userId);
								leftQty=0;
								goFlag = false;
							}else{//qty>theQty
								
								//5.如果是卸下,則更新[批號控管物料檔](TB_INV_LOT)的可用數(+)
								invDao.updateInvLot_StdQty_Unload(theQty, theFaNo, matNo, userId);
								leftQty = (new BigDecimal(qty).subtract(new BigDecimal(theQty))).intValue();
							}//end if
						
						}else{//leftQty>0
							if(leftQty<=theQty){
								//5.如果是卸下,則更新[批號控管物料檔](TB_INV_LOT)的可用數(+)
								invDao.updateInvLot_StdQty_Unload(leftQty, theFaNo, matNo, userId);
								leftQty=0;
								goFlag = false;
							}else{//leftQty>theQty
								//5.如果是卸下,則更新[批號控管物料檔](TB_INV_LOT)的可用數(+)
								invDao.updateInvLot_StdQty_Unload(theQty, theFaNo, matNo, userId);
								leftQty = (new BigDecimal(leftQty).subtract(new BigDecimal(theQty))).intValue();
							}
						}//end if (leftQty==0)
					}//end if goFLag
					
				}//end for
				
			  }//end if txtType
			
			  for(TbInvLot theInvLot:invLotList){//批號控管物料檔list
				//宣告變數
				Long lotNo = theInvLot.getLot_no();
				lotFaNoList.add(theInvLot.getFa_no());//insert TB_INV_TO_ERP_LOC用
				
				int lotDtlTxnType = 0;//須將txnType轉換成TB_INV_LOT_DTL.txn_type的對應值
				if(txnType==1){//安裝
					lotDtlTxnType = 2;//在TB_INV_LOT_DTL.txn_type的對應值
				}else{//拆下
					lotDtlTxnType = 3;//在TB_INV_LOT_DTL.txn_type的對應值
				}
				
				//6.新增[批號控管物料紀錄檔(批號件明細)](TB_INV_LOT_DTL)
				invDao.insertInvLotDtl(lotNo,lotDtlTxnType,txnNo,siteId,qty,userId);
			  }//end for
			  
			}else if(ctrlType.equals("S") && (isAsset==true)){//序號件			
		     /*
			 if(txnType==1){
				//4-1.如果是安裝,則更新[序號控管物料檔](TB_INV_SRL)的site_id(有値),wh_id(無值)
				invDao.updateInvSrl(txnType,siteId,null,userId,srlNo);
			 }else if(txnType==2){
				//4-2.如果是卸下,則更新[序號控管物料檔](TB_INV_SRL)的site_id(ON_HAND),wh_id(無値)
				//ifSuccess = invDao.updateInvSrl(txnType,null,rtnWhId,userId,srlNo);
				 invDao.updateInvSrl(txnType,"ON_HAND",null,userId,srlNo); 
			 }*/
			 
			}//end if ctrlType && isAsset
			
			//7.查詢是否已存在於TB_INV_ASSET
			invAssetCount = invDao.countInvAsset(siteId,assetType,matNo);
			if(invAssetCount>0){//代表已經存在於TB_INV_ASSET
				
				if(txnType==1){
					//8-1.如果是安裝,則更新[資產檔](TB_INV_ASSET)的料號數量(+),完工日
					invDao.updateInvAsset_Complete(siteId,assetType,matNo,qty,new Date(),new Date(),userId);
				}else if(txnType==2){
					//宣告變數					
					int convertedQty = (new BigDecimal(qty).multiply(new BigDecimal("-1"))).intValue();//將數量變負值,將數量減去
					
					//8-2.如果是卸下,則更新[資產檔](TB_INV_ASSET)的料號數量(-),完工日
					invDao.updateInvAsset_Complete(siteId,assetType,matNo,convertedQty,new Date(),new Date(),userId);
				}
				
			}else{//代表不存在於TB_INV_ASSET
				/*
				if(txnType==1){//只有安裝時才要新增TB_INV_ASSET
					//宣告變數
					//double apAmount = 0;//Q:驗收金額apAmount的值哪裡來??
					
					//8.新增[資產檔](TB_INV_ASSET)
					if(ctrlType.equals("V") && (isAsset==true)){//數量件
						for(String tmpFaNo:lotFaNoList){
							ifSuccess = invDao.insertInvAsset(locationId,siteId,assetType,matNo,srlNo,tmpFaNo,qty,apAmount,apDate,userId,keepUserId,null,null,0,null);
						}
					}else if(ctrlType.equals("S") && (isAsset==true)){//序號件
						ifSuccess = invDao.insertInvAsset(locationId,siteId,assetType,matNo,srlNo,faNo,qty,apAmount,apDate,userId,keepUserId,null,null,0,null);
					}//end if ctrlType && isAsset	
					
				}
				*/
			}//end if(invAssetCount>0)
			
			
			
			//9.查詢序號控管物料檔取得ven_sn (By TB_INV_SITE_TXN.srl_no)
			/*
			if(srlNo!=null && (!srlNo.equals(""))){
				invSrl = invDao.selectInvSrl(srlNo);//用srl_no查,ㄧ定只會有一筆
				if(invSrl!=null){
					venSn = invSrl.getVen_sn();		
				}
			}
			//10.新增[資材保管地變更紀錄檔](TB_INV_TO_ERP_LOC)
			if(txnType==1){
				//如果是安裝,則目前保管地是基站,來源保管地是倉庫
				replace = siteId;
				source = mroWhId;
				
			}else if(txnType==2){
				//如果是卸下,則目前保管地是倉庫,來源保管地是基站
				replace = rtnWhId;
				source = siteId;
			}
			
			if(ctrlType.equals("V") && (isAsset==true)){//數量件
				for(String tmpFaNo:lotFaNoList){
					invDao.insertInvToErpLoc(siteId,orderNo,matNo,venSn,tmpFaNo,qty,replace,source,userId,keepUserId);
				}
			}else if(ctrlType.equals("S") && (isAsset==true)){//序號件
				invDao.insertInvToErpLoc(siteId,orderNo,matNo,venSn,faNo,qty,replace,source,userId,keepUserId);
			}//end if ctrlType && isAsset	
			*/
			
		}//end for loop invSiteTxnList
		
		//11.更新TB_INV_SITE_TXN.to_erp_date
		//invDao.updateInvSiteTxn_ToErpDate(siteId, orderNo, txnType, userId);
		
		
		
		
		//如果是統包,才要做以下驗收這段
		if(actType.equals("T")){
			//宣告變數
			int invTxnType = 1;//發料
			String whAttribute = "T";//暫收倉(倉庫屬性)
			String whType = null;//(倉庫類型)
			int invInvCount = 0;//select TB_INV_INV 筆數(用來判斷是否已存在TB_INV_INV)
			
			
			//1.查詢暫收倉執行發料的紀錄
			invTxnList = invDao.selectInvTxn(siteId,orderNo,invTxnType,whAttribute);
			
			//2.取得總倉代碼
			whAttribute = "M";//總倉(倉庫屬性)
			whType = "C";//公司倉(倉庫類型)
			invWhList = invDao.getTotalWareHouse(whAttribute,whType);
			if(invWhList!=null && invWhList.size()>0){
				TbInvWarehouse tbInvWarehouse = invWhList.get(0);//用wh_attribute='M' and wh_type='C'查,ㄧ定只會有一筆
				totalWhId = tbInvWarehouse .getWh_id();
			}
			
			for(TbInvTxn invTxn:invTxnList){//交易檔list
				//宣告變數			
				String invTxnNo = invTxn.getTxn_no();
				String matNo = invTxn.getMat_no();
				Integer qty  = invTxn.getQty();
				//String poNo = invTxn.getPo_no();//Q:po單號哪裡來?
				//int poLineNbr = invTxn.getPo_line_nbr();//Q:po單明細哪裡來?
				String faNo  = invTxn.getFa_no();			
				Long srlNo   = invTxn.getSrl_no();
				String whId  = invTxn.getWh_id();	
				String refActNo = invTxn.getRef_act_no();	
				//String rnResn = invTxn.getRn_resn();	
				//String rnMemo = invTxn.getRn_memo();	
				//Integer matStatus = invTxn.getMat_status().intValue();
				String poNo = null;
				int poLineNbr = 0;				
				//String osId = null;//派工單號??(TB_INV_TO_ERP_MAT會用到?)
				//String rcvNo = null;//驗收單號??(TB_INV_TO_ERP_MAT會用到?)
				
				//3.查詢是否已存在於TB_INV_INV
				invInvCount = invDao.countInvInv(matNo,totalWhId);				
				
				//4.取得po_no,po_line_nbr
				List<TbInvInv> tmpInvInvList = invDao.getPoNoByWhIdAndMatNo(matNo, whId);
				if(tmpInvInvList!=null && tmpInvInvList.size()>0){
					TbInvInv tbInvInv = tmpInvInvList.get(0);//ㄧ定只會有一筆
					poNo = tbInvInv.getPo_no();
					poLineNbr = tbInvInv.getPo_line_nbr();
				}
				
				if(invInvCount>0){
					//5-1.如果存在於TB_INV_INV,則更新可用數量					
					invDao.updateInvInv_StdQty(qty,matNo,totalWhId,userId);
					
				}else {
					//5-2.如果不存在於TB_INV_INV,則新增TB_INV_INV
					invDao.insertInvInv(totalWhId,matNo,qty,poNo,poLineNbr,userId);
					
				}
				//6.新增資料至TB_INV_TXN(把暫收倉執行發料的紀錄複製一份到交易檔,但倉庫設為總倉)
				int tmpMatStatus = 1;//可用品
				String rnResn = null;
				String rnMemo = null;
				invDao.insertInvTxn(invTxnNo,invTxnType,totalWhId,siteId,orderNo,refActNo,matNo,tmpMatStatus,qty,faNo,srlNo,poNo,poLineNbr,rnResn,rnMemo,userId);
			
				/*
				//7.查詢序號控管物料檔取得ven_sn (By TB_INV_TXN.srl_no)
				invSrl = invDao.selectInvSrl(srlNo);//用srl_no查,ㄧ定只會有一筆
				if(invSrl!=null){
					venSn = invSrl.getVen_sn();		
				}
				
				//8.新增資料至TB_INV_TO_ERP_MAT(統包設備驗收紀錄檔)
				invDao.insertInvToErpMat(siteId,orderNo,osId,poNo,poLineNbr,matNo,venSn,faNo,qty,rcvNo,userId,keepUserId,apDate);
				*/
			}//end loop invTxnList
			
		}//end if(actType.equals("T"))
		return ifSuccess;
	  } catch (Exception e) {
		log.error(ExceptionUtils.getFullStackTrace(e));
		throw new NomsException(e.getMessage());
	  }	
	  
		
	}//end method
	
}
