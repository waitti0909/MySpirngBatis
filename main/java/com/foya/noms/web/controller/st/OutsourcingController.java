package com.foya.noms.web.controller.st;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample.Criteria;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.enums.WorkType;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.basic.CM001Service;
import com.foya.noms.service.st.OutsourcingService;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.st.ST003Service;
import com.foya.noms.service.st.SiteEmailService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.MailResponse;
import com.foya.noms.web.controller.BaseController;
import com.foya.workflow.exception.WorkflowException;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2015/3/10</td>
 * <td>改KC的改到半死</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Controller
public class OutsourcingController extends BaseController {

	@Autowired
	private ST003Service sT003Service;
	@Autowired
	private OutsourcingService outsourcingService;
	@Autowired
	private CM001Service cM001Service;
	@Autowired
	private ST002Service sT002Service;
	@Autowired
	private SiteEmailService siteEmailService;

	/**
	 * 委外申請
	 */
	@RequestMapping(value = "/outSourcing/out/init")
	public String processPage(Map<String, Object> model, @RequestParam("workId") String workId) {
		return "/ajaxPage/st/ST003M2";
	}
	
	/**
	 * 委外申請(新增)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/add")
	@ResponseBody
	public boolean getAdd(@RequestParam("poId") String poId, Map<String, Object> model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("poId", poId);
		return true;
	}
	
	/**
	 * 委外申請-儲存
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/save")
	@ResponseBody
	public BaseJasonObject<String> outSourceSave(@RequestParam("orderId") String orderId, @RequestParam("coVatNo") String coVatNo, @RequestParam("poId") String poId,
			@RequestParam("eqpType") String eqpType, @RequestParam("eqpModel") String eqpModel, @RequestParam("osDesc") String osDesc,
			@RequestParam("amount") String amount, @RequestParam("osId") String osId, @RequestParam("itemId") String itemId,
			@RequestParam("poNo") String poNo, @RequestParam("osNumber") BigDecimal[] osNumber,
			// @RequestParam("checkBoxItemId") String checkBoxItemId,
			Map<String, Object> model) {

		TbSiteOutsourcing outsource = StringUtils.isBlank(osId) ? new TbSiteOutsourcing() : outsourcingService.getByPrimaryKey(osId);

		// 派工單號 為空 跑新增 不為空 就UPDATE 原資料
		outsource.setORDER_ID(orderId);
		outsource.setCO_VAT_NO(coVatNo);
		outsource.setPO_ID(Long.valueOf(poId));
		outsource.setEQP_TYPE(eqpType);
		outsource.setEQP_MODEL(eqpModel);
		outsource.setOS_DESC(osDesc);
		outsource.setAMOUNT(new BigDecimal(amount));

		osId = outsourcingService.save(outsource, itemId, osNumber);

		return new BaseJasonObject<>(true, osId);
	}

	/**
	 * 委外申請-申請
	 * @return
	 */
	@RequestMapping("/st/outSourcing/out/applicationCheck")
	@ResponseBody
	public BaseJasonObject<Object> outsourceApply(@RequestParam("osId") String osId, @RequestParam("aplDept") String aplDept, @RequestParam("processType") String processType) {
		try {
			String errMsg = outsourcingService.outsourceApply(osId, aplDept, processType);
			return new BaseJasonObject<Object>(StringUtils.isEmpty(errMsg), StringUtils.isEmpty(errMsg) ? "申請成功！" : errMsg);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<Object>(false, "申請失敗：" + e.getMessage());
		}
	}

