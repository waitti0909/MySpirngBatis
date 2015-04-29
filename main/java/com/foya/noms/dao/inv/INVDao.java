package com.foya.noms.dao.inv;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvAssetMapper;
import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvLotDtlMapper;
import com.foya.dao.mybatis.mapper.TbInvMaterialMapper;
import com.foya.dao.mybatis.mapper.TbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.TbInvSiteTxnMapper;
import com.foya.dao.mybatis.mapper.TbInvSrlMapper;
import com.foya.dao.mybatis.mapper.TbInvToErpLocMapper;
import com.foya.dao.mybatis.mapper.TbInvToErpMatMapper;
import com.foya.dao.mybatis.mapper.TbInvTxnMapper;
import com.foya.dao.mybatis.mapper.UTbInvAssetMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvLotMapper;
import com.foya.dao.mybatis.mapper.UTbInvOnhandMapper;
import com.foya.dao.mybatis.mapper.UTbInvSiteTxnMapper;
import com.foya.dao.mybatis.mapper.UTbInvTxnMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.TbInvAsset;
import com.foya.dao.mybatis.model.TbInvAssetExample;
import com.foya.dao.mybatis.model.TbInvAssetExample.Criteria;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvLot;
import com.foya.dao.mybatis.model.TbInvLotDtl;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvOnhandExample;
import com.foya.dao.mybatis.model.TbInvSiteTxn;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvToErpLoc;
import com.foya.dao.mybatis.model.TbInvToErpMat;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.noms.dao.BaseDao;



/**
 * 供站點呼叫之Dao
 */
@Repository
public class INVDao extends BaseDao {

	
	@Autowired
	private TbInvMaterialMapper tbInvMaterialMapper;
	
	@Autowired
	private TbInvSiteTxnMapper tbInvSiteTxnMapper;
	
	@Autowired
	private TbInvTxnMapper tbInvTxnMapper;
	
	@Autowired
	private TbInvInvMapper tbInvInvMapper;
	
	@Autowired
	private TbInvOnhandMapper tbInvOnhandMapper;
	
	@Autowired
	private TbInvToErpLocMapper tbInvToErpLocMapper;
	
	@Autowired
	private TbInvAssetMapper tbInvAssetMapper;
	
	@Autowired
	private TbInvLotDtlMapper tbInvLotDtlMapper;
	
	@Autowired
	private TbInvToErpMatMapper tbInvToErpMatMapper;
	
	@Autowired
	private TbInvSrlMapper tbInvSrlMapper;
	
	@Autowired
	private UTbInvLotMapper uTbInvLotMapper;
	
	@Autowired
	private UTbInvOnhandMapper uTbInvOnhandMapper;
	
	@Autowired
	private UTbInvSiteTxnMapper uTbInvSiteTxnMapper;
	
	@Autowired
	private UTbInvAssetMapper uTbInvAssetMapper;
	
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;
	
	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;
	
	@Autowired
	private UTbInvTxnMapper uTbInvTxnMapper;
	
