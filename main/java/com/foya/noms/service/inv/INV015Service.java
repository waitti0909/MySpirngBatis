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

import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvSrlExample;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.inv.INV012Dao;
import com.foya.noms.dao.inv.INV015Dao;
import com.foya.noms.dao.inv.InvPublicUtilDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvOnhandDTO;
import com.foya.noms.dto.inv.TbInvRtDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.BaseService;

@Service
public class INV015Service extends BaseService{
	@Autowired
	private INV015Dao inv015Dao;
	@Autowired
	private InvPublicUtilDao invPublicUtilDao;
	@Autowired
	public PersonnelDao personnelDao;
	@Autowired
	private InvPublicUtilService invPublicUtilService;
	@Autowired
	private INV012Dao inv012Dao;
	public List<TbInvMaterialOptDTO> search(HashMap<String,Object> dataObj){
		return inv015Dao.search(dataObj);
	}
	
	public List<TbInvRtDDTO> searchUTbInvRtD(HashMap<String,Object> dataObj){
		List<TbInvRtDDTO> list = inv015Dao.searchUTbInvRtD(dataObj);
		try{
			for(int i=0;i<list.size();i++){
				TbInvRtDDTO dto = (TbInvRtDDTO)list.get(i);
				try{
					dto.setRtQty(inv015Dao.searchTxnRTSumQty(String.valueOf(dataObj.get("optId")),dto.getMAT_NO(), String.valueOf(dto.getDTL_SEQ())));
				}catch(Exception e){}
				list.set(i, dto);
			}
		}catch(NullPointerException e){}
		return list;
		
	}
	
	public List<TbInvRtDDTO> searchFs(HashMap<String,Object> dataObj){
		List<TbInvRtDDTO> TbInvMrDDTOList = inv015Dao.searchFs(dataObj);
		TbInvMaterialOptExample example = new TbInvMaterialOptExample();
		String whId=(String)dataObj.get("whId");
		
		for(int i=0;i<TbInvMrDDTOList.size();i++){			
		
			TbInvRtDDTO dto = (TbInvRtDDTO)TbInvMrDDTOList.get(i);
			
			example = new TbInvMaterialOptExample();
			example.createCriteria().andOPT_IDEqualTo(String.valueOf(dto.getOPT_ID())).andOPT_TYPEEqualTo("RTN");
			List<TbInvMaterialOpt> optList = inv015Dao.selectInvOpt(example);
			try{
				if(optList.size()>0){//抓取opt資料
					dto.setTbInvMaterialOpt(optList.get(0));	
				}
			}catch(Exception e){}
			HashMap<String,Object> dataObjTxn = new HashMap<String,Object>();
			dataObjTxn.put("optId", dto.getOPT_ID());
			dataObjTxn.put("matNo", dto.getMAT_NO());
			dataObjTxn.put("dtlSeq", dto.getDTL_SEQ());
			List<TbInvTxnDTO> txnList = inv015Dao.searchTbInvTxnRTDetailNonPage(dataObjTxn);
			int txnSum=0;
			for(int iTxn=0;iTxn<txnList.size();iTxn++){
				TbInvTxn txn=(TbInvTxn)txnList.get(iTxn);
				txnSum+=txn.getQty();//計算TXN金額加總
			}
			try{
				List<TbSysLookup> lookup = invPublicUtilDao.getLookupByTypeAndCode("INV_RN_RESN", dto.getRTN_REASON());
				if(lookup.size()>0){//抓退料原因
					TbSysLookup code=(TbSysLookup)lookup.get(0);
					dto.setRnResnDscr(code.getNAME());	
				}
			}catch(Exception e){}
			dto.setTxnQty(BigDecimal.valueOf(txnSum));
			dto.setWhId(whId);
				TbInvMrDDTOList.set(i,dto);
		}
		return TbInvMrDDTOList;
	}
	
