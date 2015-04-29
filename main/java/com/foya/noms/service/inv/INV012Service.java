package com.foya.noms.service.inv;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMrAct;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvSrlExample;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INV012Dao;
import com.foya.noms.dao.inv.InvPublicUtilDao;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvMrDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.BaseService;

@Service
public class INV012Service extends BaseService{
	
	@Autowired
	private INV012Dao inv012Dao;
	
	@Autowired
	private InvPublicUtilDao invPublicUtilDao;
	@Autowired
	private InvPublicUtilService invPublicUtilService;
	public List<TbInvMaterialOptDTO> search(HashMap<String,Object> dataObj){
		return inv012Dao.search(dataObj);
	}
	
	public List<TbInvMrDDTO> searchUTbInvMrD(String optId){
		List<TbInvMrDDTO> list =inv012Dao.searchUTbInvMrD(optId);
		try{
			for(int i=0;i<list.size();i++){
				TbInvMrDDTO dto = (TbInvMrDDTO)list.get(i);
				try{
					dto.setMrQty(inv012Dao.searchTxnMRSumQty(optId,dto.getMAT_NO(), String.valueOf(dto.getDTL_SEQ())));
				}catch(Exception e){}
				list.set(i, dto);
			}
		}catch(NullPointerException e){}
		return list;
	}
	
	public List<TbInvTxnDTO> searchUTbInvTxn(String optId,String matNo,Long dtlSeq){
		return inv012Dao.searchUTbInvTxn(optId,matNo,dtlSeq);
	}
	public List<TbSysLookup> getLookupByTypeAndName(String type, String name) {
		return invPublicUtilDao.getLookupByTypeAndName(type, name);
	}
	@Transactional
	public void updateTbInvMaterialOpt(String optId,String status,String user,Date today,String whId) throws NomsException{
		
		TbInvBookingExample example = new TbInvBookingExample();
		example.createCriteria().andAct_noEqualTo(optId);
		List<TbInvBooking> bookingList = inv012Dao.selectByExample(example);
		
		for(TbInvBooking tbInvBooking : bookingList){
			try{
				if(tbInvBooking.getBooking_qty() == null){
					inv012Dao.updateTbInvInv(0, tbInvBooking.getMat_no(), whId);
				} else {
					inv012Dao.updateTbInvInv(tbInvBooking.getBooking_qty(), tbInvBooking.getMat_no(), whId);
				}
				
				tbInvBooking.setBooking_qty(0);
				tbInvBooking.setMd_time(new Date());
				tbInvBooking.setMd_user(getLoginUser().getUsername());
				inv012Dao.updateBooking(tbInvBooking);
				
			}catch(Exception e){throw new NomsException("更新TB_INV_INV 錯誤："+e.fillInStackTrace());
			}
		}
		
		try{
		 inv012Dao.updateTbInvMaterialOpt(optId, status, user, today);
		}catch(Exception e){throw new NomsException("更新TB_INV_MATERIALOPT 錯誤："+e.fillInStackTrace());}
	}
	public List<TbInvMrD> searchTbInvMrDMatNo(String optId) throws NomsException{
		return inv012Dao.searchTbInvMrDMatNo(optId);
	}
	@Transactional
	public int updateTbInvBooking(String optId,List<TbInvMrD> matNoList,String whId) throws Exception{
		Iterator<TbInvMrD> iter=matNoList.iterator();
		int result=0;
		while(iter.hasNext()){
			TbInvMrD updateData=(TbInvMrD)iter.next();
			TbInvTxnDTO txnData=inv012Dao.searchCloseTxnQty(optId, updateData.getMAT_NO(),whId);
			result=inv012Dao.updateTbInvBooking(optId, updateData.getMAT_NO(), whId,txnData.getMrQty());
		}			
		return result;
	}
	@Transactional
	public int updateTbInvInv(List<TbInvMrD> matNoList,String whId) throws NomsException{
		Iterator<TbInvMrD> iter=matNoList.iterator();
		int result=0;
		while(iter.hasNext()){
			TbInvMrD updateData=(TbInvMrD)iter.next();
			TbInvTxnDTO txnData=inv012Dao.searchCloseTxnQty(updateData.getOPT_ID(), updateData.getMAT_NO(),whId);
			result=inv012Dao.updateTbInvInv(txnData.getMrQty(), updateData.getMAT_NO(), whId);
		}			
		return result;
	}
	
