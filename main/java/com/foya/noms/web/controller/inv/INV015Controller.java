package com.foya.noms.web.controller.inv;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvRtDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.service.inv.INV008Service;
import com.foya.noms.service.inv.INV012Service;
import com.foya.noms.service.inv.INV015Service;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.IReportUtil;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/inv")
public class INV015Controller extends BaseController {

	@Inject
	private InvPublicUtilController invPublicUtilController;
	@Inject
	private INV015Service inv015Service;
	@Autowired
	private InvPublicUtilService invPublicUtilService;
	@Inject
	private INV012Service inv012Service;
	@Inject
	private PersonnelService personnelService;
	@Inject
	private INV008Service inv008Service;
	@Autowired
	private RoleMenuFunService roleMenuFunService;
	/**
	 * 收料單查詢Detail Map
	 */
	private HashMap<String, Object> callInDetailMap = new HashMap<String, Object>();
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv015/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		// 收料倉庫
		//model.put("callInDepot", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		if(!"HQ".equals(getLoginUser().getDomain().getID())){
			model.put("callInDepot", invPublicUtilService.getWareHouseLikeDomain(getLoginUser().getDomain().getID().substring(0, 1)));
		}else{
			model.put("callInDepot", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		}
		// 收料狀態
		model.put("callInStatus",
				invPublicUtilController.getLookupByType("INV_RT_STATUS"));

		// 申請單位
		//model.put("applyDept", invPublicUtilController.initDeptSelect(request));
		if (getLoginUser().getDomain().getID() != null && getLoginUser().getDomain().getID().equals("HQ")) {
			model.put("applyDept", invPublicUtilService.getAllFourthDept());
		} else {
			model.put("applyDept", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
		}
		return "ajaxPage/inv/INV015";
	}
	
	/**
	 * 搜尋倉管收料詳細資料
	 * 
	 * @return JqGridData
	 */
	@RequestMapping(value = "/inv015/searchCallInDetail")
	@ResponseBody
	public JqGridData<TbInvRtDDTO> searchCallInDetail(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="whId", required = false) String whId,
			@RequestParam(value="workType", required = false) String workType) {
		List<String> optIdList = new ArrayList<String>();
		if("S".equals(workType)){
			optIdList.add(optId);
			try{
				whId=inv015Service.selectInvOpt(optId).get(0).getWH_ID();
			}catch(Exception e){}
		}else{
			String[] optIdStr = optId.split("&");
			for (int g=0; g<optIdStr.length; g++) {
				String[] optStr = optIdStr[g].split("="); 
				optIdList.add(optStr[1]);
			}
		}
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("optId", optIdList);
		dataObj.put("whId", whId);
		List<TbInvRtDDTO> TbInvMrDDTOList = inv015Service.searchFs(dataObj);		
		PageList<TbInvRtDDTO> page = (PageList<TbInvRtDDTO>) TbInvMrDDTOList;
		return new JqGridData<TbInvRtDDTO>(page.getPaginator().getTotalCount(),TbInvMrDDTOList);
	}

	/***********************************************************************/

	

	/**
	 * 執行收料作業
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            Map<String, Object>
	 * @return URL
	 */
	@RequestMapping(value = "/inv015/callInView")
	public String callInView(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="whId", required = false) String whId,
			@RequestParam(value="whName", required = false) String whName,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="workType", required = false) String workType) {
		// 收料倉庫
		try {
			if (getLoginUser().getDomain().getID() != null && getLoginUser().getDomain().getID().equals("HQ")) {
				model.put("departmentAll", invPublicUtilService.getAllFourthDept());
			} else {
				model.put("departmentAll", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
			}
			model.put("department", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
			if("S".equals(workType)){
				try{
					model.put("whId" ,inv015Service.searchWhId(optId).getWH_ID());
					model.put("whName" ,invPublicUtilService.getWareHouseByPkey(inv015Service.searchWhId(optId).getWH_ID()).getWh_name());
				}catch(Exception e){}
			}else{
				model.put("whName" ,whName);
				model.put("whId" ,whId);
			}
			model.put("optId" ,optId);
			model.put("workType", workType);
			model.put("deptId", getLoginUser().getDeptId());
			model.put("loginUserId", getLoginUser().getUsername());
			model.put("loginUserName", getLoginUser().getChName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ajaxPage/inv/INV015M";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/inv015/search")
	@ResponseBody
	public JqGridData<TbInvMaterialOptDTO> search(
			@RequestParam("inWhId") String wareHouseSelect,
			@RequestParam("inStatus") String statusSelect,
			@RequestParam("optId") String optId,
			@RequestParam("siteId") String siteId,
			@RequestParam("applyDept") String deptApplySelect,
			@RequestParam("applyUser") String personnelSelect
			) throws Exception{		
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
				
		dataObj.put("wareHouse", wareHouseSelect);
		dataObj.put("status", statusSelect);
		dataObj.put("optId", optId);
//		dataObj.put("siteId", siteId);
		if(!"".equals(siteId)){
			try{
				dataObj.put("siteId", invPublicUtilService.getSiteId(siteId).getSITE_ID());
			}catch(Exception e){dataObj.put("siteId","");}	
		}
		dataObj.put("deptApply",deptApplySelect);
		dataObj.put("personnel", personnelSelect);
		
		List<TbInvMaterialOptDTO> TbInvMaterialOptDTOList = inv015Service.search(dataObj);
		PageList<TbInvMaterialOptDTO> page = (PageList<TbInvMaterialOptDTO>) TbInvMaterialOptDTOList;
		return new JqGridData<TbInvMaterialOptDTO>(page.getPaginator().getTotalCount(),TbInvMaterialOptDTOList);
	}
	
	/**
	 * 明細畫面 主檔資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv015/searchMasterDscr")
	public String searchMasterDscr(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="whIdDscr", required = false) String whIdDscr,
			@RequestParam(value="deptIdDscr", required = false) String deptIdDscr,
			@RequestParam(value="appUser", required = false) String appUser,
			@RequestParam(value="appDate", required = false) String appDate,
			@RequestParam(value="siteIdDscr", required = false) String siteIdDscr,
			@RequestParam(value="apwhIdpDate", required = false) String whId,
			@RequestParam(value="siteId", required = false) String siteId,
			@RequestParam(value="statusDscr", required = false) String statusDscr
			) throws Exception{
		HashMap<String,Object> dataObj=new HashMap<String,Object>();		
		dataObj.put("optId", optId);
		dataObj.put("whIdDscr", whIdDscr);
		dataObj.put("deptIdDscr", deptIdDscr);
		dataObj.put("appUser", appUser);
		dataObj.put("appDate", appDate);
		dataObj.put("siteIdDscr", siteIdDscr);
		dataObj.put("whId", whId);
		dataObj.put("siteId", siteId);
		dataObj.put("statusDscr", statusDscr);		
		model.put("masterDscr", dataObj);
		return "ajaxPage/inv/INV015D";
	}
	
	/**
	 * 收料單查詢
	 * 
	 * @param model
	 *            Map<String, Object>
	 * @return URL
	 */
	@RequestMapping(value = "/inv015/searchCallInInvoice")
	public String searchCallInInvoice(HttpServletRequest request,Map<String, Object> model) throws Exception {		
		//倉庫
		//model.put("callInDepot", invPublicUtilController.initWareHouseDeptSelect(null, getLoginUser().getUsername()));
		if(!"HQ".equals(getLoginUser().getDomain().getID())){
			model.put("callInDepot", invPublicUtilService.getWareHouseLikeDomain(getLoginUser().getDomain().getID().substring(0, 1)));
		}else{
			model.put("callInDepot", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		}
		//部門單位
		model.put("department", invPublicUtilController.initDeptSelect(null));		
		return "ajaxPage/inv/INV015L";
	}
	
	/**
	 * 搜尋退料清單
	 */
	@RequestMapping(value = "/inv015D/searchReturnDetail")
	@ResponseBody
	public JqGridData<TbInvRtDDTO> searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="whId", required = false) String whId
			) throws Exception{
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("optId", optId);
		dataObj.put("whId", whId);
		System.err.println(optId);
		System.err.println(whId);
		List<TbInvRtDDTO> TbInvMrDDTOList = inv015Service.searchUTbInvRtD(dataObj);		
		for(int i=0;i<TbInvMrDDTOList.size();i++){
			TbInvRtDDTO dto = (TbInvRtDDTO)TbInvMrDDTOList.get(i);
			dto.setOptId(optId);
			dto.setWhId(whId);
			TbInvMrDDTOList.set(i,dto);
		}			
		PageList<TbInvRtDDTO> page = (PageList<TbInvRtDDTO>) TbInvMrDDTOList;
		return new JqGridData<TbInvRtDDTO>(page.getPaginator().getTotalCount(),TbInvMrDDTOList);
	}
	
	/**
	 * 明細畫面 歷程資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv015/searchDtlHistory")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchDtlHistory(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="matNo", required = false) String matNo,
			@RequestParam(value="dtlSeq", required = false) String dtlSeq
			) throws Exception{
		List<TbInvTxnDTO> TbInvTxnDTOList = inv015Service.searchTbInvTxnExample(optId,matNo,dtlSeq);
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),TbInvTxnDTOList);
	}
	
	/**
	 * 收料簽收單
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv015/printSignPage")
	public String printSignPage(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="txnNo", required = true) String txnNo,
			@RequestParam(value="whName", required = false) String whName,
			@RequestParam(value="crUser", required = false) String crUser) throws Exception{
		HashMap<String,Object> dataObj = new HashMap<String,Object> ();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
		String printTime = formatter.format(curDate);
		
		dataObj.put("txnNo", txnNo);
		dataObj.put("whName", whName);
		dataObj.put("printUser", crUser);
		dataObj.put("printTime", printTime);
		model.put("masterDscr", dataObj);
		model.put("detail", inv015Service.searchInvTxnPrintPageDetail(txnNo));
		
		return "ajaxPage/inv/inv015SP1";
	}
	
	/**
	 * 產生發料單號
	 */
	@RequestMapping(value = "/inv015/genTxnNo")
	@ResponseBody
	public BaseJasonObject<Object> genTxnNo(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String initTxnNo=sdf.format(new Date()),txnNo="";
		try{
			txnNo=inv015Service.selectTxnNoToday(initTxnNo,getLoginUser().getUsername());
		}catch(NullPointerException e){
			txnNo="產生單號為空值";
		}
		return new BaseJasonObject<Object>(true,txnNo);
	}
	
	/**
	 * 更新OPT
	 */
	@RequestMapping(value = "/inv015/updateOpt")
	@ResponseBody
	public BaseJasonObject<Object> updateOpt(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId) throws Exception{
		String status="退料完成",statusForMrIng="退料中";
		TbInvMaterialOpt record = new TbInvMaterialOpt(); 
		TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
		try{
			List<TbSysLookup> list = invPublicUtilController.getLookupByTypeName("INV_RT_STATUS", status);
			TbSysLookup lookupData=(TbSysLookup)list.get(0);
			record.setSTATUS(lookupData.getCODE());
		}catch(IndexOutOfBoundsException e){}//避免領料數大於的情形
		catch(NullPointerException e){}
		try{			 
			List<TbSysLookup> list = invPublicUtilController.getLookupByTypeName("INV_RT_STATUS", statusForMrIng);
			TbSysLookup lookupData=(TbSysLookup)list.get(0);
			recordMring.setSTATUS(lookupData.getCODE());
		}catch(IndexOutOfBoundsException e){}//避免領料數大於的情形
		catch(NullPointerException e){}
		String[] optIdStr = optId.split("&");
		Set<String> optIdList = new HashSet<String>();
		for (int g=0; g<optIdStr.length; g++) {
			String[] optStr = optIdStr[g].split("="); 
			optIdList.add(optStr[1]);
			}
		Iterator<String> it = optIdList.iterator();        
        while(it.hasNext()){
			record.setOPT_ID(it.next());
			record.setOPT_TYPE("RTN");
			record.setMD_TIME(invPublicUtilService.getToday());
			record.setMD_USER(getLoginUser().getUsername());
			inv012Service.updateOpt(record);	//先更新所有的optId狀態成發料完成
		}
      //取得此optId清單所有的資料
      		List<TbInvRtDDTO> rtDList=inv015Service.searchRtDOptId(optIdList);
      		for(int i=0;i<rtDList.size();i++){			
      			HashMap<String,Object> dataObj=new HashMap<String,Object>();
      			TbInvRtDDTO rtD=(TbInvRtDDTO)rtDList.get(i);
      			dataObj.put("txnType", "2");
      			dataObj.put("optId", rtD.getOPT_ID());
//      			dataObj.put("matNo", rtD.getMAT_NO());
//      			dataObj.put("qty", rtD.getQTY());
      			List<TbInvTxnDTO> list = inv015Service.searchLessQty(dataObj);
      			try{
      				for(int iTxn=0;iTxn<list.size();iTxn++){
    					TbInvTxnDTO dto=list.get(iTxn);
    					if(rtD.getOPT_ID().equals(dto.getRef_act_no())){
    						if(rtD.getQTY()>dto.getQty()){
    							recordMring.setOPT_ID(rtD.getOPT_ID());
    							recordMring.setOPT_TYPE("RTN");
    							recordMring.setMD_TIME(invPublicUtilService.getToday());
    							recordMring.setMD_USER(getLoginUser().getUsername());
    							inv012Service.updateOpt(recordMring);
    						}
    					}
    				}	
      			}catch(IndexOutOfBoundsException e){}//如果無資料
      			catch(NullPointerException e){}//如果無資料
      		}
      		return new BaseJasonObject<Object>(true,"");
	}
	 
	
	/**
	 * 更新InvInv
	 */
	@RequestMapping(value = "/inv015/updateInvInvMinusQty")
	@ResponseBody
	public BaseJasonObject<Object> updateInvInvMinusQty(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="matNo", required = false) String matNo,
			@RequestParam(value="qty", required = false) String qty,
			@RequestParam(value="whId", required = false) String whId,
			@RequestParam(value="matStatus", required = false) String matStatus) throws Exception{
		HashMap<String,Object> dataObj=new HashMap<String,Object>(); 
		dataObj.put("matNo", matNo);
		dataObj.put("whId", whId);
		
		if("1".equals(matStatus))
			dataObj.put("stdQty", qty);
		else if("2".equals(matStatus))
			dataObj.put("wrdQty", qty);
		else if("3".equals(matStatus))
			dataObj.put("wspQty", qty);
		String.valueOf(inv015Service.updateInvInv(dataObj));
		return new BaseJasonObject<Object>(true,"");
	}
	/**
	 * 收料動作合併
	 */
	@RequestMapping(value = "/inv015/saveToTable")
	@ResponseBody
	public BaseJasonObject<Object> saveToTable(@RequestParam("msArray") String msArray) {
		Date today=invPublicUtilService.getToday();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String initTxnNo=sdf.format(new Date()),txnNo="";
		try{
			txnNo=inv015Service.selectTxnNoToday(initTxnNo,getLoginUser().getUsername());
		}catch(NullPointerException e){
			txnNo="產生單號為空值";
		}
			try{
				inv015Service.insertToTable(msArray, today, txnNo);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<Object>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<Object>(false,e.getMessage());
			}
			return new BaseJasonObject<Object>(true,txnNo);
	}
	/**
	 * 寫入InvTxn
	 */
	@RequestMapping(value = "/inv015/insertTxn")
	@ResponseBody
	public BaseJasonObject<Object> insertTxn(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="txnNo", required = false) String txnNo,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="matNo", required = false) String matNo,
			@RequestParam(value="matStatus", required = false) String matStatus,
			@RequestParam(value="qty", required = false) String qty,
			@RequestParam(value="whId", required = false) String whId,
			@RequestParam(value="tagNo", required = false) String tagNo,
			@RequestParam(value="faNo", required = false) String faNo,
			@RequestParam(value="srlNo", required = false) String srlNo,
			@RequestParam(value="rnResn", required = false) String rnResn,
			@RequestParam(value="dtlSeq", required = false) String dtlSeq,
			@RequestParam(value="siteId", required = false) String siteId,
			@RequestParam(value="orderNo", required = false) String orderNo,
			@RequestParam(value="crTime", required = false) Date crTime,
			@RequestParam(value="rtUser", required = false) String rtUser,
			@RequestParam(value="rcvUser", required = false) String rcvUser) throws Exception{
		try {
			if(!"".equals(srlNo)){
				inv012Service.updateSrl(Long.valueOf(srlNo));
			}
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
		TbInvTxn record = new TbInvTxn();
		record.setTxn_no(txnNo);
		record.setTxn_type(new Byte("2"));
		record.setRef_act_no(optId);
		record.setWh_id(whId);
		record.setMat_no(matNo);
		record.setMat_status(new Byte(matStatus));
		record.setRn_resn(rnResn);
		record.setMro_rt_user(rtUser);
		record.setSend_rcv_user(rcvUser);
		record.setDtl_seq(Long.valueOf(dtlSeq));
		try{
			record.setQty(Math.abs(Integer.valueOf(qty)));
		}catch(NumberFormatException e){
			record.setQty(0);
		}catch(NullPointerException e){
			record.setQty(0);
		}
		try{
			record.setFa_no(faNo);
		}catch(NullPointerException e){}
		try{
			record.setSrl_no(Long.valueOf(srlNo));
		}catch(NullPointerException e){}
		record.setSite_id(siteId);
		record.setOrder_no(orderNo);
		record.setCr_user(rtUser);//由於要靈活一點 所以寫入頁面上的收料人員 原始為getLoginUser().getUsername()
		record.setCr_time(crTime);
		String.valueOf(inv015Service.insertInvTxn(record));
		return new BaseJasonObject<Object>(true,"");
	}
	
	/**
	 * 寫入InvOnhand
	 */
	@RequestMapping(value = "/inv015/updateOnHand")
	@ResponseBody
	public BaseJasonObject<Object> updateOnHand(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="optId", required = false) String optId,
			@RequestParam(value="matNo", required = false) String matNo,
			@RequestParam(value="qty", required = false) int qty,
			@RequestParam(value="orderNo", required = false) String orderNo,
			@RequestParam(value="crTime", required = false) Date crTime) throws Exception{
		//String user=getLoginUser().getUsername();
		//String.valueOf(inv015Service.updateOnhandRTNQty(optId, matNo, qty, orderNo, user, crTime));
		return new BaseJasonObject<Object>(true,"");
	}
	/**
	 * 取貼標號碼
	 */
	@RequestMapping(value = "/inv015/lookupByType")
	@ResponseBody
	public BaseJasonObject<TbSysLookup> lookupByType(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value="status", required = false) String status) throws Exception{
		return new BaseJasonObject<TbSysLookup>(invPublicUtilService.getLookupByType(status), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 收料單查詢
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inv015L/searchCallInData")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchCallInData(HttpServletRequest request) {
		String strDate="",endDate="",whId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		List<TbInvTxnDTO> tbInvTxnList = null;
		String txnNo = request.getParameter("txnNo").trim();
		if("".equals(txnNo)){
			strDate = request.getParameter("strDate").trim() + " 00:00:00";
			endDate = request.getParameter("endDate").trim() + " 23:59:59";
			whId = request.getParameter("whId").trim();
			try {
				tbInvTxnList = inv015Service.getGroupInvTxnDataByGrid(txnNo, whId,
						"", sdf.parse(strDate), sdf.parse(endDate));
				mountInvTxnDtoMap(tbInvTxnList);
			} catch (ParseException e) {
				log.info("[INV015][/inv/inv015L/searchCallInData] ERROR :"
						+ e.getMessage());
				e.printStackTrace();
			} catch (Throwable throwable) {
				log.info("[INV015][/inv/inv015L/searchCallInData] ERROR :"
						+ throwable.getMessage());
				throwable.printStackTrace();
				}
		}else{
		//String whId = request.getParameter("whId").trim();
		//String createUser = request.getParameter("createUser").trim();
		try {
			tbInvTxnList = inv015Service.getGroupInvTxnDataByGrid(txnNo, whId,
					"", null, null);
			mountInvTxnDtoMap(tbInvTxnList);
		} catch (Throwable throwable) {
			log.info("[INV015][/inv/inv015L/searchCallInData] ERROR :"
					+ throwable.getMessage());
			throwable.printStackTrace();
			}
		}
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) tbInvTxnList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				tbInvTxnList);
	}
	
	/**
	 * 收料單歷史查詢
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inv015L/searchCallInHistoryDetail")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchCallInHistoryDetail(
			HttpServletRequest request) {

		String txnNo = request.getParameter("txnNo").trim();
		List<TbInvTxnDTO> invTxnList = null;
		//TbInvTxnDTO invTxnDto = (TbInvTxnDTO) callInDetailMap.get(txnNo);
		try{
			//invTxnList = invTxnDto.getInvTxnDtoList();
			invTxnList=inv015Service.getInvTxnData(txnNo, null, null, null, null);
		}catch(NullPointerException e){
			return new JqGridData<TbInvTxnDTO>(0, invTxnList);
		};
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) invTxnList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				invTxnList);
	}

	/**
	 * 列印收料單
	 * 
	 * @param model
	 *            Map<String, Object>
	 * @return URL
	 */
	@RequestMapping(value = "/inv015L/printReceipt")
	public void printReceipt(HttpServletResponse response,
			HttpServletRequest request) throws Exception {

		String txnNo = request.getParameter("txnNo").trim();

		TbInvTxnDTO invTxnDto = (TbInvTxnDTO) callInDetailMap.get(txnNo);

		List<TbInvTxnDTO> invTxnDtoList = invTxnDto == null ? inv015Service.getInvTxnDataByGrid(txnNo, null, null, null, null) : invTxnDto
				.getInvTxnDtoList();

		// 個人習慣使用 listMap 來對應 JRxml Bean Name
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < invTxnDtoList.size(); i++) {
			if(i==0 ){
				invTxnDto = invTxnDtoList.get(i);
			}
			TbInvTxnDTO dto = invTxnDtoList.get(i);

			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("index", (i + 1));
			map.put("matNo", dto.getMat_no());
			map.put("returnNumber", dto.getRef_act_no());
//			map.put("applyDept", dto.getDept_name());
//			map.put("applicant", dto.getAppUserName());
			map.put("platformNo", dto.getBtsSiteId());
			map.put("receiptNumber", dto.getOrder_no());
			map.put("matName", dto.getMatName());

			if (dto.getInvSrl() != null && dto.getInvSrl().getTag_no() != null) {
				map.put("companyNumber", dto.getInvSrl().getVen_sn());
				map.put("labelNumber", dto.getInvSrl().getTag_no());
			}

			map.put("status", dto.getCallInMatStatus());
			map.put("callInNumber", dto.getQty());

			listMap.add(map);
		}

		// 新增 Parameters
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("callInNumberP", invTxnDto.getTxn_no());
		parametersMap.put("callInDepot", invTxnDto.getWh_name());
		parametersMap.put("printUser", getLoginUser().getChName());
		parametersMap.put("printTime",
				new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		parametersMap.put("applyDept", invTxnDto.getDept_name());
		parametersMap.put("applicant", invTxnDto.getAppUserName());
		/*********************************************************************/
		String fileName = "CallInReceipt_"
				+ new SimpleDateFormat("HH:mm:ss").format(new Date())+".pdf";

		IReportUtil reportUtil = new IReportUtil("INV015SP1",request);

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				reportUtil.getJasperReport(), parametersMap,

				reportUtil.getJRDataSource(listMap));

		response.setHeader(reportUtil.getHeaderName(),
				reportUtil.getHeaderValue(fileName));

		response.setContentType("application/pdf");

		response.setCharacterEncoding("UTF-8");

		reportUtil.mountJRPdfExporter(response, jasperPrint);

		response.getOutputStream().close();
	}

	
	/**
	 * 收料單查詢畫面 15L
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv015/txnPage")
	public String txnPage(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
		model.put("wareHouseSelect", invPublicUtilController
				.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		model.put("personnelSelect", invPublicUtilController
				.initPersonnelDeptSelectlike(request, "4"));
		model.put("deptSelect", invPublicUtilController.initDeptSelect(request));
		return "ajaxPage/inv/inv015L";
	}
	
	/**
	 * 
	 * @param invTrDto
	 */
	private void mountInvTxnDtoMap(List<TbInvTxnDTO> TbInvTxnList) {

		callInDetailMap.clear();

		for (TbInvTxnDTO dto : TbInvTxnList) {
			callInDetailMap.put(dto.getTxn_no(), dto);
		}

	}
	
	/**
	 * 供待辦事項呼叫
	 */
	@RequestMapping(value = "/inv/inv015/getTodoOrders")
	@ResponseBody
	public List<TodoOrdersDTO> getTodoOrders() {
		List<TodoOrdersDTO> todoDtoList = new ArrayList<TodoOrdersDTO>();
		
		List<TbAuthMenu> menuList = inv008Service.getMenuId("/inv/inv015/init");
		int menuId = 0;
		if (menuList.size() > 0) {
			menuId = menuList.get(0).getMENU_ID().intValue();
		}

		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), menuId);
		boolean butCompetence = false;

		for (RoleMenuFunDTO dto : list) {

			if (dto.getBtnName().equals("收料")) {
				butCompetence = true;
			}
		}
		Set<String> statusList = new HashSet<String>();
		statusList.add("RT01");
		
		if(butCompetence){
			List<TbInvMaterialOptDTO> optList = invPublicUtilService.searchTodo("RTN", statusList, getLoginUser().getUsername(),"INV_RT_STATUS");
			for (TbInvMaterialOptDTO dto : optList) {
				// 取的姓名
				List<TbAuthPersonnel> personnelList = personnelService
						.searchPsnByPsnNo(dto.getCR_USER());
				TodoOrdersDTO ordersDto = new TodoOrdersDTO();
				ordersDto.setOrderId(dto.getOPT_ID());
				ordersDto.setOrderTypeDesc("收料");
				ordersDto.setAppUser(dto.getCR_USER());
				ordersDto.setOrderInfo(dto.getBTS_SITE_ID());
				ordersDto.setAppUserName(personnelList.size() > 0 ? personnelList
						.get(0).getCHN_NM() : "");
				ordersDto.setAppTime(new Timestamp(dto.getCR_TIME().getTime()));
				ordersDto.setOrderStatus(String.valueOf(dto.getSTATUS()));
				ordersDto.setOrderStatusDesc(dto.getStatusDscr());
				ordersDto.setLinkURL("/inv/inv015/callInView?workType=S&optId="+dto.getOPT_ID());
				
				todoDtoList.add(ordersDto);
			}
		}
		return todoDtoList;
	}
	
}