	public List<TbInvTxnDTO> searchTbInvTxnExample(String optId,String matNo,String dtlSeq){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("dtlSeq", dtlSeq);
		 return inv015Dao.searchTbInvTxnRTDetail(dataObj);
	}
	public List<TbInvMrD> searchTbInvMrDMatNo(String optId) throws NomsException{
		return inv015Dao.searchTbInvMrDMatNo(optId);
	}
	
	@Transactional
	public int updateTbInvBooking(String optId,List<TbInvMrD> matNoList,String whId) throws Exception{
		Iterator<TbInvMrD> iter=matNoList.iterator();
		int result=0;
		while(iter.hasNext()){
			TbInvMrD updateData=(TbInvMrD)iter.next();
			result=inv015Dao.updateTbInvBooking(optId, updateData.getMAT_NO(), whId);
		}			
		return result;
	}
	@Transactional
	public int updateTbInvInv(List<TbInvMrD> matNoList,String whId) throws NomsException{
		Iterator<TbInvMrD> iter=matNoList.iterator();
		int result=0;
		while(iter.hasNext()){
			TbInvMrD updateData=(TbInvMrD)iter.next();
			result=inv015Dao.updateTbInvInv(updateData.getQTY(), updateData.getMAT_NO(), whId);
		}			
		return result;
	}
	
	public List<TbInvTxn> searchTbInvTxn(String txnNo,String whId,String crUser,Date crSDate,Date crEDate,String txnType,String siteId){
		return inv015Dao.searchTbInvTxn(txnNo, whId, crUser, crSDate, crEDate, txnType, siteId);
	}
	
	public List<TbInvTxnDTO> searchInvTxnPrintPageDetail(String txnNo){
		return inv015Dao.searchInvTxnPrintPageDetail(txnNo);
	}
	
	public String selectTxnNoToday(String txnNo,String personnel){
		String todayMaxTrNo="",returnSeqNo="",nowdate="",genInitSeq="001",genInitFirstWord="RC",plusOneSeqString="",domainId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");    
		nowdate = sdf.format(new Date());    
		TbInvTxnDTO todayMaxTxnNoDTO  = inv015Dao.selectMaxSeq(txnNo);
		DeptDTO dept=invPublicUtilDao.searchDeptById(invPublicUtilDao.getPersonnelDeptId(personnel).getHrDeptId());
		TbOrgDomain domain=invPublicUtilDao.selectTbOrgDomainByPrimaryKey(dept.getDOMAIN());
		domainId=domain.getID();
		try {
			todayMaxTrNo=todayMaxTxnNoDTO.getTxn_no();
			if ("".equals(todayMaxTrNo))
				returnSeqNo = genInitFirstWord + domainId + nowdate + genInitSeq;
			else
				todayMaxTrNo = todayMaxTrNo.substring(todayMaxTrNo.length()-3,
						todayMaxTrNo.length() );
			log.debug("1 todayMaxTrNo:"+todayMaxTrNo);
			int plusOneSeq = Integer.valueOf(todayMaxTrNo);
			plusOneSeqString = String.format("%03d",plusOneSeq + 1);
			log.debug("plusOneSeqString:"+plusOneSeqString);
			returnSeqNo = genInitFirstWord + domainId + nowdate + plusOneSeqString;
		} catch (NullPointerException e) {
			returnSeqNo = genInitFirstWord + domainId +nowdate + genInitSeq;
			log.debug("returnSeqNo:"+returnSeqNo);
		}
		return returnSeqNo;
	}
	public TbInvMaterialOpt searchWhId(String optId){
		return inv015Dao.searchWhId(optId);
	}
	@Transactional
	public int updateInvInv(HashMap<String,Object> dataObj){
		return inv015Dao.updateInvInv(dataObj);
	}
	@Transactional
	public int insertInvTxn(TbInvTxn record){
		return inv015Dao.insertInvTxn(record);
	}
	@Transactional
	public void updateSrl(Long srlNo)throws NomsException{
		try{
			String loginUser=getLoginUser().getUsername();
			Date today=invPublicUtilService.getToday();
			TbInvSrlExample example=new TbInvSrlExample();  
			TbInvSrl record=new TbInvSrl();
			example.createCriteria().andSrl_noEqualTo(srlNo);
			record.setWh_id("");
			record.setMd_time(today);
			record.setMd_user(loginUser);
			inv012Dao.updateTbInvSrl(example, record);
		}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
	}
	public List<TbInvMaterialOpt> selectInvOpt(String optId){
		return inv015Dao.selectInvOpt(optId);
	}
	@Transactional
	public int updateOnhandRTNQty(String optId,String matNo,String qty,String orderNo,
			String user,Date today,String txnType,String whId){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("qty", qty);
		dataObj.put("orderNo", orderNo);
		dataObj.put("user", user);
		dataObj.put("mdTime", today);
		dataObj.put("txnType", txnType);
		dataObj.put("whId", whId);
		return inv015Dao.updateOnhandRTNQty(dataObj);
	}
	
