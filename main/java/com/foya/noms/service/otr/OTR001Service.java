package com.foya.noms.service.otr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvRtD;
import com.foya.dao.mybatis.model.TbInvRtDExample;
import com.foya.dao.mybatis.model.TbInvTroD;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.exception.NomsException;
import com.foya.noms.dao.otr.OTR001Dao;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.enums.MaterialOptType;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.inv.INV012Service;
import com.foya.noms.service.inv.INV015Service;
import com.foya.noms.service.st.MeterialOptService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class OTR001Service extends BaseService {

	@Autowired
	private OTR001Dao otr001Dao;
	
	@Autowired
	private MeterialOptDao materialOptDao;
	
	@Autowired
	private UniqueSeqService uniqueSeqService;
	
	@Autowired
	private MeterialOptService materialOptService;
	
	@Autowired
	private INV015Service inv015Service;
	
	@Autowired
	private INV012Service inv012Service;

	/**
	 * 接工單位、負責單位
	 * @param deptList
	 * @return
	 */
	public List<SiteWorkOrderDTO> selectWorkOrderDeptInUserDept(List<String> deptList){
		return otr001Dao.selectWorkOrderDeptInUserDept(deptList);
	}
	
	public PageList<SiteWorkDTO> selectSiteWorkByConditions(Map<String,Object> map){
		return (PageList<SiteWorkDTO>)otr001Dao.selectSiteWorkByConditions(map);
	}
	
	public List<TbSiteOutsourcing> findOutSourcingByOrderIdAndStatus(String orderId, String osStatus){
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		example.createCriteria().andORDER_IDEqualTo(orderId).andSTATUSEqualTo(osStatus);
		return otr001Dao.selectByExample(example);
	}
	
	public List<TbInvWarehouse> findWareHourseByDomain(String domain){
		TbInvWarehouseExample example = new TbInvWarehouseExample();
		example.createCriteria().andDomainEqualTo(domain);
		return otr001Dao.selectByExample(example);
	}
	
	/**
	 * 查詢有序號之在途物料
	 * @param siteId
	 * @param ctrlType
	 * @return
	 */
	public List<MeterialRtnDTO> getTransOutDetail(String osId,String ctrlType){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("osId", osId);
		map.put("ctrlType", ctrlType);
		return otr001Dao.selectRemInsItemQuery(map);
	}
	
	
	/**
	 * 工單物料轉出
	 * @param orderIdIn
	 * @param siteIdIn
	 * @param osIdIn
	 * @param whIdIn
	 * @param workTypeIn
	 * @param jsonArrStrOut
	 * @param orderIdOut
	 * @param siteIdOut
	 * @param osIdOut
	 * @param whIdOut
	 * @param workTypeOut
	 * @param optDesc
	 * @param userName
	 * @param currentTime
	 * @throws JSONException
	 * @throws WorkflowException
	 */
	@Transactional
	public void materialTransfer(String orderIdIn, String siteIdIn,
			String osIdIn, String whIdIn, String workTypeIn,
			String jsonArrStrOut, String orderIdOut, String siteIdOut,
			String osIdOut, String whIdOut, String workTypeOut, String optDesc)
			throws NomsException, JSONException, Exception {
		   
		   Date currentTime = new Date();
		   String userName = getLoginUser().getUsername();
		    log.info("開始進行 物料轉出....！！");
		    
			//轉出
			String optId = uniqueSeqService.getNextMaterialOptId(orderIdOut);
			log.info("1.物料轉出 optId : " + optId + " ========>");
			TbInvMaterialOpt tbInvMaterialOpt = new TbInvMaterialOpt();
			tbInvMaterialOpt.setOPT_ID(optId);
			tbInvMaterialOpt.setOPT_TYPE(MaterialOptType.TRO.name());
			tbInvMaterialOpt.setSITE_ID(siteIdOut); //站台編號
			tbInvMaterialOpt.setORDER_ID(orderIdOut);//轉出工單
			tbInvMaterialOpt.setOS_ID(osIdOut);//轉出派工單
			tbInvMaterialOpt.setWORK_TYPE(workTypeOut); //工務類型
			tbInvMaterialOpt.setAPP_USER(userName);
			tbInvMaterialOpt.setAPP_TIME(currentTime);
			tbInvMaterialOpt.setOPT_DESC(optDesc);
			tbInvMaterialOpt.setWH_ID(whIdOut); //退料倉庫
			tbInvMaterialOpt.setORDER_ID_IN(orderIdIn); //轉出目的工單
			tbInvMaterialOpt.setOS_ID_IN(osIdIn);
			tbInvMaterialOpt.setCR_TIME(currentTime);
			tbInvMaterialOpt.setCR_USER(userName);
			tbInvMaterialOpt.setMD_TIME(currentTime);
			tbInvMaterialOpt.setMD_USER(userName);
			materialOptDao.insert(tbInvMaterialOpt);
			
			JSONArray jsonArray = new JSONArray(jsonArrStrOut);
			TbInvTroD tbInvTroD = null;
			JSONObject obj = null;
			StringBuffer jsonArrayTemp = new StringBuffer();
			for (int i = 0; i < jsonArray.length(); i++) {
				obj = jsonArray.getJSONObject(i);
				tbInvTroD = new TbInvTroD();
				tbInvTroD.setOPT_ID(optId);
				tbInvTroD.setSRL_NO(obj.optLong("srlNo")); //序號控管物料檔編號
				tbInvTroD.setMAT_NO(obj.optString("matNo")); //料號
				tbInvTroD.setQTY(obj.optInt("qty")); //轉出數量
				tbInvTroD.setCR_USER(userName);
				tbInvTroD.setCR_TIME(currentTime);
				tbInvTroD.setMD_TIME(currentTime);
				tbInvTroD.setMD_USER(userName);
				otr001Dao.insert(tbInvTroD);
			}
			
			log.info("END=====1.物料轉出 optId : " + optId + " ========>");
			//轉出 END
			
			//退料
			log.info("1.物料轉出 -> 2.進行退料  ========>");
            
			tbInvMaterialOpt = materialOptService.mtReturn(MaterialOptType.RTN.name(), orderIdOut, osIdOut, whIdOut, optDesc, jsonArrStrOut);
			optId = tbInvMaterialOpt.getOPT_ID();
			//退料 END
			log.info("END===1.物料轉出 -> 2.進行退料 optId : " + optId + " ========>");
			
			//收料
			log.info("===1.物料轉出 -> 2.進行退料 ->3.進行收料 optId : " + optId + " ========>");
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String toDaty = sdf.format(currentTime); 
			String txnNo = inv015Service.selectTxnNoToday(toDaty, userName);
			
			//取得退料明細
			TbInvRtDExample tbInvRtDExample = new TbInvRtDExample();
			tbInvRtDExample.createCriteria().andOPT_IDEqualTo(optId);
			ArrayList<TbInvRtD> tbInvRtDList = (ArrayList<TbInvRtD>) otr001Dao.selectTbinvRtDByCondistions(tbInvRtDExample);
			
			jsonArrayTemp.append("[");
			Long dtlSeq = 0L;
			for (int i = 0; i < jsonArray.length(); i++) {
				obj = jsonArray.getJSONObject(i);
				if(jsonArrayTemp.length() != 1){
					jsonArrayTemp.append(",");
				}
				
				for(TbInvRtD tbInvRtD :tbInvRtDList){
					if(StringUtils.equals(tbInvRtD.getMAT_NO(),obj.optString("matNo"))){
						dtlSeq = tbInvRtD.getDTL_SEQ();
						tbInvRtDList.remove(tbInvRtD);
						break;
					}
				}
				
				jsonArrayTemp.append("{");
				jsonArrayTemp.append("\"dtlSeq\":\""+dtlSeq+"\",");
				jsonArrayTemp.append("\"prId\":\""+optId+"\",");
				jsonArrayTemp.append("\"matNo\":\""+obj.optString("matNo")+"\",");
				jsonArrayTemp.append("\"whId\":\""+whIdIn+"\",");
				jsonArrayTemp.append("\"prCnt\":\""+obj.optInt("qty")+"\",");
				jsonArrayTemp.append("\"txnNo\":\""+txnNo+"\",");
				jsonArrayTemp.append("\"srlNo\":\""+obj.optLong("srlNo")+"\",");
				jsonArrayTemp.append("\"matStatus\":\""+obj.optString("matStatus")+"\",");
				jsonArrayTemp.append("\"rnResn\":\""+obj.optString("trnReason")+"\",");
				jsonArrayTemp.append("\"prUser\":\""+userName+"\",");
				jsonArrayTemp.append("\"msUser\":\""+userName+"\",");
				jsonArrayTemp.append("\"faNo\":\""+obj.optString("faNo")+"\"");
				jsonArrayTemp.append("}");
				//發料
			}
			jsonArrayTemp.append("]");
			
			inv015Service.msCallable(jsonArrayTemp.toString());
			log.info("END===1.物料轉出 -> 2.進行退料 ->3.進行收料 optId : " + optId + " ========>");
			//收料 END
			
			//領料
			log.info("===1.物料轉出 -> 2.進行退料 ->3.進行收料 ->4.進行領料 ========>");
			
			tbInvMaterialOpt = materialOptService.mtApply(null,MaterialOptType.MRQ.name(), orderIdIn, osIdIn, whIdIn,
			currentTime, optDesc,
			"MR02",  //已核可 
			jsonArrStrOut);
			optId = tbInvMaterialOpt.getOPT_ID();
			
			log.info("END===1.物料轉出 -> 2.進行退料 ->3.進行收料 ->4.進行領料 optId : " + optId + " ========>");
			//領料 END
			
			//取得領料明細
			TbInvMrDExample tbInvMrDExample = new TbInvMrDExample();
			tbInvMrDExample.createCriteria().andOPT_IDEqualTo(optId);
			ArrayList<TbInvMrD> tbInvMrDList = (ArrayList<TbInvMrD>) otr001Dao.selectTbInvMrDByCondistions(tbInvMrDExample);
			
			log.info("===1.物料轉出 -> 2.進行退料 ->3.進行收料 ->4.進行領料 ->5.進行發料 optId : " + optId + " ========>");
			txnNo =  inv012Service.selectTxnNoToday(toDaty, userName);
			jsonArrayTemp = new StringBuffer();
			jsonArrayTemp.append("[");
			for (int i = 0; i < jsonArray.length(); i++) {
				dtlSeq = 0L;
				
				obj = jsonArray.getJSONObject(i);
				if(jsonArrayTemp.length() != 1){
					jsonArrayTemp.append(",");
				}
				
				for(TbInvMrD tbInvMrD :tbInvMrDList){
					if(StringUtils.equals(tbInvMrD.getMAT_NO(),obj.optString("matNo"))){
						dtlSeq = tbInvMrD.getDTL_SEQ();
						tbInvMrDList.remove(tbInvMrD);
						break;
					}
				}
				
				jsonArrayTemp.append("{");
				jsonArrayTemp.append("\"dtlSeq\":\""+dtlSeq+"\",");
				jsonArrayTemp.append("\"prId\":\""+optId+"\",");
				jsonArrayTemp.append("\"srlNo\":\""+obj.optLong("srlNo")+"\",");
				jsonArrayTemp.append("\"txnMatNo\":\""+obj.optString("matNo")+"\",");//實際料號
				jsonArrayTemp.append("\"matNo\":\""+obj.optString("matNo")+"\",");
				jsonArrayTemp.append("\"whId\":\""+whIdIn+"\",");
				jsonArrayTemp.append("\"prCnt\":\""+obj.optInt("qty")+"\",");
				jsonArrayTemp.append("\"prUser\":\""+userName+"\",");
				jsonArrayTemp.append("\"msUser\":\""+userName+"\",");
				jsonArrayTemp.append("\"txnNo\":\""+txnNo+"\",");
				jsonArrayTemp.append("\"faNo\":\""+obj.optString("faNo")+"\"");
				jsonArrayTemp.append("}");
				//發料
			}
			jsonArrayTemp.append("]");
			inv012Service.msCallable(jsonArrayTemp.toString());
			log.info("END===1.物料轉出 -> 2.進行退料 ->3.進行收料 ->4.進行領料 ->5.進行發料 optId : " + optId + " ========>");
			//發料 END
			log.info("物料轉出完成！！");
			
	}
	
	/**
	 * 工單物料轉出查詢
	 * @param deptId
	 * @param orderId
	 * @param orderIdIn
	 * @param btsSiteIdOut
	 * @param btsSiteIn
	 * @param siteNameIn
	 * @param siteNameOut
	 * @param crTimeStart
	 * @param crTimeEnd
	 * @return
	 */
	public PageList<TbInvMaterialOptDTO> findMaterialTransferByConditions(String deptId, String orderId,
			String orderIdIn, String btsSiteIdOut, String btsSiteIdIn,
			String siteNameIn, String siteNameOut, String crTimeStart,
			String crTimeEnd) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("deptId", deptId);
    	map.put("orderId", orderId);
    	map.put("orderIdIn", orderIdIn);
    	map.put("btsSiteIdOut", btsSiteIdOut);
    	map.put("btsSiteIdIn", btsSiteIdIn);
    	map.put("siteNameIn", siteNameIn);
    	map.put("siteNameOut", siteNameOut);
    	map.put("crTimeStart", crTimeStart);
    	map.put("crTimeEnd", crTimeEnd);
    	return (PageList<TbInvMaterialOptDTO>)materialOptDao.selectMaterialTransferByConditions(map);
    }
	
	public TbInvMaterialOpt findMaterialOptByPk(String optId){
		TbInvMaterialOpt tbInvMaterialOpt =   materialOptDao.findByPk(optId);
		if(tbInvMaterialOpt == null){
			return new TbInvMaterialOpt();
		}
		return tbInvMaterialOpt;
	}
	
	public String selectWhNameByWhId(String whId){
		TbInvWarehouse warehouse = materialOptDao.selectWarehouseByPrimaryKey(whId);
		if(warehouse == null){
			return "";
		}
		return warehouse.getWh_name();
	}
	
	public TbSiteWorkOrder selectWorkorder(String orderId){
		return materialOptDao.selectWorkorder(orderId);
		
	}
	
	public TbSiteWork selectSiteWorkByWorkId(String workId){
		return materialOptDao.selectSiteWork(workId);
	}
	
	/**
	 * 查詢物料轉出明細
	 * @param optId 操作單號
	 * @return
	 */
	public List<TbInvMaterialOptDTO> selectTroDetail(String optId){
		return materialOptDao.selectTroDetail(optId);
	}
	
	/**
	 * 查出轉出紀錄(join轉出單位名稱及轉出者)
	 * @param optId
	 * @return
	 */
	public TbInvMaterialOptDTO selectTroHistory(String optId){
		return materialOptDao.selectTroHistory(optId);
	}
	
}