	/**
	 * 新增站點交易檔(TB_INV_SITE_TXN)
	 * @param actType :String (P:一般驗收,T:統包驗收)	
	 * @param txnNo :String 拆裝單號
	 * @param txnType :int 作業類型(1:安裝 2:拆下)
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param matNo :String 料號
	 * @param qty :int 安裝數量
	 * @param faNo :String 資產編號
	 * @param srlNo :long 序號控管物件檔編號
	 * --@param poNo :String PO單號
	 * --@param poLinenbr :int PO單明細號碼
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int insertInvSiteTxn(String actType,String txnNo,int txnType,String siteId,String orderNo,String matNo,int qty,String faNo,long srlNo,String userId){
	//public boolean insertInvSiteTxn(String actType,String txnNo,int txnType,String siteId,String orderNo,String matNo,int qty,String faNo,long srlNo,String userId){	
      
	   //boolean ifSuccess = true;
       TbInvSiteTxn record = new TbInvSiteTxn();
       
       //try{
    	   record.setAct_type(actType);
    	   record.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
    	   record.setTxn_no(txnNo);
    	   record.setSite_id(siteId);
       	   record.setOrder_no(orderNo);
       	   record.setMat_no(matNo);
       	   record.setQty(qty);
       	   record.setFa_no(faNo);
       	   record.setSrl_no(srlNo);
       	   record.setWh_id(null);//
       	   record.setPo_no(null);
       	   record.setPo_line_nbr(null);
       	   record.setTo_erp_date(null);//
       	   record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   record.setMd_user(userId);
       	   record.setMd_time(new Date());   
       
       	   return tbInvSiteTxnMapper.insertSelective(record);
		//}catch (Exception e){
		//	ifSuccess = false;		
		//}
       	//return ifSuccess;
       
    
	}
	
	/**
	 * 新增交易檔(TB_INV_TXN)
	 * @param txnNo :String (txn_type:0=ERP的到料單號 or 統包驗收時記工單號碼,txn_type:1=發料單號,txn_type:2=收料單號,txn_type:3=4=調撥單號,txn_type:5=庫存異動單號)
	 * @param txnType :int 作業類型(0:到料入庫，1:發料，2:收料，3:調出，4:調入，5:庫存狀態異動)
	 * @param whId :String 倉庫代碼
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param refActNo: String 參考單號 (txn_type:1=領料申請單號，2=退料申請單號，其餘皆空值)
	 * @param matNo :String 料號
	 * @param matStatus :int 物料狀態(1:可用品，2:待送修，3:待報廢)
	 * @param qty :int 數量
	 * @param faNo :String 資產編號
	 * @param srlNo :long 序號控管物件檔編號
	 * @param poNo :String PO單號
	 * @param poLineNbr :int PO單明細號碼
	 * @param rnResn :String 送修或報廢原因(代碼)
	 * @param rnMemo :String 送修或報廢原因備註
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int insertInvTxn(String txnNo,int txnType,String whId,String siteId,String orderNo,String refActNo,String matNo,int matStatus,int qty,String faNo,long srlNo,String poNo,int poLineNbr,String rnResn,String rnMemo,String userId){
	//public boolean insertInvTxn(String txnNo,int txnType,String whId,String siteId,String orderNo,String refActNo,String matNo,int matStatus,int qty,String faNo,long srlNo,String poNo,int poLineNbr,String rnResn,String rnMemo,String userId){	        
        //boolean ifSuccess = true;
        TbInvTxn record = new TbInvTxn();
        
        //try{
     	   record.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
     	   record.setTxn_no(txnNo);
     	   record.setWh_id(whId);
     	   record.setSite_id(siteId);
           record.setOrder_no(orderNo);
           record.setRef_act_no(refActNo);
           record.setMat_no(matNo);
           record.setMat_status(Byte.valueOf(String.valueOf(matStatus)));
           record.setQty(qty);
           record.setFa_no(faNo);
           record.setSrl_no(srlNo);        	  
           record.setPo_no(poNo);
           record.setPo_line_nbr(Short.valueOf(String.valueOf(poLineNbr)));
           record.setRn_resn(rnResn);
           record.setRn_memo(rnMemo);
           record.setCr_user(userId);
           record.setCr_time(new Date());   
        
     	   return tbInvTxnMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
        	
       
	}
	
	/**
	 * 新增庫存檔(TB_INV_INV)
	 * @param whId :String 倉庫代碼
	 * @param matNo :String 料號
	 * @param stdQty :int 數量
	 * @param poNo :String PO單號
	 * @param poLineNbr :int PO單明細號碼
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int insertInvInv(String whId,String matNo,int stdQty,String poNo,int poLineNbr,String userId){
	//public boolean insertInvInv(String whId,String matNo,int stdQty,String poNo,int poLineNbr,String userId){
		//boolean ifSuccess = true;
        TbInvInv record = new TbInvInv();
     
        //try{
           record.setWh_id(whId);
           record.setMat_no(matNo);
           record.setStd_qty(stdQty);
           record.setInv_qty(0);
           record.setWrd_qty(0);
           record.setWsp_qty(0);
           record.setRd_qty(0);
           record.setRj_qty(0);
           record.setBooking_qty(0);
           record.setPo_no(poNo);
           record.setPo_line_nbr(Short.valueOf(String.valueOf(poLineNbr)));
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   record.setMd_user(userId);
       	   record.setMd_time(new Date());   
       	   
     	   
        
           return tbInvInvMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
       
	}
	
	/**
	 * 新增站點在途檔(TB_INV_ONHAND)
	 * @param txnNo :String 拆裝單號
	 * @param onhandTxnType :int 在途檔的作業類型(1:發料，2:調出，3:拆下)
	 * @param siteId :String 基站代碼	 
	 * @param orderNo: String 工單號碼
	 * @param matNo :String 料號	 
	 * @param matStatus :int 物料狀態 	 
	 * @param qty :int 拆下數量
	 * @param faNo :String 資產編號
	 * @param srlNo :long 序號控管物件檔編號
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int insertInvOnHand(String txnNo,int onhandTxnType,String siteId,String orderNo,String matNo,int matStatus,int qty,String faNo,long srlNo,String userId){
	//public boolean insertInvOnHand(String txnNo,int onhandTxnType,String siteId,String orderNo,String matNo,int matStatus,int qty,String faNo,long srlNo,String userId){
		//boolean ifSuccess = true;
        TbInvOnhand record = new TbInvOnhand ();
        

       // try{
           record.setTxn_type(Byte.valueOf(String.valueOf(onhandTxnType)));
     	   record.setTxn_no(txnNo);
     	   record.setSite_id(siteId);
     	   //record.setSite_id("ON_HAND");
      	   record.setOrder_no(orderNo);
      	   record.setWh_id(null);//是null           
           record.setMat_no(matNo);
           record.setMat_status(Byte.valueOf(String.valueOf(matStatus)));           
           record.setQty(qty);
           record.setOnhand_qty(qty);
           record.setFa_no(faNo);
           record.setSrl_no(srlNo);           
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   record.setMd_user(userId);
       	   record.setMd_time(new Date());
        
           return tbInvOnhandMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 新增資材保管地變更紀錄檔(TB_INV_TO_ERP_LOC)	
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param matNo :String 料號
	 * @param venSn :String 廠商序號
	 * @param faNo :String 資產編號
	 * @param qty :int 數量
	 * @param replace :String 目前保管地
	 * @param source :String 來源保管地
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人
	 * @return 是否成功 :Boolean
	 */
	public int insertInvToErpLoc(String siteId,String orderNo,String matNo,String venSn,String faNo,int qty,String replace,String source,String userId,String keepUserId){
	//public boolean insertInvToErpLoc(String siteId,String orderNo,String matNo,String venSn,String faNo,int qty,String replace,String source,String userId,String keepUserId){
		//boolean ifSuccess = true;
		TbInvToErpLoc record = new TbInvToErpLoc();
        

       // try{
           
     	   record.setSite_id(siteId);
      	   record.setOrder_no(orderNo);
      	   record.setMat_no(matNo);
      	   record.setVen_sn(venSn);
      	   record.setFa_no(faNo);
      	   record.setQty(qty);
      	   record.setReplace(replace);
      	   record.setAssigned_name(keepUserId);//保管人
      	   record.setSource(source);
      	   record.setLast_sync_time(null);      	        
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   
        
       	   return tbInvToErpLocMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 新增資產檔(TB_INV_ASSET)
	 * @param locationId :String 站點代碼
	 * @param siteId :String 基站代碼
	 * @param assetType :String 資產類型
	 * @param itemNo: String 料號或工單號碼
	 * @param srlNo :long 序號控管物件檔編號
	 * @param faNo  :String 資產編號
	 * @param qty   :int 數量
	 * @param apAmount :BigDecimal 驗收金額
	 * @param apDate   :Date 驗收日期
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人
	 * @param setupDate :Date 安裝日期
	 * @param setupOrderNo :String 安裝工單號碼
	 * @param setupQty :int 安裝數量
	 * @param completeDate :Date 完工日期
	 * @return 是否成功 :Boolean
	 */
	public int insertInvAsset(String locationId,String siteId,String assetType,String itemNo,long srlNo,String faNo,int qty,BigDecimal apAmount,Date apDate,String userId,String keepUserId,Date setupDate,String setupOrderNo,int setupQty,Date completeDate){
	//public boolean insertInvAsset(String locationId,String siteId,String assetType,String itemNo,long srlNo,String faNo,int qty,BigDecimal apAmount,Date apDate,String userId,String keepUserId,Date setupDate,String setupOrderNo,int setupQty,Date completeDate){
		//boolean ifSuccess = true;
		TbInvAsset record = new TbInvAsset();
        

       // try{
           record.setLocation_id(locationId);
     	   record.setSite_id(siteId);
     	   record.setAssigned_name(keepUserId);//保管人員
     	   record.setAsset_type(assetType);
     	   record.setItem_no(itemNo);
     	   record.setSrl_no(srlNo);
     	   record.setFa_no(faNo);
     	   record.setQty(qty);
     	   record.setAp_amount(apAmount);//驗收金額
     	   record.setAp_date(apDate);//驗收日期
     	   record.setFa_date(null);//insert到TB_INV_TO_ERP_FA時更新此欄位??何時insert TB_INV_TO_ERP_FA?? 
     	   record.setUnload_qty(0);
     	   record.setScrp_date(null);//除帳工單日期
     	   record.setScrp_order_no(null);//除帳工單號碼  
     	   
     	   record.setSetup_date(setupDate);//安裝日期
     	   record.setSetup_order_no(setupOrderNo);//工單號碼
     	   record.setSetup_qty(setupQty);//安裝數量
     	   record.setComplete_date(completeDate);//工單完工日
     	  
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   record.setMd_user(userId);
       	   record.setMd_time(new Date());
       	   
        
       	   return tbInvAssetMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
        
	    
	}
	
	/**
	 * 新增批號控管物料紀錄檔(批號件明細)(TB_INV_LOT_DTL)
	 * @param lotNo :long 批號控管物料檔編號(流水號)
	 * @param txnType :int (1.安裝 2.拆下)
	 * @param txnNo: String 拆裝單號
	 * @param siteId :String 基站代碼
	 * @param qty   :int 數量	
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int insertInvLotDtl(long lotNo,int txnType,String txnNo,String siteId,int qty,String userId){
	//public boolean insertInvLotDtl(long lotNo,int txnType,String txnNo,String siteId,int qty,String userId){
		//boolean ifSuccess = true;		
		TbInvLotDtl record = new TbInvLotDtl();
		
        
        //try{
           
     	   record.setLot_no(lotNo);
     	   record.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
     	   record.setTxn_no(txnNo);
     	   record.setAct_date(new Date());//工單驗收日-DB:最近異動日期
     	   record.setSite_id(siteId);
     	   record.setQty(qty);     	   
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   record.setMd_user(userId);
    	   record.setMd_time(new Date());
        
       	   return tbInvLotDtlMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 新增統包設備驗收紀錄檔(TB_INV_TO_ERP_MAT)
	 * @param siteId :String 基站代碼
	 * @param orderNo :String 工單號碼
	 * @param osId    :String 派工單號
	 * @param poNo 	  :String PO單號
	 * @param poLineNbr  :int PO眀細項目號碼
	 * @param matNo   :String 料號
	 * @param venSn   : String 廠商序號
	 * @param faNo    :String 資產編號
	 * @param qty   :int 數量	
	 * @param rcvNo   :String 驗收單號	
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管人
	 * @param apDate:Date 驗收日期
	 * @return 是否成功 :Boolean
	 */
	public int insertInvToErpMat(String siteId,String orderNo,String osId,String poNo,int poLineNbr,String matNo,String venSn,String faNo,int qty,String rcvNo,String userId,String keepUserId,Date apDate){
	//public boolean insertInvToErpMat(String siteId,String orderNo,String osId,String poNo,int poLineNbr,String matNo,String venSn,String faNo,int qty,String rcvNo,String userId,String keepUserId,Date apDate){
		//boolean ifSuccess = true;
		TbInvToErpMat record = new TbInvToErpMat();
        
        
        //try{
           
     	   record.setSite_id(siteId);
     	   record.setOrder_no(orderNo);
     	   record.setOs_id(osId);//?
     	   record.setPo_no(poNo);
     	   record.setPo_line_number(Short.valueOf(String.valueOf(poLineNbr)));
     	   record.setMat_no(matNo);
     	   record.setVen_sn(venSn);
     	   record.setFa_no(faNo);
     	   record.setQty(qty);
     	   record.setAp_date(apDate);//驗收日期??
     	   record.setAssigned_name(keepUserId);
     	   record.setRcv_no(rcvNo);//驗收單號??
     	   record.setLast_sync_time(null);     	    	        	        
           record.setCr_user(userId);
       	   record.setCr_time(new Date());
       	   
        
       	   return tbInvToErpMatMapper.insertSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	public int updateInvOnHand(TbInvOnhand record){
		return tbInvOnhandMapper.updateByPrimaryKey(record);
	}
	/**
	 * 更新在途檔(TB_INV_ONHAND)
	 * @param txnType :int 作業類型(1:發料 3:拆下)
	 * @param siteId :String 站台代碼
	 * @param whId :String 倉庫代碼
	 * @param orderNo :String 工單號碼	
	 * @param matNo :String 料號
	 * @param qty :int 安裝數量	
	 * @param userId :String 使用者
	 * @param srlNo :long 序號控管物件檔編號
	 * @return 是否成功 :Boolean
	 */
	public int updateInvOnHand(int txnType,String siteId,String whId,String orderNo,String matNo,int qty,String userId,long srlNo){
	//public boolean updateInvOnHand(String orderNo,String matNo,int qty,String userId){
		//boolean ifSuccess = true;
		TbInvOnhand record = new TbInvOnhand();
		
        //try{
           
     	   record.setOnhand_qty(qty);
     	   //record.setSite_id(siteId);
     	   //record.setWh_id(whId);
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   

     	   record.setOrder_no(orderNo);
     	   record.setMat_no(matNo);
     	   record.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
     	   record.setSrl_no(srlNo);
       	   return uTbInvOnhandMapper.updateOnhandByOrderNoAndMatNo(record);
 		//}catch (Exception e){
 			//ifSuccess = false;		
 		//}
        //return ifSuccess;
       
	}
	
	/**
	 * 更新資產檔(TB_INV_ASSET)的卸下數量
	 * @param siteId :String 基站編號	
	 * @param assetType :String 資產類型(M:物料 W:作業)
	 * @param itemNo :String 料號/工程款
	 * @param unloadQty :int 卸下數量
	 * @param scrpDate :Date 除帳日
	 * @param scrpOrderNo :String  除帳工單
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int updateInvAsset_Unload(String siteId,String assetType,String itemNo,int unloadQty,Date scrpDate,String scrpOrderNo,String userId,long srlNo){
	//public boolean updateInvAsset_Unload(String siteId,String assetType,String itemNo,int unloadQty,Date scrpDate,String scrpOrderNo,String userId){		    
	    //boolean ifSuccess = true;
		TbInvAsset record = new TbInvAsset();
		
		int qty = 0;
		List<TbInvAsset> invAssetList = selectInvAsset(siteId, itemNo, assetType,srlNo);
		if(invAssetList.size()>0){	
			TbInvAsset theInvAsset = invAssetList.get(0);
			qty = theInvAsset.getQty() - unloadQty;//總數量=(原始數量-本次拆下)
			unloadQty = (theInvAsset.getUnload_qty()==null?0:theInvAsset.getUnload_qty()) + unloadQty;//拆下數量=(原始拆下數量+本次拆下)
        }
		
        //try{          
		   record.setQty(qty);
     	   record.setUnload_qty(unloadQty);     	   
     	   record.setScrp_date(scrpDate);
     	   record.setScrp_order_no(scrpOrderNo);     	   
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   
     	   record.setSite_id(siteId);
     	   record.setItem_no(itemNo);
     	   record.setAsset_type(assetType);
     	   record.setSrl_no(srlNo);
        
       	   return uTbInvAssetMapper.updateBySiteIdAndItemNoSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
       // return ifSuccess;
        
	    
	}
	/**
	 * 更新資產檔(TB_INV_ASSET)的料號數量
	 * @param siteId :String 基站編號	
	 * @param assetType :String 資產類型(M:物料 W:作業)
	 * @param itemNo :String 料號/工程款
	 * @param qty :int 數量	
	 * @param completeDate :Date 完工日
	 * @param apDate :Date 驗收日
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int updateInvAsset_Complete(String siteId,String assetType,String itemNo,int qty,Date completeDate,Date apDate,String userId){
	//public boolean updateInvAsset_Complete(String siteId,String assetType,String itemNo,int qty,Date completeDate,String userId){
		//boolean ifSuccess = true;
		TbInvAsset record = new TbInvAsset();
                
        //try{          
     	  
     	   record.setQty(qty);
     	   record.setComplete_date(completeDate);
     	   record.setAp_date(apDate);
     	   
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   record.setSite_id(siteId);
     	   record.setItem_no(itemNo);
     	   record.setAsset_type(assetType);
     	  
        
       	   return uTbInvAssetMapper.updateBySiteIdAndItemNoSelective(record);
 		//}catch (Exception e){
 			//ifSuccess = false;		
 		//}
        //return ifSuccess;
        
	    
	}
	
	/**
	 * 更新資產檔(TB_INV_ASSET)的料號數量
	 * @param locationId :String 站點編號
	 * @param srlNo :long 序號件
	 * @param faNo :String 批號
	 * @param qty :int 數量
	 * @param siteId :String 基站編號	
	 * @param assetType :String 資產類型(M:物料 W:作業)
	 * @param itemNo :String 料號/工程款
	 * @param setupDate :Date 安裝日期
	 * @param setupOrderNo :String 安裝工單號碼
	 * @param setupQty :int 安裝數量	 
	 * @param userId :String 使用者
	 * @param keepUserId :String 保管使用者
	 * @return 是否成功 :Boolean
	 */
	public int updateInvAsset_Setup(String locationId,long srlNo,String faNo,int qty,String siteId,String assetType,String itemNo,Date setupDate,String setupOrderNo,int setupQty,String userId,String keepUserId){
	//public boolean updateInvAsset_Setup(String siteId,String assetType,String itemNo,Date setupDate,String setupOrderNo,int setupQty,Date completeDate,String userId){
		//boolean ifSuccess = true;
		TbInvAsset record = new TbInvAsset();
		List<TbInvAsset> invAssetList = selectInvAsset(siteId, itemNo, assetType,srlNo);
		
		int totalQty = setupQty = qty;//初始：總數量=安裝數量=本次安裝
		
		if(invAssetList.size()>0){//找到資產資料
			TbInvAsset theInvAsset = invAssetList.get(0);
			totalQty = theInvAsset.getQty() + qty;//總數量=(原始數量+本次安裝)
			setupQty = theInvAsset.getSetup_qty() + qty;//安裝數量=(原始安裝數量+本次安裝)
        } 
     	   
		   record.setLocation_id(locationId);     
  	   	   record.setAssigned_name(keepUserId);//保管人員     	   
  	   	   record.setSrl_no(srlNo);
  	   	   record.setFa_no(faNo);
  	   	   record.setQty(totalQty);//append
  	   
     	   record.setSetup_date(setupDate);
     	   record.setSetup_order_no(setupOrderNo);
     	   record.setSetup_qty(setupQty);
     	   record.setMd_user(userId);
    	   record.setMd_time(new Date());
    	   
    	   record.setSite_id(siteId);
    	   record.setItem_no(itemNo);
    	   record.setAsset_type(assetType);
    	   
       	   return uTbInvAssetMapper.updateBySiteIdAndItemNoSelective(record);
	    
	}
	/**
	 * 更新序 號控管物料檔(TB_INV_SRl)的物料保管地
	 * @param txnType :int (1.安裝 2.拆下)	
	 * @param siteId :String 基站代碼
	 * @param whId :String 倉庫代碼
	 * @param userId :String 使用者
	 * @param srlNo  :long 序號控管物料檔編號(流水號)
	 * @return 是否成功 :Boolean
	 */
	public int updateInvSrl(int txnType,String siteId,String whId,String userId,long srlNo){
	//public boolean updateInvSrl(int txnType,String siteId,String whId,String userId,long srlNo){
		//boolean ifSuccess = true;
		TbInvSrl record = new TbInvSrl();
        
        
        //try{
        	if(txnType==1){//安裝
        		record.setSite_id(siteId);
        		record.setWh_id(whId);
        	}else if(txnType==2){//拆下        		
             	record.setSite_id(siteId);        		
        	}     	   
     	    record.setMd_user(userId);
     	    record.setMd_time(new Date());
     	    record.setSrl_no(srlNo);
        
       	    return tbInvSrlMapper.updateByPrimaryKeySelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 更新批號控管物料檔(TB_INV_LOT)的安裝數量
	 * @param setupQty :int 安裝數量
	 * @param faNo :String 資產編號
	 * @param matNo :String 料號
	 * @param userId :String 使用者	
	 * @return 是否成功 :Boolean
	 */
	public int updateInvLot_SetUpQty_Setup(int setupQty,String faNo,String matNo,String userId){
	//public boolean updateInvLot_SetUpQty_Setup(int setupQty,String faNo,String matNo,String userId){
		//boolean ifSuccess = true;
		TbInvLot record = new TbInvLot();
                
        //try{
           
     	   record.setSetup_qty(setupQty);
     	   record.setStd_qty(setupQty);
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   record.setMat_no(matNo);
     	   record.setFa_no(faNo);
        
       	   return uTbInvLotMapper.updateByMatNoAndFaNo_Setup(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 更新批號控管物料檔(TB_INV_LOT)的可用數量
	 * @param stdQty :int 數量
	 * @param faNo :String 資產編號
	 * @param matNo :String 料號
	 * @param userId :String 使用者	
	 * @return 是否成功 :Boolean
	 */
	public int updateInvLot_StdQty_Unload(int stdQty,String faNo,String matNo,String userId){
	//public boolean updateInvLot_StdQty_Unload(int stdQty,String faNo,String matNo,String userId){
		//boolean ifSuccess = true;
		TbInvLot record = new TbInvLot();
                
        //try{          
     	  
     	   record.setStd_qty(stdQty);
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   record.setMat_no(matNo);
     	   record.setFa_no(faNo);
        
       	   return uTbInvLotMapper.updateByMatNoAndFaNo_Unload(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
	    
	}
	
	/**
	 * 更新庫存檔(TB_INV_INV)的可用數量
	 * @param stdQty :int 安裝數量
	 * @param matNo :String 料號
	 * @param whId :String 倉庫代碼	
	 * @param userId :String 使用者	
	 * @return 是否成功 :Boolean
	 */
	public int updateInvInv_StdQty(int stdQty,String matNo,String whId,String userId){
	//public boolean updateInvInv_StdQty(int stdQty,String matNo,String whId,String userId){
		//boolean ifSuccess = true;
		TbInvInv record = new TbInvInv();
                
       // try{          
     	  
     	   record.setStd_qty(stdQty);
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   record.setMat_no(matNo);
     	   record.setWh_id(whId);
        
       	   return uTbInvInvMapper.updateByMatNoAndWhIdSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
       // return ifSuccess;
	    
	}
	
	/**
	 * 更新站點交易檔(TB_INV_SITE_TXN)的TO_ERP_DATE
	 * @param siteId :String 基站代碼
	 * @param orderNo: String 工單號碼
	 * @param txnType :int 作業類型(1:安裝 2:拆下)
	 * @param userId :String 使用者
	 * @return 是否成功 :Boolean
	 */
	public int updateInvSiteTxn_ToErpDate(String siteId,String orderNo,int txnType,String userId){
	//public boolean updateInvSiteTxn_ToErpDate(String siteId,String orderNo,int txnType,String userId){
		//boolean ifSuccess = true;
		TbInvSiteTxn record = new TbInvSiteTxn();
		
       // try{
           
     	   record.setTo_erp_date(new Date());
     	   record.setMd_user(userId);
     	   record.setMd_time(new Date());
     	   
     	   record.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
     	   record.setSite_id(siteId);
     	   record.setOrder_no(orderNo);
     	   
       	   return uTbInvSiteTxnMapper.updateBySiteIdAndOrderNoSelective(record);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
       // return ifSuccess;
       
	}
	
	/**
	 * 刪除資產檔(TB_INV_ASSET)	
	 * @param siteId :String 基站代碼
	 * @param itemNo: String 料號或工單號碼
	 * @param assetType :String 資產類型	
	 * @return 是否成功 :Boolean
	 */
	public int deleteInvAsset(String siteId,String itemNo,String assetType){
	//public boolean deleteInvAsset(String siteId,String itemNo,String assetType){
		//boolean ifSuccess = true;	
		TbInvAssetExample example = new TbInvAssetExample();

        //try{
     	  
     	  example.createCriteria().andSite_idEqualTo(siteId);
     	  example.createCriteria().andItem_noEqualTo(itemNo);
     	  example.createCriteria().andAsset_typeEqualTo(assetType);
        
       	   return tbInvAssetMapper.deleteByExample(example);
 		//}catch (Exception e){
 		//	ifSuccess = false;		
 		//}
        //return ifSuccess;
        
	    
	}
	
	/**
	 * 查詢序號控管物料檔(TB_INV_SRL)
	 * @param srlNo: long 序號控管物料檔編號(流水號)
	 * @return 資料列 :TbInvSrl
	 */	
	public TbInvSrl selectInvSrl(long srlNo){		
		TbInvSrl record = new TbInvSrl();  
		
       	record = tbInvSrlMapper.selectByPrimaryKey(srlNo);
 		
        return record;

	}
	
	/**工單完工:安裝用
	 * 查詢批號控管物料檔(TB_INV_LOT)
	 * @param matNo :String 料號
	 * @return 資料列 :List<TbInvLot>
	 */	
	public List<TbInvLot> selectInvLot_Setup(String matNo){
		
		TbInvLot paramInvLot = new TbInvLot();
		List<TbInvLot> recordList = new ArrayList<TbInvLot>();
		
		//組parameter
		paramInvLot.setMat_no(matNo);
		//paramInvLot.setFa_no(faNo);
		
		recordList = uTbInvLotMapper.selectLotByMatNo_Setup(paramInvLot);
		
		
		return recordList;
	}
	
	/**工單完工:拆下用
	 * 查詢批號控管物料檔(TB_INV_LOT)
	 * @param matNo :String 料號
	 * @return 資料列 :List<TbInvLot>
	 */	
	public List<TbInvLot> selectInvLot_Unload(String matNo){
		
		TbInvLot paramInvLot = new TbInvLot();
		List<TbInvLot> recordList = new ArrayList<TbInvLot>();
		
		//組parameter
		paramInvLot.setMat_no(matNo);
		//paramInvLot.setFa_no(faNo);
		
		recordList = uTbInvLotMapper.selectLotByMatNo_Unload(paramInvLot);
		
		
		return recordList;
	}
	
	/**
	 * 查詢站點交易檔(TB_INV_SITE_TXN)
	 * @param siteId: String 基站編號
	 * @param orderNo: String 工單編號
	 * @param txnType: int (1.安裝 2.拆下)
	 * @return 資料列 :List<TbInvSiteTxn>
	 */	
	public List<TbInvSiteTxn> selectInvSiteTxn(String siteId,String orderNo,int txnType){
			
		TbInvSiteTxn  paramInvSiteTxn = new TbInvSiteTxn();
		List<TbInvSiteTxn> recordList = new ArrayList<TbInvSiteTxn>();
		
		//組parameter
		paramInvSiteTxn.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
		paramInvSiteTxn.setSite_id(siteId);
		paramInvSiteTxn.setOrder_no(orderNo);
		
		recordList = uTbInvSiteTxnMapper.selectInvSiteTxnBySiteIdAndOrderNo(paramInvSiteTxn);
		
		
		return recordList;
	}
	
	/**
	 * 查詢交易檔(TB_INV_TXN)
	 * @param siteId: String 基站編號
	 * @param orderNo: String 工單編號
	 * @param txnType: int (1.安裝 2.拆下)
	 * @param whAttribute: String  (V:虛擬倉 A:實體倉 T:暫收倉 M:總倉)
	 * @return 資料列 :List<TbInvTxn>
	 */	
	public List<TbInvTxn> selectInvTxn(String siteId,String orderNo,int txnType,String whAttribute){
		TbInvTxn  paramInvTxn = new TbInvTxn();
		List<TbInvTxn> recordList = new ArrayList<TbInvTxn>();
		
		//組parameter
		paramInvTxn.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
		paramInvTxn.setSite_id(siteId);
		paramInvTxn.setOrder_no(orderNo);
		
		recordList = uTbInvTxnMapper.selectInvTxnByTempWh(paramInvTxn);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢資產檔(TB_INV_ASSET)
	 * @param siteId: String 基站編號
	 * @param itemNo: String 料號/工程款
	 * @param assetType: String  (M:物料 W:工程)
	 * @param srlNo: 序號件編號
	 * @return 資料列 :List<TbInvAsset>
	 */	
	public List<TbInvAsset> selectInvAsset(String siteId,String itemNo,String assetType,long srlNo){

		List<TbInvAsset> recordList = new ArrayList<TbInvAsset>();
		
		
		TbInvAssetExample example = new TbInvAssetExample();
		example.setOrderByClause("SETUP_DATE");
		
		Criteria criteria = example.createCriteria();
		criteria.andSite_idEqualTo(siteId);
		criteria.andItem_noEqualTo(itemNo);
		criteria.andAsset_typeEqualTo(assetType);
		criteria.andSrl_noEqualTo(srlNo);
		
		
		recordList = tbInvAssetMapper.selectByExample(example);	
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢資產檔(TB_INV_ASSET)
	 * @param siteId: String 基站編號
	 * @param itemNo: String 料號/工程款
	 * @param assetType: String  (M:物料 W:工程)
	 * @return 資料列 :List<TbInvAsset>
	 */	
	public List<TbInvAsset> selectInvAsset(String siteId,String itemNo,String assetType){

		List<TbInvAsset> recordList = new ArrayList<TbInvAsset>();
		
		TbInvAssetExample example = new TbInvAssetExample();
		example.setOrderByClause("SETUP_DATE");
		
		Criteria criteria = example.createCriteria();
		criteria.andSite_idEqualTo(siteId);
		criteria.andItem_noEqualTo(itemNo);
		criteria.andAsset_typeEqualTo(assetType);
		
		recordList = tbInvAssetMapper.selectByExample(example);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢在塗檔(TB_INV_ONHAND)
	 * @param txnType: int 作業類型(1:發料 3:拆下)
	 * @param matNo: String 料號
	 * @param orderNo: String  工單號碼
	 * @return 資料列 :List<TbInvOnhand>
	 */	
	public List<TbInvOnhand> selectInvOnhand(int txnType,String matNo,String orderNo){

		List<TbInvOnhand> recordList = new ArrayList<TbInvOnhand>();
		
		TbInvOnhandExample example = new TbInvOnhandExample();
		example.createCriteria().andTxn_typeEqualTo(Byte.valueOf(String.valueOf(txnType)));
		example.createCriteria().andMat_noEqualTo(matNo);
		example.createCriteria().andOrder_noEqualTo(orderNo);
		
		
		recordList = tbInvOnhandMapper.selectByExample(example);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢在途檔(TB_INV_ONHAND)
	 * @param matNo: String 料號
	 * @param orderNo: String  工單號碼
	 * @return 資料列 :List<TbInvOnhand>
	 */	
	public List<TbInvOnhand> selectInvOnhandOrderbyTxnType(String matNo,String orderNo,long srlNo){

		List<TbInvOnhand> recordList = new ArrayList<TbInvOnhand>();
		
		TbInvOnhand tbInvOnhand = new TbInvOnhand();
		tbInvOnhand.setMat_no(matNo);
		tbInvOnhand.setOrder_no(orderNo);
		tbInvOnhand.setSrl_no(srlNo);
		
		recordList = uTbInvOnhandMapper.selectInvOnhandOrderbyTxnType(tbInvOnhand);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢倉庫檔(TB_INV_WAREHOUSE)
	 * @param whAttribute: String  (V:虛擬倉 A:實體倉 T:暫收倉 M:總倉)
	 * @param whType: String  (C:公司 V:廠商)
	 * @return 資料列 :List<TbInvWarehouse>
	 */
	public List<TbInvWarehouse> getTotalWareHouse(String whAttribute,String whType){
		TbInvWarehouse  paramInvWarehouse = new TbInvWarehouse();
		List<TbInvWarehouse> recordList = new ArrayList<TbInvWarehouse>();
		
		//組parameter
		paramInvWarehouse.setWh_attribute(whAttribute);
		paramInvWarehouse.setWh_type(whType);
		
		recordList = uTbInvWarehouseMapper.selectTotalWhId(paramInvWarehouse);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢交易檔(TB_INV_TXN)
	 * @param txnType: int (2.收料)
	 * @param siteId: String 基站編號
	 * @param orderNo: String 工單編號
	 * @param matNo: String 料號
	 * @param faNo: String 資產編號
	 * @return 資料列 :List<TbInvTxn>
	 */
	public List<TbInvTxn> getRtnWhIdBySiteIdAndOrderNo(int txnType,String siteId,String orderNo,String matNo,String faNo){
		TbInvTxn  paramInvTxn = new TbInvTxn();
		List<TbInvTxn> recordList = new ArrayList<TbInvTxn>();
		
		//組parameter
		paramInvTxn.setTxn_type(Byte.valueOf(String.valueOf(txnType)));
		paramInvTxn.setSite_id(siteId);
		paramInvTxn.setOrder_no(orderNo);
		paramInvTxn.setMat_no(matNo);
		paramInvTxn.setFa_no(faNo);
		recordList = uTbInvTxnMapper.getRtnWhIdBySiteIdAndOrderNo(paramInvTxn);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢庫存檔(TB_INV_INV)
	 * @param matNo: String 料號
	 * @param whId: String 倉庫代碼
	 * @return 資料列 :List<TbInvInv>
	 */
	public List<TbInvInv> getPoNoByWhIdAndMatNo(String matNo,String whId){
		TbInvInv  paramInvInv = new TbInvInv();
		List<TbInvInv> recordList = new ArrayList<TbInvInv>();
		
		//組parameter
		paramInvInv.setMat_no(matNo);
		paramInvInv.setWh_id(whId);
		recordList = uTbInvInvMapper.getPoNoByWhIdAndMatNo(paramInvInv);
		
		
		return recordList;
		
		
	}
	
	/**
	 * 查詢料號主檔(TB_INV_MATERIAL)
	 * @param matNo: String 料號	
	 * @return 資料列 :TbInvMaterial
	 */
	public TbInvMaterial getMaterialByMatNo(String matNo){
	
		TbInvMaterial tbInvMaterial = tbInvMaterialMapper.selectByPrimaryKey(matNo);
		
		return tbInvMaterial;
		
		
	}
	
	/**
	 * 是否在資產檔有資料(TB_INV_ASSET)
	 * @param siteId :String 基站編號	
	 * @param assetType :String 資產類型(M:物料 W:作業)
	 * @param matNo :String 料號	
	 * @return 筆數 :int
	 */
	public int countInvAsset(String siteId,String assetType,String matNo){
		
		TbInvAsset  paramInvAsset = new TbInvAsset();
		int invAssetCount = 0;	
		
		//組parameter
		paramInvAsset.setAsset_type(assetType);
		paramInvAsset.setSite_id(siteId);
		paramInvAsset.setItem_no(matNo);
		
		invAssetCount = uTbInvAssetMapper.countBySiteIdAndItemNo(paramInvAsset);
		
		
		return invAssetCount;
		
		
	}
	/**
	 * 是否在庫存檔有資料(TB_INV_INV)
	 * @param matNo :String 料號	
	 * @param whId :String 倉庫代碼	
	 * @return 筆數 :int
	 */
	public int countInvInv(String matNo,String whId){
		
		TbInvInv  paramInvInv = new TbInvInv();
		int invInvCount = 0;	
		
		//組parameter
		paramInvInv.setMat_no(matNo);
		paramInvInv.setWh_id(whId);	
		
		invInvCount = uTbInvInvMapper.countByMatNoAndWhId(paramInvInv);
		
		
		return invInvCount;
		
	}
}