	@Transactional
	public int updateOnhandRTNQtyForZero(String optId,String matNo,String orderNo,
			String user,Date today,String txnType,String whId){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		dataObj.put("orderNo", orderNo);
		dataObj.put("user", user);
		dataObj.put("mdTime", today);
		dataObj.put("txnType", txnType);
		dataObj.put("whId", whId);
		return inv015Dao.updateOnhandRTNQtyForZero(dataObj);
	}
	public List<TbInvTxn> selectInvTxn(String txnNo){
		return inv015Dao.selectInvTxn(txnNo);
	}
	

	/**
	 * 搜尋 Group Txn Data By Grid
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	public List<TbInvTxnDTO> getGroupInvTxnDataByGrid(String txnNo, String whId,
			String createUser, Date strDate, Date endDate) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txnNo", txnNo);
		map.put("whId", whId);
		map.put("createUser", createUser);
		map.put("strDate", strDate);
		map.put("endDate", endDate);
		
		return inv015Dao.getGroupInvTxnDataByGrid(map);
	}
	
	/**
	 * 搜尋 Txn Data
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	public List<TbInvTxnDTO> getInvTxnData(String txnNo, String whId,
			String createUser, Date strDate, Date endDate) {
		
		return inv015Dao.getInvTxnData(txnNo, whId, createUser, strDate, endDate);
	}
	
	/**
	 * 搜尋 Txn Data By Grid
	 * 
	 * @param txnNo
	 *            單號
	 * @param whId
	 *            收料倉
	 * @param createUser
	 *            收料人員
	 * @param strDate
	 *            起始日期
	 * @param endDate
	 *            起訖日期
	 * @return List<TbInvTxnDTO>
	 */
	public List<TbInvTxnDTO> getInvTxnDataByGrid(String txnNo, String whId,
			String createUser, Date strDate, Date endDate){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("txnNo", txnNo);
		map.put("whId", whId);
		map.put("createUser", createUser);
		map.put("strDate", strDate);
		map.put("endDate", endDate);
		
		return inv015Dao.getInvTxnDataByGrid(map);
	}
	
	public List<TbInvRtDDTO> searchRtDOptId(Set<String> optId){
		return inv015Dao.searchRtDOptId(optId);
	}
	