	/**
	 * 委外申請-取消
	 * @return
	 */
	@RequestMapping("/st/outSourcing/out/applicationCancel")
	@ResponseBody
	public BaseJasonObject<Object> outsourceCancel(@RequestParam("osId") String osId) {

		try {
			outsourcingService.outsourceCancel(osId);
			return new BaseJasonObject<Object>(true, "取消成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<Object>(false, "取消失敗");
		}
	}
	
	/**
	 * 委外派工驗收送審
	 * @param osId
	 * @param poId
	 * @param apNumber
	 * @param itemId
	 * @param apAmount
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping("/st/outSourcing/out/checkOsOrder")
	@ResponseBody
	public BaseJasonObject<Object> checkOsOrder(String osId, Long poId, @RequestParam("apNumber") BigDecimal[] apNumber,
			@RequestParam("itemId") String[] itemId, @RequestParam("apAmount") BigDecimal[] apAmount, @RequestParam("processType") String processType) {
		try {
			TbSiteOutsourcing outSourcing = outsourcingService.getByPrimaryKey(osId);
			// 檢查目前委外單狀態為有效
			String osStatus = outSourcing.getSTATUS();
			if (!OutSourceStatus.OS05.name().equals(osStatus)) {
				return new BaseJasonObject<>(false, "無效的委外單狀態，請刷新至最新資料");
			} else {
				TbComPoMain poMain = outsourcingService.selectByPrimaryKey(outSourcing.getPO_ID());
				if (StringUtils.equals(poMain.getIS_TEMP(), "Y") 
						&& StringUtils.equals(poMain.getIS_MERGE(), "N")) {	// 臨時PO單未合併前不可進行驗收
					return new BaseJasonObject<>(false, "臨時PO單未合併尚無法進行驗收！請確認");
				}
			}
			
			outsourcingService.checkOsOrder(osId, poId, getLoginUser().getUsername(), apNumber, apAmount, itemId, processType);
			return new BaseJasonObject<Object>(true, "派工單號：" + osId + " 驗收送審成功！");
		} catch (WorkflowException e) {
			log.error("派工單號：" + osId + " 驗收送審失敗！");
			log.error(e.getErrMsg(), e);
			return new BaseJasonObject<Object>(false, "派工單號：" + osId + " 驗收送審失敗！" + e.getMessage());
		} catch (Exception e) {
			log.error("派工單號：" + osId + " 驗收送審失敗！");
			log.error(e.getMessage(), e);
			return new BaseJasonObject<Object>(false, "派工單號：" + osId + " 驗收送審失敗！" + e.getMessage());
		}
	}

	/**
	 * 委外申請/委外驗收(簽核頁Detail用)
	 * 
	 * @param osId
	 * @param model
	 * @return
	 */
	@RequestMapping("/st/outSourcing/out/outSourceing")
	public String outSourceing(String osId, Map<String, Object> model) {
		model.put("tbSiteOutsourcing", "");

		Map<String, String> map = new HashMap<String, String>();
		map.put("osId", osId);
		List<TbSiteOutsourcingDTO> siteWorkIdOrderSearchList = outsourcingService.getSiteWorkOrderSearchByExample(map);

		model.put("tbSiteOutsourcing", siteWorkIdOrderSearchList.get(0));

		TbSiteOutsourcing tbSiteOutsourcing = outsourcingService.getByPrimaryKey(osId);
		TbComPoMain tbComPoMain = outsourcingService.selectByPrimaryKey(tbSiteOutsourcing.getPO_ID());
		model.put("tbComPoMain", tbComPoMain);

		TbComCompany tbComCompany = cM001Service.getCompanyDetailByID(tbComPoMain.getCO_VAT_NO());
		model.put("tbComCompany", tbComCompany);

		TbSiteWorkOrder tbSiteWorkOrder = outsourcingService.selectByPrimaryKey(tbSiteOutsourcing.getORDER_ID());
		SiteWorkDTO siteWork = sT002Service.getSiteWorkByWorkId(tbSiteWorkOrder.getWORK_ID());
		model.put("siteWork", siteWork);

		model.put("disabled", true);
		model.put("workType", WorkType.detectLabel(siteWork.getWORK_TYPE()));

		if ("NSR".equalsIgnoreCase(siteWork.getWORK_TYPE())) {
			model.put("processType", "SearchSiteApplyNSR");
		} else {
			model.put("processType", "SearchSiteApplySH");
		}
		
		if (StringUtils.equals(siteWork.getWORK_TYPE(), WorkType.NSR.name()) || StringUtils.equals(siteWork.getWORK_TYPE(), WorkType.SH.name())) {
			model.put("os_process_apply", "SearchSiteOutsourcingApply");
			model.put("os_process_accept", "SearchSiteOutsourcingAccept");
		} else {
			model.put("os_process_apply", "SiteBuildOutsourcingApply");
			model.put("os_process_accept", "SiteBuildOutsourcingAccept");
		}

		return "ajaxPage/st/OutSource";
	}
	
	/**
	 * 查詢tb_com_item_main
	 * 
	 * @param orderId
	 * @param model
	 * @return *123*
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemMainTable")
	@ResponseBody
	public BaseJasonObject<ItemMainDTO> getItemMainTable(@RequestParam("poId") String poId, @RequestParam("mainItem") String mainItem,
			@RequestParam("subItem") String subItem, Map<String, Object> model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("poId", poId);
		map.put("mainItem", mainItem);
		map.put("subItem", subItem);
		List<ItemMainDTO> mainItemList = outsourcingService.selectItemMainItem(map);
		return new BaseJasonObject<>(mainItemList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢tb_com_item_main
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemMainToChk")
	@ResponseBody
	public BaseJasonObject<ItemMainDTO> getitemMainToChk(@RequestParam("itemId") String itemId, @RequestParam("poId") String poId,
			Map<String, Object> model) {
		String itemIdStr[] = null;
		List<ItemMainDTO> mainItemList = new ArrayList<ItemMainDTO>();
		if (itemId != null) {
			if (itemId.length() > 0) {
				itemId = itemId.substring(0, itemId.length() - 1);
				itemIdStr = itemId.split(",");
				for (int i = 0; i < itemIdStr.length; i++) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("itemId", itemIdStr[i]);
					map.put("poId", poId);
					ItemMainDTO mainItem = outsourcingService.selectMainItem(map);

					mainItemList.add(mainItem);
				}
			}
		}

		return new BaseJasonObject<>(mainItemList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 工項設定(主)
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemMainSearchTable")
	public String processPage(@RequestParam("poId") Long poId, Map<String, Object> model) {
		TbComPoMain poMain = outsourcingService.getTbComPoMain(poId);
		TbComCompany company = cM001Service.getCompanyDetailByID(poMain.getCO_VAT_NO());
		model.put("poMain", poMain);
		model.put("company", company);
		return "/ajaxPage/st/POItem";

	}

	/**
	 * 查詢OutSourceTableSearchTable
	 * 
	 * @param srId
	 * @param model
	 * @return *123*
	 */
	@RequestMapping(value = "/st/outSourcing/out/outSourceSearchTable")
	@ResponseBody
	public BaseJasonObject<TbSiteOutsourcingDTO> getOutSourceTableSearchTable(@RequestParam("osId") String osId, Map<String, Object> model) {
		log.debug("getOutSourceTableSearchTable osId = " + osId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("osId", osId);

		List<TbSiteOutsourcingDTO> siteWorkIdOrderSearchList = outsourcingService.getSiteWorkOrderSearchByExample(map);
		log.debug("siteWorkIdOrderSearchList = " + siteWorkIdOrderSearchList.size());
		return new BaseJasonObject<>(siteWorkIdOrderSearchList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢 廠商下拉選單
	 * @param workId
	 * @param model
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/st/outSourcing/out/osVenSearchTable")
	@ResponseBody
	public BaseJasonObject<CompanyDTO> getOsVenSearchTable(@RequestParam("workId") String workId, Map<String, Object> model) {

		log.debug("workId : = " + workId);
		TbSiteWork work = outsourcingService.selectTbSiteWorkByOrderId(workId);
		List<CompanyDTO> osVenSearchList = new ArrayList<CompanyDTO>();

		if (PurchaseOrderType.T.name().equals(work.getOS_TYPE())) {
			osVenSearchList = outsourcingService.getCompanyForTypeT();
		} else if (PurchaseOrderType.P.name().equals(work.getOS_TYPE())) {
			osVenSearchList = outsourcingService.getCompanyForTypeP();
		}
		return new BaseJasonObject<>(osVenSearchList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢 PO單
	 * @param osType
	 * @param coVatNo
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/st/outSourcing/out/poNoSearchTable")
	@ResponseBody
	public BaseJasonObject<PoMainDTO> getPoNoSearchTable(@RequestParam("osType") String osType, 
			@RequestParam("coVatNo") String coVatNo, @RequestParam("workId") String workId) {
		log.debug("osType = " + osType + ", coVatNo = " + coVatNo + ", workId = " + workId);
		
		String poDomain = outsourcingService.getPoDomainByWork(workId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("coVatNo", coVatNo);
		map.put("osType", osType);
		map.put("poDomain", poDomain);
		map.put("todateTime", DateUtils.formatDate(AppConstants.DATE_PATTERN));
		List<PoMainDTO> poNoSearchList = outsourcingService.getPoMainByCompany(map);
		return new BaseJasonObject<>(poNoSearchList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 取得公司基本資料
	 * @param osId
	 * @return
	 * @author Miles Chang 2014.12.18
	 */
	@RequestMapping("/st/outSourcing/out/getTbComCompany")
	@ResponseBody
	public BaseJasonObject<TbComCompany> getTbComCompany(@RequestParam("osId") String osId) {
		TbComCompany tbComCompany = null;
		if (StringUtils.isNotEmpty(osId)) {
			TbSiteOutsourcing tbSiteOutsourcing = outsourcingService.getByPrimaryKey(osId);
			if (tbSiteOutsourcing != null) {
				String coVatNo = StringUtils.trimToEmpty(tbSiteOutsourcing.getCO_VAT_NO());
				log.info("派工單號：" + osId + ", 廠商統編：" + coVatNo);
				tbComCompany = cM001Service.getCompanyDetailByID(coVatNo);
			}
		}
		return new BaseJasonObject<TbComCompany>(tbComCompany, AJAX_SUCCESS);
	}

	/**
	 * 委外派工發送Mail
	 * @param mail
	 * @param ccmail
	 * @return
	 * @author Miles Chang 2014.12.19
	 * @return
	 */
	@RequestMapping("/st/outSourcing/out/sendMail")
	@ResponseBody
	public BaseJasonObject<Object> sendMail(@RequestParam("osId") String osId) {
		Date currentDate = new Date();
		String successWording = "";

		TbSiteOutsourcing tbSiteOutsourcing = outsourcingService.getByPrimaryKey(osId);
		if (!StringUtils.equals(OutSourceStatus.OS05.name(), tbSiteOutsourcing.getSTATUS())) {	// 未派工過		
			tbSiteOutsourcing.setSTATUS(OutSourceStatus.OS05.name());// 變更狀態為已派工
			tbSiteOutsourcing.setOS_TIME(currentDate);
			tbSiteOutsourcing.setMD_TIME(currentDate);
			tbSiteOutsourcing.setMD_USER(getLoginUser().getUsername());
			outsourcingService.updateByPrimaryKeySelective(tbSiteOutsourcing);
			successWording = "委外派工完成！";
		} else {
			successWording = "委外通知完成！";
		}
		
		TbComCompany tbComCompany = null;
		if (StringUtils.isNotEmpty(osId)) {
			if (tbSiteOutsourcing != null) {
				String coVatNo = StringUtils.trimToEmpty(tbSiteOutsourcing.getCO_VAT_NO());
				log.info("派工單號：" + osId + ", 廠商統編：" + coVatNo);
				tbComCompany = cM001Service.getCompanyDetailByID(coVatNo);
			}
		}
		
		if (tbComCompany != null && StringUtils.isNotEmpty(tbComCompany.getCON_EMAIL())) {
			MailResponse resp = siteEmailService.sendAssignOsMail(osId);
			if (resp.isSuccess()) {
				return new BaseJasonObject<>(true, successWording);
			} else {
				return new BaseJasonObject<>(false, resp.getErrorMessage());
			}
		} else {
			log.info("無收件人 mail ....仍要派工！");
		}
		return new BaseJasonObject<>(true, successWording);
	}

	/**
	 * 委外申請-工項設定-大項
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemMainSearch")
	@ResponseBody
	public BaseJasonObject<TbComItemCat> getWorkOrder(Map<String, Object> model) {
		TbComItemCatExample itemCat = new TbComItemCatExample();
		itemCat.createCriteria().andCAT_TYPEEqualTo("0");
		List<TbComItemCat> comItemCat = outsourcingService.selectComItemCatByExample(itemCat);
		return new BaseJasonObject<>(comItemCat, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 委外申請-工項設定-中項
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemSubSearch")
	@ResponseBody
	public BaseJasonObject<TbComItemCat> itemMainSelect(@RequestParam("mainItem") long mainItem, Map<String, Object> model) {

		TbComItemCatExample itemCat = new TbComItemCatExample();
		itemCat.createCriteria().andPARENT_CATEqualTo(mainItem);
		List<TbComItemCat> comItemCat = outsourcingService.selectComItemCatByExample(itemCat);
		return new BaseJasonObject<>(comItemCat, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 查詢tb_com_item_main
	 * @param orderId
	 * @param model
	 * @return *123*
	 */
	@RequestMapping(value = "/st/outSourcing/out/itemMainQuery")
	@ResponseBody
	public BaseJasonObject<PoItemDTO> getItemMainQuery(@RequestParam("osId") String osId, Map<String, Object> model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("osId", osId);
		List<PoItemDTO> list = outsourcingService.selectItemIdByOsId(map);
		return new BaseJasonObject<>(list, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢outscourcingTable
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/st/outSourcing/out/outSourceTable")
	@ResponseBody
	public BaseJasonObject<TbSiteOutsourcing> getOutSourceTable(@RequestParam("orderId") String orderId, Map<String, Object> model) {
		log.debug("getOutSourceTable orderId = " + orderId);
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andORDER_IDEqualTo(orderId);
		if (getLoginUser().isVendor()) {
			List<String> vendorReadStatus = new ArrayList<>();
			vendorReadStatus.add(OutSourceStatus.OS05.name());
			vendorReadStatus.add(OutSourceStatus.OS06.name());
			vendorReadStatus.add(OutSourceStatus.OS07.name());
			criteria.andCO_VAT_NOEqualTo(getLoginUser().getCoVatNo()).andSTATUSIn(vendorReadStatus);
		}

		List<TbSiteOutsourcing> outSourcingList = outsourcingService.getSiteOutSoureByExample(example);
		if (!outSourcingList.isEmpty()) {
			for (TbSiteOutsourcing outsourcing : outSourcingList) {
				TbComCompany comPanyList = outsourcingService.getTbComCompany(outsourcing.getCO_VAT_NO());
				if (comPanyList != null) outsourcing.setCO_VAT_NO(comPanyList.getCO_NAME());
				
				TbComPoMain poMain = outsourcingService.getTbComPoMain(outsourcing.getPO_ID());
				if (poMain != null) outsourcing.setPO_DOMAIN(poMain.getPO_NO());
			}
		}

		return new BaseJasonObject<>(outSourcingList, AJAX_SUCCESS, AJAX_EMPTY);
	}
}