	public List<TbInvTxnDTO> searchTbInvTxn(String txnNo,String whId,String crUser,Date crSDate,Date crEDate,String txnType,String siteId){
		return inv012Dao.searchTbInvTxn(txnNo, whId, crUser, crSDate, crEDate, txnType, siteId);
	}
	
	public List<TbInvTxnDTO> searchWithInvSrlByTxnNo(String txnNo,String matNo,String dtlSeq){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("txnNo", txnNo);
		dataObj.put("matNo", matNo);
		dataObj.put("dtlSeq", dtlSeq);
		dataObj.put("txnType", "1");
		return inv012Dao.searchWithInvSrlByTxnNo(dataObj);
	}
	public List<TbInvTxnDTO> searchWithTxnNoForSp1(String txnNo){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("txnNo", txnNo);
		return inv012Dao.searchWithTxnNoForSp1(dataObj);
	}
	
	public List<TbInvTxnDTO> searchInvTxnPrintPageDetail(String txnNo){
		return inv012Dao.searchInvTxnPrintPageDetail(txnNo);
	}
	
	public List<TbInvMrDDTO> searchMrD(List<String> optId,String whId){
		List<TbInvMrDDTO> list = inv012Dao.searchMrD(optId, whId);;
		try{
			for(int i=0;i<list.size();i++){
				TbInvMrDDTO dto = (TbInvMrDDTO)list.get(i);
				try{
					dto.setTxnQty(BigDecimal.valueOf(inv012Dao.searchTxnMRSumQty(String.valueOf(dto.getOPT_ID()),dto.getMAT_NO(), String.valueOf(dto.getDTL_SEQ()))));
					if("S".equals(dto.getCtrlType())){
						dto.setDiffQty(1);//序號件只會有一筆
					}else{
						dto.setDiffQty(dto.getQTY()-dto.getTxnQty().intValue());
					}
				}catch(Exception e){
					dto.setDiffQty(dto.getQTY());
				}
				list.set(i, dto);
			}
		}catch(NullPointerException e){}
		return list;
		
	}
	public TbInvMaterial getMatName(String matNo){
		return inv012Dao.getMatName(matNo);
	}
	public TbInvMaterialOpt searchWhId(String optId){
		return inv012Dao.searchWhId(optId);
	}
	public String selectTxnNoToday(String txnNo,String personnel){
		String todayMaxTrNo="",returnSeqNo="",nowdate="",genInitSeq="001",genInitFirstWord="MD",plusOneSeqString="",domainId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");    
		nowdate = sdf.format(new Date());    
		TbInvTxnDTO todayMaxTxnNoDTO  = inv012Dao.selectMaxSeq(txnNo);
		DeptDTO dept=invPublicUtilDao.searchDeptById(invPublicUtilDao.getPersonnelDeptId(personnel).getHrDeptId());
		TbOrgDomain domain=invPublicUtilDao.selectTbOrgDomainByPrimaryKey(dept.getDOMAIN());
		domainId=domain.getPARENT();
		try {
			
			todayMaxTrNo=todayMaxTxnNoDTO.getTxn_no();
			if ("".equals(todayMaxTrNo))
				returnSeqNo = genInitFirstWord + domainId + nowdate + genInitSeq;
			else
				todayMaxTrNo = todayMaxTrNo.substring(todayMaxTrNo.length()-3,
						todayMaxTrNo.length() );
			
			int plusOneSeq = Integer.valueOf(todayMaxTrNo);
			plusOneSeqString = String.format("%03d",plusOneSeq + 1);			
			returnSeqNo = genInitFirstWord + domainId + nowdate + plusOneSeqString;
		} catch (NullPointerException e) {
			returnSeqNo = genInitFirstWord + domainId +nowdate + genInitSeq;
		}
		return returnSeqNo;
	}
	@Transactional
	public int updateOpt(TbInvMaterialOpt record){
		return inv012Dao.updateOpt(record);
	}
	@Transactional
	public int updateBooking(HashMap<String,Object> dataObj){
		return inv012Dao.updateBooking(dataObj);
	}
	@Transactional
	public int updateInvInv(HashMap<String,Object> dataObj){
		return inv012Dao.updateInvInv(dataObj);
	}
	@Transactional
	public void updateSrl(Long srlNo) throws NomsException{
		try{
		String loginUser=getLoginUser().getUsername();
		Date today=invPublicUtilService.getToday();
		TbInvSrlExample example=new TbInvSrlExample();  
		TbInvSrl record=new TbInvSrl();
		example.createCriteria().andSrl_noEqualTo(srlNo);
		record.setWh_id("ON_HAND");
		record.setMd_time(today);
		record.setMd_user(loginUser);
		inv012Dao.updateTbInvSrl(example, record);
		}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
	}
	@Transactional
	public int insertInvTxn(TbInvTxn record){
		return inv012Dao.insertInvTxn(record);
	}
	public List<TbInvMaterialOpt> selectInvOpt(String optId){
		return inv012Dao.selectInvOpt(optId);
	}
	@Transactional
	public int insertInvOnhand(TbInvOnhand record){
		return inv012Dao.insertInvOnhand(record);
	}
	@Transactional
	public int insertInvMrAct(TbInvMrAct record){
		return inv012Dao.insertInvMrAct(record);
	}
	public List<TbInvTxn> selectInvTxn(String txnNo){
		return inv012Dao.selectInvTxn(txnNo);
	}
	public List<TbInvSrl> selectTagNo(String matNo,String whId){
		return inv012Dao.selectTagNo(matNo, whId);
	}
	public List<TbInvSrl> selectFaNo(String matNo,String whId,String tagNo,String venSn){
		return inv012Dao.selectFaNo(matNo, whId, tagNo, venSn);
	}
	