	public List<TbInvTxnDTO> searchLessQty(HashMap<String,Object> dataObj){
		return inv015Dao.searchLessQty(dataObj);
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
				//更新TB_INV_INV
				try{
					HashMap<String,Object> dataObj=new HashMap<String,Object>(); 
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("whId", obj.getString("whId"));
					dataObj.put("mdTime", today);
					dataObj.put("mdUser", loginUser);
					if("1".equals(obj.getString("matStatus")))
						dataObj.put("stdQty", obj.getString("minusQty"));
					else if("2".equals(obj.getString("matStatus")))
						dataObj.put("wrdQty", obj.getString("minusQty"));
					else if("3".equals(obj.getString("matStatus")))
						dataObj.put("wspQty", obj.getString("minusQty"));
					String.valueOf(inv015Dao.updateInvInv(dataObj));
				}catch(Exception e){throw new NomsException("更新TB_INV_INV 錯誤："+e.getLocalizedMessage());}
				//寫入TB_INV_TXN
				try{
					TbInvTxn record = new TbInvTxn();
					record.setTxn_no(txnNo);
					record.setTxn_type(new Byte("2"));
					record.setRef_act_no(obj.getString("optId"));
					record.setWh_id(obj.getString("whId"));
					record.setMat_no(obj.getString("matNo"));
					record.setMat_status(new Byte(String.valueOf(obj.getString("matStatus"))));
					record.setRn_resn(obj.getString("rsn"));
					record.setMro_rt_user(obj.getString("rePerson"));
					record.setSend_rcv_user(obj.getString("callPerson"));
					record.setDtl_seq(Long.valueOf(obj.getString("dtlSeq")));
					try{
						record.setQty(Math.abs(Integer.valueOf(obj.getString("minusQty"))));
					}catch(NumberFormatException e){
						record.setQty(0);
					}catch(NullPointerException e){
						record.setQty(0);
					}
					try{
						record.setSrl_no(Long.valueOf(obj.getString("srlNo")));
						record.setFa_no(obj.getString("faNo"));
					}catch(Exception e){}
					try{						
						record.setSite_id(obj.getString("siteId"));
						record.setOrder_no(obj.getString("orderId"));
					}catch(Exception e){}
					record.setCr_user(loginUser);
					record.setCr_time(today);
					String.valueOf(inv015Dao.insertInvTxn(record));
				}catch(Exception e){throw new NomsException("寫入TB_INV_TXN 錯誤："+e.getLocalizedMessage());}
				//更新TB_INV_ONHAND
				try{
					//邏輯大幅修改 2015.03.24
					HashMap<String,Object> dataObj=new HashMap<String,Object>(); 
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("orderId", obj.getString("orderId"));
					TbInvOnhandDTO onHandDto=inv015Dao.getOnhandQty(dataObj);
					log.debug("minusQty: "+obj.get("minusQty"));
					log.debug(" transfer: "+Integer.valueOf(obj.getString("minusQty")) );
					if(onHandDto.getOnhand_qty()>=obj.getInt("minusQty")){
						log.debug("true");
						this.updateOnhandRTNQty(obj.getString("optId"), obj.getString("matNo"), obj.getString("minusQty"), obj.getString("orderId"), loginUser, today,"1",obj.getString("whId"));
					}else{
						log.debug("fasle");
						this.updateOnhandRTNQtyForZero(obj.getString("optId"), obj.getString("matNo"),  obj.getString("orderId"), loginUser, today,"1",obj.getString("whId"));
						this.updateOnhandRTNQty(obj.getString("optId"), obj.getString("matNo"),String.valueOf((int)obj.getInt("minusQty")-onHandDto.getOnhand_qty()), obj.getString("orderId"), loginUser, today,"3",obj.getString("whId"));
					}					
				}catch(Exception e){throw new NomsException("更新TB_INV_ONHAND 錯誤："+e.getLocalizedMessage());}
				//更新TB_INV_SRL
				try{
					TbInvMaterial matData=inv012Dao.getMaterial(obj.getString("matNo"));
					if("S".equals(matData.getCtrl_type())){
						//List<TbInvSrl> srlList=inv012Dao.selectFaNo(obj.getString("matNo"), obj.getString("whId"), "", "");
						//if(srlList.size()>0){
							TbInvSrlExample example=new TbInvSrlExample();  
							TbInvSrl record=new TbInvSrl();
							try{
								example.createCriteria().andSrl_noEqualTo(Long.valueOf(obj.getString("srlNo")));
								record.setWh_id(obj.getString("whId"));
								record.setSite_id("");
								record.setMd_time(today);
								record.setMd_user(loginUser);
								inv012Dao.updateTbInvSrl(example, record);
							}catch(NullPointerException e){log.debug("TB_INV_SRL中沒有該料號貼標資料!!");}
						//}
					}
				}catch(NullPointerException e){log.debug("TB_INV_MATERIAL中沒有該料號資料!!");}
				catch(Exception e){throw new NomsException("更新TB_INV_SRL 錯誤："+e.getMessage());}
			}
			// 更新TB_INV_MATERIAL_OPT
						String status = "退料完成", statusForMrIng = "退料中";
						TbInvMaterialOpt record = new TbInvMaterialOpt();
						TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
						try {
							List<TbSysLookup> list = invPublicUtilService
									.getLookupByTypeAndName("INV_RT_STATUS", status);
							TbSysLookup lookupData = (TbSysLookup) list.get(0);
							record.setSTATUS(lookupData.getCODE());
						} catch (IndexOutOfBoundsException e) {
						}// 避免領料數大於的情形
						catch (NullPointerException e) {
						}
						try {
							List<TbSysLookup> list = invPublicUtilService
									.getLookupByTypeAndName("INV_RT_STATUS",
											statusForMrIng);
							TbSysLookup lookupData = (TbSysLookup) list.get(0);
							recordMring.setSTATUS(lookupData.getCODE());
						} catch (IndexOutOfBoundsException e) {
						}// 避免領料數大於的情形
						catch (NullPointerException e) {
						}
						try {
							Iterator<String> it = optIdList.iterator();
							while (it.hasNext()) {
								record.setOPT_ID(it.next());
								record.setOPT_TYPE("RTN");
								record.setMD_TIME(invPublicUtilService.getToday());
								record.setMD_USER(getLoginUser().getUsername());
								inv012Dao.updateOpt(record); // 先更新所有的optId狀態成發料完成
							}
							// 取得此optId清單所有的資料
							List<TbInvRtDDTO> rtDList = inv015Dao
									.searchRtDOptId(optIdList);
							for (int iRtD = 0; iRtD < rtDList.size(); iRtD++) {
								HashMap<String, Object> dataObj = new HashMap<String, Object>();
								TbInvRtDDTO rtD = (TbInvRtDDTO) rtDList.get(iRtD);
								dataObj.put("txnType", "2");
								dataObj.put("optId", rtD.getOPT_ID());
								List<TbInvTxnDTO> list = inv015Dao
										.searchLessQty(dataObj);
								try {
									for (int iTxn = 0; iTxn < list.size(); iTxn++) {
										TbInvTxnDTO dto = list.get(iTxn);
										if (rtD.getOPT_ID().equals(dto.getRef_act_no())) {
											if (rtD.getQTY() > dto.getQty()) {
												recordMring.setOPT_ID(rtD.getOPT_ID());
												recordMring.setOPT_TYPE("RTN");
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
							throw new NomsException("更新TB_INV_MATERIAL_OPT："
									+ e.getLocalizedMessage());
						}
			
		}catch(Exception e){throw new NomsException(e.getLocalizedMessage());}
	}
	/**
	 * 提供外部呼叫
	 * param1 String:交易單號(外部產生後傳入的單號)
	 * param2 String:退料單號
	 * param3 String:料號
	 * param4 int:收料數
	 * param5 String:收料倉
	 * param6 int:實際收料狀態
	 * param7 String:退料原因代碼
	 * param8 String:退料人員代碼
	 * param9 String:收料人員代碼
	 * */
	@Transactional
	public void msCallable(String msArray) throws NomsException, Exception {
		Long dtlSeq = null;
		String loginUser = getLoginUser().getUsername();
		Date today = invPublicUtilService.getToday();
		Set<String> optIdList = new HashSet<String>();
		JSONArray jsonArray = new JSONArray(msArray);
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				optIdList.add(obj.getString("prId"));
				//TbInvRtDDTO mrDto = inv015Dao.searchRtDSumQty(obj.getString("prId"), obj.getString("matNo"));
				dtlSeq = Long.valueOf(obj.getString("dtlSeq"));
				// 更新TB_INV_INV
				try {
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("whId", obj.getString("whId"));
					if (1 == obj.getInt("matStatus"))
						dataObj.put("stdQty", obj.getInt("prCnt"));
					else if (2 == obj.getInt("matStatus"))
						dataObj.put("wrdQty", obj.getInt("prCnt"));
					else if (3 == obj.getInt("matStatus"))
						dataObj.put("wspQty", obj.getInt("prCnt"));
					dataObj.put("mdTime", today);
					dataObj.put("mdUSer", loginUser);
					String.valueOf(inv015Dao.updateInvInv(dataObj));
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_INV 錯誤："
							+ e.getLocalizedMessage());
				}
				// 寫入TB_INV_TXN
				try {
					TbInvTxn record = new TbInvTxn();
					record.setTxn_no(obj.getString("txnNo"));
					record.setTxn_type(new Byte("2"));
					record.setRef_act_no(obj.getString("prId"));
					record.setWh_id(obj.getString("whId"));
					record.setMat_no(obj.getString("matNo"));
					record.setMat_status(new Byte(obj.getString("matStatus")));
					record.setRn_resn(obj.getString("rnResn"));
					record.setMro_rt_user(obj.getString("prUser"));
					record.setSend_rcv_user(obj.getString("msUser"));
					record.setDtl_seq(Long.valueOf(dtlSeq));
					try {
						record.setQty(Math.abs(obj.getInt("prCnt")));
					} catch (NumberFormatException e) {
						record.setQty(0);
					} catch (NullPointerException e) {
						record.setQty(0);
					}
					try {// 沒有值＝非序號件 就不鳥他
							// List<TbInvSrl>
							// srlList=inv012Dao.selectFaNo(obj.getString("matNo"),
							// obj.getString("whId"), "", "");
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
					record.setCr_user(loginUser);
					record.setCr_time(today);
					String.valueOf(inv015Dao.insertInvTxn(record));
				} catch (Exception e) {
					throw new NomsException("寫入TB_INV_TXN 錯誤："
							+ e.getLocalizedMessage());
				}
				// 更新TB_INV_ONHAND
				// try{
				// String orderNo="";
				// try{
				// List<TbInvMaterialOpt> optList=this.selectInvOpt(prId);
				// orderNo=optList.get(0).getORDER_ID();
				// }catch(Exception e){}
				// this.updateOnhandRTNQty(prId, matNo, prCnt, orderNo,
				// loginUser, today);
				// }catch(Exception e){throw new
				// NomsException("更新TB_INV_ONHAND 錯誤："+e.getLocalizedMessage());}
				try {
					String orderNo = "";
					try {
						List<TbInvMaterialOpt> optList = this.selectInvOpt(obj
								.getString("prId"));
						orderNo = optList.get(0).getORDER_ID();
					} catch (Exception e) {
					}
					// 邏輯大幅修改 2015.03.24
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					dataObj.put("matNo", obj.getString("matNo"));
					dataObj.put("orderId", orderNo);
					TbInvOnhandDTO onHandDto = inv015Dao.getOnhandQty(dataObj);
					if (onHandDto.getOnhand_qty() >= obj.getInt("prCnt")) {
						this.updateOnhandRTNQty(obj.getString("prId"),
								obj.getString("matNo"),
								String.valueOf(obj.getInt("prCnt")), orderNo,
								loginUser, today, "1", obj.getString("whId"));
					} else {
						this.updateOnhandRTNQtyForZero(obj.getString("prId"),
								obj.getString("matNo"), orderNo, loginUser,
								today, "1", obj.getString("whId"));
						this.updateOnhandRTNQty(
								obj.getString("prId"),
								obj.getString("matNo"),
								String.valueOf(obj.getInt("prCnt")
										- onHandDto.getOnhand_qty()), orderNo,
								loginUser, today, "3", obj.getString("whId"));
					}
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_ONHAND 錯誤："
							+ e.getLocalizedMessage());
				}
				// 更新TB_INV_SRL
				try {
					TbInvMaterial matData = inv012Dao.getMaterial(obj
							.getString("matNo"));
					if ("S".equals(matData.getCtrl_type())) {
						// List<TbInvSrl>
						// srlList=inv012Dao.selectFaNo(obj.getString("matNo"),
						// obj.getString("whId"), "", "");
						// if(srlList.size()>0){
						TbInvSrlExample example = new TbInvSrlExample();
						TbInvSrl record = new TbInvSrl();
						try {
							example.createCriteria().andSrl_noEqualTo(
									Long.valueOf(obj.getString("srlNo")));
							record.setWh_id("");
							record.setMd_time(today);
							record.setMd_user(loginUser);
							inv012Dao.updateTbInvSrl(example, record);
						} catch (NullPointerException e) {
							log.debug("TB_INV_SRL中沒有該料號貼標資料!!");
						}
						// }
					}
				} catch (NullPointerException e) {
					log.debug("TB_INV_MATERIAL中沒有該料號資料!!");
				} catch (Exception e) {
					throw new NomsException("更新TB_INV_SRL 錯誤："
							+ e.getLocalizedMessage());
				}
			}
			// 更新TB_INV_MATERIAL_OPT
			String status = "退料完成", statusForMrIng = "退料中";
			TbInvMaterialOpt record = new TbInvMaterialOpt();
			TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
			try {
				List<TbSysLookup> list = invPublicUtilService
						.getLookupByTypeAndName("INV_RT_STATUS", status);
				TbSysLookup lookupData = (TbSysLookup) list.get(0);
				record.setSTATUS(lookupData.getCODE());
			} catch (IndexOutOfBoundsException e) {
			}// 避免領料數大於的情形
			catch (NullPointerException e) {
			}
			try {
				List<TbSysLookup> list = invPublicUtilService
						.getLookupByTypeAndName("INV_RT_STATUS",
								statusForMrIng);
				TbSysLookup lookupData = (TbSysLookup) list.get(0);
				recordMring.setSTATUS(lookupData.getCODE());
			} catch (IndexOutOfBoundsException e) {
			}// 避免領料數大於的情形
			catch (NullPointerException e) {
			}
			try {
				Iterator<String> it = optIdList.iterator();
				while (it.hasNext()) {
					record.setOPT_ID(it.next());
					record.setOPT_TYPE("RTN");
					record.setMD_TIME(invPublicUtilService.getToday());
					record.setMD_USER(getLoginUser().getUsername());
					inv012Dao.updateOpt(record); // 先更新所有的optId狀態成發料完成
				}
				// 取得此optId清單所有的資料
				List<TbInvRtDDTO> rtDList = inv015Dao
						.searchRtDOptId(optIdList);
				for (int iRtD = 0; iRtD < rtDList.size(); iRtD++) {
					HashMap<String, Object> dataObj = new HashMap<String, Object>();
					TbInvRtDDTO rtD = (TbInvRtDDTO) rtDList.get(iRtD);
					dataObj.put("txnType", "2");
					dataObj.put("optId", rtD.getOPT_ID());
					List<TbInvTxnDTO> list = inv015Dao
							.searchLessQty(dataObj);
					try {
						for (int iTxn = 0; iTxn < list.size(); iTxn++) {
							TbInvTxnDTO dto = list.get(iTxn);
							if (rtD.getOPT_ID().equals(dto.getRef_act_no())) {
								if (rtD.getQTY() > dto.getQty()) {
									recordMring.setOPT_ID(rtD.getOPT_ID());
									recordMring.setOPT_TYPE("RTN");
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
				throw new NomsException("更新TB_INV_MATERIAL_OPT："
						+ e.getLocalizedMessage());
			}
		} catch (Exception e) {
			throw new NomsException(e.getLocalizedMessage());
		}
	}
}