	public List<TbInvMrDDTO> searchMrDOptId(Set<String> optId){
		return inv012Dao.searchMrDOptId(optId);
	}
	
	public List<TbInvTxnDTO> searchLessQty(HashMap<String,Object> dataObj){
		return inv012Dao.searchLessQty(dataObj);
	}
	
	public List<TbInvTxnDTO> searchTxnDetailByNo(String txnNo){
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("txnNo", txnNo);
		return inv012Dao.searchTxnDetailByNo(dataObj);
	}
	@Transactional
	public void insertToTable(String msArray,Date today,String txnNo) throws NomsException ,Exception{
		String loginUser=getLoginUser().getUsername();
		JSONArray jsonArray = new JSONArray(msArray);
		Set<String> optIdList = new HashSet<String>();
		try{
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);				
				optIdList.add(obj.getString("optId"));
				TbInvInvDTO invData=getStdQtyByMatNo(obj.getString("txnMatNo"), obj.getString("whId"));
				try{
					if(obj.getInt("diffQty")>invData.getStd_qty()){
						throw new NomsException("實發料號： "+obj.getString("txnMatNo")+" 實際庫存數不足");
					}
				}catch(Exception e){throw new NomsException("實發料號： "+obj.getString("txnMatNo")+" 實際庫存數不足");}
				//更新TB_INV_BOOKING
				try{
					HashMap<String, Object> dataObj = new HashMap<String,Object>();
					dataObj.put("optId", obj.getString("optId"));
					dataObj.put("actType", "1");
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("whId", obj.getString("whId"));
					dataObj.put("qty", obj.getString("diffQty"));
					inv012Dao.updateBooking(dataObj);
				}catch(Exception e){throw new NomsException("更新TB_INV_BOOKING 錯誤："+e.getLocalizedMessage());}
				//更新TB_INV_INV
				try{
					HashMap<String, Object> dataObj = new HashMap<String,Object>();
					dataObj.put("matNo", obj.getString("txnMatNo"));//實際發料料號
					dataObj.put("whId", obj.getString("whId"));
					dataObj.put("qty", obj.getString("diffQty"));
					dataObj.put("mdTime", today);
					dataObj.put("mdUser", loginUser);
					inv012Dao.updateInvInv(dataObj);
					//再多扣掉申請料號的booking數
					inv012Dao.updateTbInvInv(Integer.valueOf(obj.getString("diffQty")), obj.getString("matNo"), obj.getString("whId"));
				}catch(Exception e){throw new NomsException("更新TB_INV_INV 錯誤："+e.getLocalizedMessage());}
				//寫入TB_INV_TXN
				try{
					TbInvTxn record = new TbInvTxn();
					record.setTxn_type(new Byte("1"));
					record.setTxn_no(txnNo);
					record.setMat_no(obj.getString("txnMatNo"));//實際發料料號
					record.setRef_act_no(obj.getString("optId"));
					record.setWh_id(obj.getString("whId"));
					List<TbSysLookup> list = invPublicUtilDao.getLookupByTypeAndName("MAT_STATUS", "可用品");// 可用品
					TbSysLookup lookupData = (TbSysLookup) list.get(0);
					record.setMat_status(new Byte(lookupData.getCODE()));
					record.setQty(Integer.valueOf(obj.getString("diffQty")));
					try{//沒有值＝非序號件 就不鳥他
						//List<TbInvSrl> srlList=this.selectFaNo(obj.getString("matNo"), obj.getString("matNo"), "", "");
						record.setSrl_no(Long.valueOf(obj.getString("srlNo")));
						record.setFa_no(obj.getString("faNo"));
					}catch(Exception e){}
					try{
						List<TbInvMaterialOpt> optList=this.selectInvOpt(obj.getString("optId"));
						record.setSite_id(optList.get(0).getSITE_ID());
						record.setOrder_no(optList.get(0).getORDER_ID());
					}catch(Exception e){}
					record.setDtl_seq(Long.valueOf(obj.getString("dtlSeq")));
					record.setCr_user(loginUser);
					record.setCr_time(today);
					record.setMro_rt_user(obj.getString("prPerson"));//領料人員
					record.setSend_rcv_user(obj.getString("msPerson"));//發料人員
					inv012Dao.insertInvTxn(record);
				}catch(Exception e){throw new NomsException("更新TB_INV_TXN 錯誤："+e.getLocalizedMessage());}
				try{
					TbInvMaterial matData=inv012Dao.getMaterial(obj.getString("txnMatNo"));
					if("S".equals(matData.getCtrl_type())){
//						List<TbInvSrl> srlList=this.selectFaNo(obj.getString("txnMatNo"), obj.getString("whId"), "", "");
//						if(srlList.size()>0){
						try{
							TbInvSrlExample example=new TbInvSrlExample();  
							TbInvSrl record=new TbInvSrl();
							example.createCriteria().andSrl_noEqualTo(Long.valueOf(obj.getString("srlNo")));
							record.setWh_id("ON_HAND");
							record.setSite_id("");
							record.setMd_time(today);
							record.setMd_user(loginUser);
							inv012Dao.updateTbInvSrl(example, record);
						}catch(NullPointerException e){}
//						}
					}
				}catch(Exception e){throw new NomsException("更新TB_INV_SRL 錯誤："+e.getLocalizedMessage());}
				//寫入TB_INV_ONHAND
				try{
					TbInvOnhand record = new TbInvOnhand();
					record.setTxn_type(new Byte("1"));
					record.setTxn_no(txnNo);
					try{
						List<TbInvMaterialOpt> optList=this.selectInvOpt(obj.getString("optId"));
						//record.setSite_id(optList.get(0).getSITE_ID());
						record.setOrder_no(optList.get(0).getORDER_ID());
					}catch(Exception e){}
					record.setWh_id(obj.getString("whId"));
					record.setMat_no(obj.getString("txnMatNo"));
					record.setMat_status(new Byte("1"));
					record.setQty(Integer.valueOf(obj.getString("diffQty")));
					record.setOnhand_qty(Integer.valueOf(obj.getString("diffQty")));
					try{
						record.setSrl_no(Long.valueOf(obj.getString("srlNo")));
						record.setFa_no(obj.getString("faNo"));
					}catch(Exception e){}
					record.setCr_user(loginUser);
					record.setCr_time(today);
					record.setMd_time(today);
					record.setMd_user(loginUser);
					inv012Dao.insertInvOnhand(record);
				}catch(Exception e){throw new NomsException("更新TB_INV_ONHAND 錯誤："+e.getLocalizedMessage());}
				//寫入TB_INV_MR_ACT
				try{
					TbInvMrAct record = new TbInvMrAct();
						record.setDTL_SEQ(Long.valueOf(obj.getString("dtlSeq")));
						record.setTXN_NO(txnNo);
						record.setOPT_ID(obj.getString("optId"));
						record.setMAT_NO(obj.getString("txnMatNo"));
						try{
							record.setSRL_NO(Long.valueOf(obj.getString("srlNo")));
						}catch(Exception e){}
						try {
							record.setQTY(Integer.valueOf(obj.getString("diffQty")));
						} catch (NumberFormatException e) {
							record.setQTY(0);
						} catch (NullPointerException e) {
							record.setQTY(0);
						}
						record.setCR_USER(loginUser);
						record.setCR_TIME(today);
						record.setRCV_USER(obj.getString("prPerson"));
						record.setRLS_USER(obj.getString("msPerson"));
						inv012Dao.insertInvMrAct(record);
				}catch(Exception e){throw new NomsException("更新TB_INV_MR_ACT 錯誤："+e.getLocalizedMessage());}
			}
			//更新TB_INV_MATERIAL_OPT
			try{
				String status = "發料完成", statusForMrIng = "發料中";
				TbInvMaterialOpt record = new TbInvMaterialOpt();
				TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
				try {
					List<TbSysLookup> list = invPublicUtilService.getLookupByTypeAndName("INV_MR_STATUS", status);
					TbSysLookup lookupData = (TbSysLookup) list.get(0);
					record.setSTATUS(lookupData.getCODE());
				} catch (IndexOutOfBoundsException e) {}// 避免領料數大於的情形
				catch (NullPointerException e) {}
				try {
					List<TbSysLookup> list = invPublicUtilService.getLookupByTypeAndName("INV_MR_STATUS", statusForMrIng);
					TbSysLookup lookupData = (TbSysLookup) list.get(0);
					recordMring.setSTATUS(lookupData.getCODE());
				} catch (IndexOutOfBoundsException e) {}// 避免領料數大於的情形
				catch (NullPointerException e) {}
				Iterator<String> it = optIdList.iterator();
				while (it.hasNext()) {
					record.setOPT_ID(it.next());
					record.setOPT_TYPE("MRQ");
					record.setMD_TIME(invPublicUtilService.getToday());
					record.setMD_USER(getLoginUser().getUsername());
					inv012Dao.updateOpt(record); // 先更新所有的optId狀態成發料完成
				}
				// 取得此optId清單所有的資料
				List<TbInvMrDDTO> mrDList = inv012Dao.searchMrDOptId(optIdList);
				for (int i = 0; i < mrDList.size(); i++) {
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					TbInvMrDDTO mrD = (TbInvMrDDTO) mrDList.get(i);
					dataObj.put("txnType", "1");
					dataObj.put("optId", mrD.getOPT_ID());
					dataObj.put("matNo", mrD.getMAT_NO());
					dataObj.put("qty", mrD.getQTY());
					List<TbInvTxnDTO> list = inv012Dao.searchLessQty(dataObj);
					try {
						for(int iTxn=0;iTxn<list.size();iTxn++){
							TbInvTxnDTO dto=list.get(iTxn);
							if(mrD.getOPT_ID().equals(dto.getRef_act_no())){
								if(mrD.getQTY()>dto.getQty()){
									recordMring.setOPT_ID(mrD.getOPT_ID());
									recordMring.setOPT_TYPE("MRQ");
									recordMring.setMD_TIME(invPublicUtilService.getToday());
									recordMring.setMD_USER(getLoginUser().getUsername());
									inv012Dao.updateOpt(recordMring);
								}
							}
						}	
					} catch (IndexOutOfBoundsException e) {
					}// 如果無資料
					catch (NullPointerException e) {
					}// 如果無資料
				}
			}catch(Exception e){throw new NomsException("更新TB_INV_MATERIAL_OPT 錯誤："+e.getLocalizedMessage());}
		}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
	}
	/**
	 * 提供外部呼叫
	 * param1 String:交易單號(外部產生後傳入的單號)
	 * param2 String:領料單號
	 * param3 String:料號
	 * param4 int:發料數
	 * param5 String:發料倉
	 * param6 String:領料人員代碼
	 * param7 String:發料人員代碼
	 * */
	@Transactional
	public void msCallable(String msArray) throws NomsException, Exception {
		Long dtlSeq = null;
		Set<String> optIdList = new HashSet<String>();
		// int txnQty=0;
		String loginUser = getLoginUser().getUsername();
		Date today = invPublicUtilService.getToday();
		JSONArray jsonArray = new JSONArray(msArray);
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TbInvInvDTO invData=getStdQtyByMatNo(obj.getString("txnMatNo"), obj.getString("whId"));
				try{
					if(obj.getInt("prCnt")>invData.getStd_qty()){
						throw new NomsException("領料單號： "+obj.getString("prId")+"實發料號： "+obj.getString("txnMatNo")+" 發料數申請大於庫存數");
					}
				}catch(Exception e){throw new NomsException("領料單號： "+obj.getString("prId")+"實發料號： "+obj.getString("txnMatNo")+" 發料數申請大於庫存數");}
				
				//TbInvMrDDTO mrDto = inv012Dao.searchMrDSumQty(obj.getString("prId"), obj.getString("matNo"));
				dtlSeq = Long.valueOf(obj.getString("dtlSeq"));
				// 更新TB_INV_BOOKING
				try {
					optIdList.add(obj.getString("prId"));
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					dataObj.put("optId", obj.getString("prId"));
					dataObj.put("actType", "1");
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("whId", obj.getString("whId"));
					dataObj.put("qty", obj.getInt("prCnt"));
					inv012Dao.updateBooking(dataObj);
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_BOOKING 錯誤："
							+ e.getLocalizedMessage());
				}
				// 更新TB_INV_INV
				try {
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					dataObj.put("matNo", obj.getString("txnMatNo"));
					dataObj.put("whId", obj.getString("whId"));
					dataObj.put("qty", obj.getInt("prCnt"));
					dataObj.put("mdTime", today);
					dataObj.put("mdUSer", loginUser);
					inv012Dao.updateInvInv(dataObj);
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_INV 錯誤："
							+ e.getLocalizedMessage());
				}
				// 寫入TB_INV_TXN
				try {
					TbInvTxn record = new TbInvTxn();
					record.setTxn_type(new Byte("1"));
					record.setMat_no(obj.getString("txnMatNo"));
					record.setTxn_no(obj.getString("txnNo"));
					record.setRef_act_no(obj.getString("prId"));
					record.setWh_id(obj.getString("whId"));
					List<TbSysLookup> list = invPublicUtilDao
							.getLookupByTypeAndName("MAT_STATUS", "可用品");// 可用品
					TbSysLookup lookupData = (TbSysLookup) list.get(0);
					record.setMat_status(new Byte(lookupData.getCODE()));
					record.setQty(obj.getInt("prCnt"));
					try {// 沒有值＝非序號件 就不鳥他
						//List<TbInvSrl> srlList = this.selectFaNo(obj.getString("matNo"), obj.getString("whId"),"", "");
						record.setSrl_no(Long.valueOf(obj.getString("srlNo")));
						record.setFa_no(obj.getString("faNo"));
					} catch (Exception e) {
					}
					try {
						List<TbInvMaterialOpt> optList = this.selectInvOpt(obj
								.getString("prId"));
						record.setSite_id(optList.get(0).getSITE_ID());
						record.setOrder_no(optList.get(0).getORDER_ID());
					} catch (Exception e) {
					}
					record.setDtl_seq(dtlSeq);
					record.setCr_user(loginUser);
					record.setCr_time(today);
					record.setMro_rt_user(obj.getString("prUser"));
					record.setSend_rcv_user(obj.getString("msUser"));
					inv012Dao.insertInvTxn(record);
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_TXN 錯誤："
							+ e.getLocalizedMessage());
				}
				// 更新TB_INV_SRL
				try {
					TbInvMaterial matData = inv012Dao.getMaterial(obj
							.getString("txnMatNo"));
					if ("S".equals(matData.getCtrl_type())) {
						TbInvSrl srlData = inv012Dao.getSrlData(Long.valueOf(obj.getString("srlNo")));
						try{
							srlData.getSrl_no();
						}catch(Exception e){throw new NomsException("領料單號： "+obj.getString("prId")+"實發料號： "+obj.getString("txnMatNo")+" SRL_NO不存在於貼標主檔");}
							TbInvSrlExample example = new TbInvSrlExample();
							TbInvSrl record = new TbInvSrl();
							example.createCriteria().andSrl_noEqualTo(Long.valueOf(obj.getString("srlNo")));
							record.setWh_id("ON_HAND");
							record.setSite_id("");
							record.setMd_time(today);
							record.setMd_user(loginUser);
							inv012Dao.updateTbInvSrl(example, record);
					}
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_SRL 錯誤："
							+ e.getLocalizedMessage());
				}
				// 寫入TB_INV_ONHAND
				try {
					TbInvOnhand record = new TbInvOnhand();
					record.setTxn_type(new Byte("1"));
					record.setTxn_no(obj.getString("txnNo"));
					try {
						List<TbInvMaterialOpt> optList = this.selectInvOpt(obj
								.getString("prId"));
						record.setSite_id(optList.get(0).getSITE_ID());
						record.setOrder_no(optList.get(0).getORDER_ID());
					} catch (Exception e) {
					}
					record.setWh_id(obj.getString("whId"));
					record.setMat_no(obj.getString("txnMatNo"));
					record.setMat_status(new Byte("1"));
					record.setQty(obj.getInt("prCnt"));
					record.setOnhand_qty(obj.getInt("prCnt"));
					try {
						record.setSrl_no(Long.valueOf(obj.getString("srlNo")));
						record.setFa_no(obj.getString("faNo"));
					} catch (Exception e) {
					}
					record.setCr_user(loginUser);
					record.setCr_time(today);
					record.setMd_time(today);
					record.setMd_user(loginUser);
					inv012Dao.insertInvOnhand(record);
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_ONHAND 錯誤："
							+ e.getLocalizedMessage());
				}
				// 寫入TB_INV_MR_ACT
				try {
					TbInvMrAct record = new TbInvMrAct();
					record.setDTL_SEQ(dtlSeq);
					record.setTXN_NO(obj.getString("txnNo"));
					record.setOPT_ID(obj.getString("prId"));
					record.setMAT_NO(obj.getString("txnMatNo"));
					try {
						record.setSRL_NO(Long.valueOf(obj.getString("srlNo")));
					} catch (Exception e) {
					}
					try {
						record.setQTY(Integer.valueOf(obj.getInt("prCnt")));
					} catch (NumberFormatException e) {
						record.setQTY(0);
					} catch (NullPointerException e) {
						record.setQTY(0);
					}
					record.setCR_USER(loginUser);
					record.setCR_TIME(today);
					record.setRCV_USER(obj.getString("prUser"));
					record.setRLS_USER(obj.getString("msUser"));
					inv012Dao.insertInvMrAct(record);
					// }
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_MR_ACT 錯誤："
							+ e.getLocalizedMessage());
				}
				// 更新TB_INV_MATERIAL_OPT
				try {
					String status = "發料完成", statusForMrIng = "發料中";
					TbInvMaterialOpt record = new TbInvMaterialOpt();
					TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
					try {
						List<TbSysLookup> list = invPublicUtilService
								.getLookupByTypeAndName("INV_MR_STATUS", status);
						TbSysLookup lookupData = (TbSysLookup) list.get(0);
						record.setSTATUS(lookupData.getCODE());
					} catch (IndexOutOfBoundsException e) {
					}// 避免領料數大於的情形
					catch (NullPointerException e) {
					}
					try {
						List<TbSysLookup> list = invPublicUtilService
								.getLookupByTypeAndName("INV_MR_STATUS",
										statusForMrIng);
						TbSysLookup lookupData = (TbSysLookup) list.get(0);
						recordMring.setSTATUS(lookupData.getCODE());
					} catch (IndexOutOfBoundsException e) {
					}// 避免領料數大於的情形
					catch (NullPointerException e) {
					}
					Iterator<String> it = optIdList.iterator();
					while (it.hasNext()) {
						record.setOPT_ID(it.next());
						record.setOPT_TYPE("MRQ");
						record.setMD_TIME(invPublicUtilService.getToday());
						record.setMD_USER(getLoginUser().getUsername());
						inv012Dao.updateOpt(record); // 先更新所有的optId狀態成發料完成
					}
					// 取得此optId清單所有的資料
					List<TbInvMrDDTO> mrDList = inv012Dao
							.searchMrDOptId(optIdList);
					for (int iMrD = 0; iMrD < mrDList.size(); iMrD++) {
						HashMap<String, Object> dataObj = new HashMap<String, Object>();
						TbInvMrDDTO mrD = (TbInvMrDDTO) mrDList.get(iMrD);
						dataObj.put("txnType", "1");
						dataObj.put("optId", mrD.getOPT_ID());
						dataObj.put("matNo", mrD.getMAT_NO());
						dataObj.put("qty", mrD.getQTY());
						List<TbInvTxnDTO> list = inv012Dao
								.searchLessQty(dataObj);
						try {
							for (int iTxn = 0; iTxn < list.size(); iTxn++) {
								TbInvTxnDTO dto = list.get(iTxn);
								if (mrD.getOPT_ID().equals(dto.getRef_act_no())) {
									if (mrD.getQTY() > dto.getQty()) {
										recordMring.setOPT_ID(mrD.getOPT_ID());
										recordMring.setOPT_TYPE("MRQ");
										recordMring
												.setMD_TIME(invPublicUtilService
														.getToday());
										recordMring.setMD_USER(getLoginUser()
												.getUsername());
										inv012Dao.updateOpt(recordMring);
									}
								}
							}
						} catch (IndexOutOfBoundsException e) {
						}// 如果無資料
						catch (NullPointerException e) {
						}// 如果無資料
					}
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_MATERIAL_OPT 錯誤："
							+ e.getLocalizedMessage());
				}
			}
		} catch (Exception e) {
			throw new NomsException(e.getLocalizedMessage());
		}
	}
	public TbInvMaterial getMaterial(String matNo) {
		return inv012Dao.getMaterial(matNo);
	}
	
	public TbInvInvDTO getStdQtyByMatNo(String matNo,String whId) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		return inv012Dao.getStdQtyByMatNo(dataObj);
	}
}
