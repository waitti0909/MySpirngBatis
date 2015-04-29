package com.foya.noms.web.controller.pay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.UTbPayAcceptanceOrderExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceDtlExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dto.pay.TbPayAcceptanceOrderDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY006Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

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
 * <td>2014/10/31</td>
 * <td>新建檔案</td>
 * <td>Markee</td>
 * </tr>
 * </table>
 * 
 * @author Markee
 * 
 */
@Controller
@RequestMapping(value = "/pay/pay006")
public class PAY006Controller extends BaseController {

	@Autowired
	private PAY006Service pay006Service;

	@Autowired
	private DomainService domainService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model) {
		HttpSession session = request.getSession();

		session.setAttribute("pay006_orgDomainList", domainService.getStandardDomainList()); // 維運區
		session.setAttribute("pay006_companyList", pay006Service.selectComCompanyByExample(new TbComCompanyExample())); // 業者
		//session.setAttribute("pay006_authPersonnelList", pay006Service.selectAuthPersonnelByExample(new TbAuthPersonnelExample())); // 申請人
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("OSA_STATUS");
		session.setAttribute("pay006_statusList", pay006Service.selectPayLookupCodeByExample(example)); // 申請狀態

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		model.put("cr_time_s", cal.getTime()); // 申請起日
		model.put("cr_time_e", new Date()); // 申請迄日

		return "ajaxPage/pay/PAY006";
	}

	/**
	 * 查詢驗收清單
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param crTimeS 申請起日
	 * @param crTimeE 申請迄日
	 * @param status 申請狀態
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbPayOutsourceAcceptanceDTO> query(HttpServletRequest request,
			@RequestParam("domain") String domain,
			@RequestParam("co_vat_no") String coVatNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("cr_time_s") String crTimeS,
			@RequestParam("cr_time_e") String crTimeE,
			@RequestParam("status") String status) throws Exception {

		UTbPayOutsourceAcceptanceExample example = new UTbPayOutsourceAcceptanceExample();
		UTbPayOutsourceAcceptanceExample.Criteria cr = example.createCriteria();
		if (domain != null && domain != "") {
			cr.andDOMAINEqualTo(domain);
		}
		if (coVatNo != null && coVatNo != "") {
			cr.andCO_VAT_NOEqualTo(coVatNo);
		}
		if (poNo != null && poNo != "") {
			cr.andPO_NOLike("%" + poNo +"%");
		}
		if (crTimeS != null && !crTimeS.equals("") && crTimeE != null && !crTimeE.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			cr.andAPP_DATEBetween(sdf.parse(crTimeS + " 00:00:00.000"), sdf.parse(crTimeE + " 23:59:59.999"));
		}
		if (status != null && status != "") {
			String[] s = status.split(",");
			List<String> statusList = new ArrayList<String>();
			for (int i = 0; i < s.length; i++) {
				statusList.add(s[i]);
			}
			cr.andSTATUSIn(statusList);
		}
		
		List<TbPayOutsourceAcceptanceDTO> list = pay006Service.selectPayOutsourceAcceptanceByExamplePage(example);
		PageList<TbPayOutsourceAcceptanceDTO> page = (PageList<TbPayOutsourceAcceptanceDTO>) list;
		return new JqGridData<TbPayOutsourceAcceptanceDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 導頁至新增與修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("btn_type") String btnType,
			@RequestParam("ap_no") String apNo) {

		model.put("btn_type", btnType); // 新增/修改/顯示明細

		if (btnType.equals("add")) {
			// 新增
			TbSiteOutsourcingDTO tb = new TbSiteOutsourcingDTO();
			tb.setAPL_TIME(new Date());
			model.put("element", tb);
			return "ajaxPage/pay/PAY006A";
		} else {
			// 修改/顯示明細
			TbPayOutsourceAcceptanceDTO tb = pay006Service.selectPayOutsourceAcceptanceByPrimaryKey(apNo);
			model.put("element", tb);
			return "ajaxPage/pay/PAY006M";
		}
	}

	/**
	 * 取得工程驗收清單資料
	 * @param request
	 * @param apNo 驗收單號
	 * @param poNo
	 * @return
	 */
	@RequestMapping(value = "/queryAcceptanceOrder")
	@ResponseBody
	public JqGridData<TbPayAcceptanceOrderDTO> queryAcceptanceOrder(
			HttpServletRequest request, @RequestParam("ap_no") String apNo,
			@RequestParam("po_no") String poNo) {

		UTbPayAcceptanceOrderExample example = new UTbPayAcceptanceOrderExample();
		UTbPayAcceptanceOrderExample.Criteria cr = example.createCriteria();
		cr.andMST_AP_NOEqualTo(apNo);
		cr.andPO_NOEqualTo(poNo);

		List<TbPayAcceptanceOrderDTO> list = pay006Service.selectPayAcceptanceOrderByExamplePage(example);
		PageList<TbPayAcceptanceOrderDTO> page = (PageList<TbPayAcceptanceOrderDTO>) list;
		return new JqGridData<TbPayAcceptanceOrderDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 取得工程驗收工單資訊
	 * @param request
	 * @param apNo 驗收單號
	 * @param poNo
	 * @return
	 */
	@RequestMapping(value = "/queryOutsourceAcceptanceDtl")
	@ResponseBody
	public JqGridData<TbPayOutsourceAcceptanceDtlDTO> queryOutsourceAcceptanceDtl(
			HttpServletRequest request, @RequestParam("ap_no") String apNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("order_no") String orderNo,
			@RequestParam("os_id") String osId) {

		UTbPayOutsourceAcceptanceDtlExample example = new UTbPayOutsourceAcceptanceDtlExample();
		UTbPayOutsourceAcceptanceDtlExample.Criteria cr = example.createCriteria();
		cr.andMST_AP_NOEqualTo(apNo);
		cr.andPO_NOEqualTo(poNo);
		cr.andORDER_NOEqualTo(orderNo);
		cr.andOS_IDEqualTo(osId);

		List<TbPayOutsourceAcceptanceDtlDTO> list = pay006Service.selectPayOutsourceAcceptanceDtlByExamplePage(example);
		PageList<TbPayOutsourceAcceptanceDtlDTO> page = (PageList<TbPayOutsourceAcceptanceDtlDTO>) list;
		return new JqGridData<TbPayOutsourceAcceptanceDtlDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 刪除工程驗收資料
	 * @param request
	 * @param mstApNo 主檔驗收單號
	 * @param poNo 主檔PO
	 * @param orderNos 工單
	 * @return
	 */
	@RequestMapping(value = "/deleteAcceptanceOrder")
	@ResponseBody
	public BaseJasonObject<Object> deleteAcceptanceOrder(
			HttpServletRequest request,
			@RequestParam("mst_ap_no") String mstApNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("order_nos") String orderNos,
			@RequestParam("os_ids") String osIds) {

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mst_ap_no", mstApNo);
			map.put("po_no", poNo);
			map.put("order_nos", orderNos);
			map.put("os_ids", osIds);
			boolean noDataFlag = pay006Service.deleteAcceptanceOrder(map);
			
			BaseJasonObject<Object> bjo = new BaseJasonObject<Object>(true, getMessageDetail("msg.delete.success"));
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put("noDataFlag", noDataFlag);
			bjo.setMaps(messageMap);
			return bjo;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 修改工單實際驗收數量
	 * @param request
	 * @param mstApNo 主檔驗收單號
	 * @param poNo 主檔PO
	 * @param orderNo 工單單號
	 * @param orderItem 工單項目
	 * @param osId 派工單號
	 * @param unitPrice 單價
	 * @param realApQty 實際驗收數量
	 * @return
	 */
	@RequestMapping(value = "/updateRealApQty")
	@ResponseBody
	public BaseJasonObject<Object> updateRealApQty(HttpServletRequest request,
			@RequestParam("mst_AP_NO") String mstApNo,
			@RequestParam("po_NO") String poNo,
			@RequestParam("order_NO") String orderNo,
			@RequestParam("order_ITEM") String orderItem,
			@RequestParam("os_ID") String osId,
			@RequestParam("unit_PRICE") String unitPrice,
			@RequestParam("real_AP_QTY") String realApQty) {

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mst_ap_no", mstApNo);
			map.put("po_no", poNo);
			map.put("order_no", orderNo);
			map.put("order_item", orderItem);
			map.put("os_id", osId);
			map.put("unit_price", unitPrice);
			map.put("real_ap_qty", realApQty);
			pay006Service.updateRealApQty(map);
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 取得驗收主檔資料
	 * @param apNo
	 * @return
	 */
	@RequestMapping(value = "/search/payOutsourceAcceptance")
	@ResponseBody
	public TbPayOutsourceAcceptanceDTO getPayOutsourceAcceptance(@RequestParam("ap_no") String apNo) {
		return pay006Service.selectPayOutsourceAcceptanceByPrimaryKey(apNo);
	}

	/**
	 * 送請款
	 * @param request
	 * @param apNo 驗收單號
	 * @param poNo PO
	 * @return
	 */
	@RequestMapping(value = "/paymentRequest")
	@ResponseBody
	public BaseJasonObject<Object> paymentRequest(HttpServletRequest request,
			@RequestParam("ap_no") String apNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("co_vat_no") String coVatNo) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ap_no", apNo);
			map.put("po_no", poNo);
			map.put("co_vat_no", coVatNo);
			pay006Service.paymentRequest(map);
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 輸入憑證初始頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sp1/init")
	public String sp1Init(HttpServletRequest request,
			Map<String, Object> model, @RequestParam("ap_no") String apNo) {
		TbPayOutsourceAcceptanceDTO tb = pay006Service.selectPayOutsourceAcceptanceByPrimaryKey(apNo);
		model.put("element", tb);
		return "ajaxPage/pay/PAY006SP1";
	}

	/**
	 * 取得工程驗收清單資料
	 * @param request
	 * @param apNo 驗收單號
	 * @param poNo
	 * @return
	 */
	@RequestMapping(value = "/sp1/queryPaymentRequestVoucher")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestVoucherDTO> queryPaymentRequestVoucher(
			HttpServletRequest request, @RequestParam("ap_no") String apNo) {

		UTbPayPaymentRequestVoucherExample example = new UTbPayPaymentRequestVoucherExample();
		UTbPayPaymentRequestVoucherExample.Criteria cr = example.createCriteria();
		cr.andPROCESS_TYPEEqualTo("OSV");
		cr.andEqualTo("B.CASH_REQ_AP_NO", apNo);

		List<TbPayPaymentRequestVoucherDTO> list = pay006Service.selectPayPaymentRequestVoucherByExamplePage(example);
		PageList<TbPayPaymentRequestVoucherDTO> page = (PageList<TbPayPaymentRequestVoucherDTO>) list;
		return new JqGridData<TbPayPaymentRequestVoucherDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 取得憑證類別
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search/voucherType")
	@ResponseBody
	public Map<String, String> getVoucherType(HttpServletRequest request) {
		Map<String, String> map = (Map<String, String>) request.getSession().getAttribute("pay006_voucherTypeList");
		if (map == null) {
			TbPayLookupCodeExample example = new TbPayLookupCodeExample();
			example.createCriteria().andLOOKUP_TYPEEqualTo("VOUCHER_TYPE");
			List<TbPayLookupCode> list = pay006Service.selectPayLookupCodeByExample(example);

			map = new LinkedHashMap<String, String>();
			for (int i = 0; i < list.size(); i++) {
				TbPayLookupCode tb = list.get(i);
				map.put(tb.getLOOKUP_CODE(), tb.getLOOKUP_CODE_DESC());
			}
		}

		request.getSession().setAttribute("pay006_voucherTypeList", map);
		return map;
	}

	/**
	 * 儲存憑證資料
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sp1/creditSave")
	@ResponseBody
	public BaseJasonObject<Object> creditAddEdit(HttpServletRequest request) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mst_ap_no", request.getParameter("mst_ap_no")); // 驗收單號
			map.put("mst_po_no", request.getParameter("mst_po_no")); // 驗收PO
			map.put("id", request.getParameter("id")); // jgGrid資料ID
			map.put("voucher_type", request.getParameter("voucher_type")); // 憑證類別
			map.put("voucher_nbr", request.getParameter("voucher_nbr")); // 憑証號碼
			map.put("voucher_date", request.getParameter("voucher_date")); // 憑證日期
			map.put("vat_number", request.getParameter("vat_number")); // 統一編號
			map.put("tax_exclusive_amt", request.getParameter("tax_exclusive_amt")); // 未稅金額
			pay006Service.creditSave(map);
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.add.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 匯出Excel
	 * @param request
	 * @param response
	 * @param apNo 驗收單號
	 * @param poNo PO
	 * @param coVatNo 業者代碼
	 * @throws Exception
	 */
	@RequestMapping(value = "/genExcel")
	public void genExcel(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("ap_no") String apNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("co_vat_no") String coVatNo) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		// 取得合約名稱
		TbComPoMainExample comPoMainExample = new TbComPoMainExample();
		comPoMainExample.createCriteria().andPO_NOEqualTo(poNo);
		List<TbComPoMain> comPoMainList = pay006Service.selectComPoMainByExample(comPoMainExample);
		if (comPoMainList.size() > 0) {
			map.put("po_name", comPoMainList.get(0).getPO_NAME());
		}
		
		map.put("po_no", poNo); // 採購單號

		// 取得廠商名稱
		TbComCompanyExample comCompanyExample = new TbComCompanyExample();
		comCompanyExample.createCriteria().andCO_VAT_NOEqualTo(coVatNo);
		List<TbComCompany> comCompanylist = pay006Service.selectComCompanyByExample(comCompanyExample);
		if (comCompanylist.size() > 0) {
			map.put("co_name", comCompanylist.get(0).getCO_NAME());
		}

		// 取得已請款記錄
		UTbPayOutsourceAcceptanceDtlExample example = new UTbPayOutsourceAcceptanceDtlExample();
		UTbPayOutsourceAcceptanceDtlExample.Criteria cr = example.createCriteria();
		cr.andPO_NOEqualTo(poNo);
		cr.andEqualTo("B.STATUS", "P"); // 付款
		List<TbPayOutsourceAcceptanceDtlDTO> list1 = pay006Service.selectPayOutsourceAcceptanceDtlByExample(example);
		// 取得本次請款
		example = new UTbPayOutsourceAcceptanceDtlExample();
		cr = example.createCriteria();
		cr.andMST_AP_NOEqualTo(apNo);
		cr.andPO_NOEqualTo(poNo);
		List<TbPayOutsourceAcceptanceDtlDTO> list2 = pay006Service.selectPayOutsourceAcceptanceDtlByExample(example);

		// 設定檔案名稱
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "PAY006_" + sdf.format(new Date()) + ".xls";

		pay006Service.exportExcel(request.getSession(), response, fileName, map, list1, list2);
	}

	/**
	 * 查詢站點工程資料
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param aplUser 申請人
	 * @param aplTime 申請日期
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySiteOutsourcing")
	@ResponseBody
	public JqGridData<TbSiteOutsourcingDTO> querySiteOutsourcing(HttpServletRequest request,
			@RequestParam("domain") String domain,
			@RequestParam("co_vat_no") String coVatNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("apl_user") String aplUser,
			@RequestParam("apl_time") String aplTime) throws Exception {

		UTbSiteOutsourcingExample example = new UTbSiteOutsourcingExample();
		UTbSiteOutsourcingExample.Criteria cr = example.createCriteria();
		if (coVatNo != null && coVatNo != "") {
			cr.andCO_VAT_NOEqualTo(coVatNo);
		}
		if (aplUser != null && aplUser != "") {
			cr.andAPL_USEREqualTo(aplUser);
		}
		if (aplTime != null && aplTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			cr.andAPL_TIMEBetween(sdf.parse(aplTime + " 00:00:00.000"), sdf.parse(aplTime + " 23:59:59.999"));
		}
		if (poNo != null && poNo != "") {
			cr.andEqualTo("B.PO_NO", poNo);
		}
		if (domain != null && domain != "") {
			cr.andEqualTo("F.DOMAIN", domain);
		}
		cr.andSTATUSEqualTo("OS07"); // 已驗收
		cr.andPAY_FLGEqualTo("Y");

		List<TbSiteOutsourcingDTO> list = pay006Service.selectSiteOutsourcingByExamplePage(example);
		PageList<TbSiteOutsourcingDTO> page = (PageList<TbSiteOutsourcingDTO>) list;
		return new JqGridData<TbSiteOutsourcingDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢站點工程明細資料
	 * @param request
	 * @param osIds 站點工程代碼
	 * @param poIds PO
	 * @return
	 */
	@RequestMapping(value = "/querySiteOsItem")
	@ResponseBody
	public JqGridData<Object> querySiteOsItem(
			HttpServletRequest request, @RequestParam("os_ids") String osIds,
			@RequestParam("po_ids") String poIds,
			@RequestParam("order_ids") String orderIds) {

		String[] osId = osIds.split(",");
		String[] poId = poIds.split(",");
		String[] orderId = orderIds.split(",");

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < osId.length; i++) {
			UTbSiteOsItemExample example = new UTbSiteOsItemExample();
			UTbSiteOsItemExample.Criteria cr = example.createCriteria();
			cr.andOS_IDEqualTo(osId[i]);
			cr.andPO_IDEqualTo(Integer.valueOf(poId[i]).longValue());
			List<TbSiteOsItemDTO> tempList = pay006Service.selectSiteOsItemByExample(example);
			for (int j = 0; j < tempList.size(); j++) {
				tempList.get(j).setMST_ORDER_ID(orderId[i]);
			}
			list.addAll(tempList);
		}
		
		return this.getJqGridData(request.getParameter("page"), request.getParameter("size"), list);
	}

	/**
	 * 取得站點工程相關金額加總
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param aplUser 申請人
	 * @param aplTime 申請日期
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/search/siteOutsourcingSumAmount")
	@ResponseBody
	public TbSiteOutsourcingDTO getSiteOutsourcingSumAmount(
			HttpServletRequest request, @RequestParam("domain") String domain,
			@RequestParam("co_vat_no") String coVatNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("apl_user") String aplUser,
			@RequestParam("apl_time") String aplTime) throws Exception {

		UTbSiteOutsourcingExample example = new UTbSiteOutsourcingExample();
		UTbSiteOutsourcingExample.Criteria cr = example.createCriteria();
		if (coVatNo != null && coVatNo != "") {
			cr.andCO_VAT_NOEqualTo(coVatNo);
		}
		if (aplUser != null && aplUser != "") {
			cr.andAPL_USEREqualTo(aplUser);
		}
		if (aplTime != null && aplTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			cr.andAPL_TIMEBetween(sdf.parse(aplTime + " 00:00:00.000"), sdf.parse(aplTime + " 23:59:59.999"));
		}
		if (poNo != null && poNo != "") {
			cr.andEqualTo("B.PO_NO", poNo);
		}
		if (domain != null && domain != "") {
			cr.andEqualTo("F.DOMAIN", domain);
		}
		cr.andSTATUSEqualTo("OS07"); // 已驗收
		cr.andPAY_FLGEqualTo("Y");

		TbSiteOutsourcingDTO tb = pay006Service.selectSiteOutsourcingSumAmountByExample(example);
		if (tb == null) {
			tb = new TbSiteOutsourcingDTO();
			tb.setAP_AMT(BigDecimal.valueOf(0));
			tb.setTAX_EXCLUSIVE_AMT(BigDecimal.valueOf(0));
			tb.setBUSINESS_TAX(BigDecimal.valueOf(0));
		}
		return tb;
	}

	/**
	 * 新增站點工程驗收資料
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param aplUser 申請人
	 * @param aplTime 申請日期
	 * @param orderIds 工單代碼
	 * @param siteIds 站點代碼
	 * @param mstOrderIds Master的工單代碼
	 * @param itemIds 工項
	 * @param prices 單價
	 * @param numbers 申請數量
	 * @param apNumbers 驗收數量
	 * @param realApNumbers 實際驗收數量
	 * @return
	 */
	@RequestMapping(value = "/insertSiteOutsourcing")
	@ResponseBody
	public BaseJasonObject<Object> insertSiteOutsourcing(HttpServletRequest request,
			@RequestParam("domain") String domain,
			@RequestParam("co_vat_no") String coVatNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("apl_user") String aplUser,
			@RequestParam("apl_time") String aplTime,
			@RequestParam("order_ids") String orderIds,
			@RequestParam("site_ids") String siteIds,
			@RequestParam("os_ids") String osIds,
			@RequestParam("mst_order_ids") String mstOrderIds,
			@RequestParam("item_ids") String itemIds,
			@RequestParam("prices") String prices,
			@RequestParam("numbers") String numbers,
			@RequestParam("ap_numbers") String apNumbers,
			@RequestParam("real_ap_numbers") String realApNumbers) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("domain", domain); // Main
			map.put("co_vat_no", coVatNo); // Main
			map.put("po_no", poNo); // Main
			map.put("apl_user", aplUser); // Main
			map.put("apl_time", aplTime); // Main
			map.put("order_ids", orderIds); // Master
			map.put("site_ids", siteIds); // Master
			map.put("os_ids", osIds); // // Master
			map.put("mst_order_ids", mstOrderIds); // Detail
			map.put("item_ids", itemIds); // Detail
			map.put("prices", prices); // Detail
			map.put("numbers", numbers); // Detail
			map.put("ap_numbers", apNumbers); // Detail
			map.put("real_ap_numbers", realApNumbers); // Detail
			pay006Service.insertSiteOutsourcing(map);
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.add.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 取得營業稅
	 * @param taxExclusiveAmt
	 * @return
	 */
	@RequestMapping(value = "/search/businessTax")
	@ResponseBody
	public Integer getBusinessTax(@RequestParam("tax_exclusive_amt") String taxExclusiveAmt) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PI_TYPE", "3"); // 計算營業稅
		map.put("PI_AMT", new BigDecimal(taxExclusiveAmt)); // 傳入未稅金額tax_exclusive_amt
		map.put("PI_INCLUDE_TAX", "N"); // 傳入金額是否含稅N:未稅
		pay006Service.callPayFnGetTax(map);
		return (Integer) map.get("RETURN_VALUE");
	}

	/**
	 * 取得申請人
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/search/authPersonnelByDomain")
	@ResponseBody
	public List<TbAuthPersonnel> getAuthPersonnel(
			@RequestParam("domain") String domain) {
		TbOrgDeptExample odExample = new TbOrgDeptExample();
		odExample.createCriteria().andDOMAINEqualTo(domain);
		List<TbOrgDept> odList = pay006Service.selectOrgDeptByExample(odExample);
		List<String> deptIdList = new ArrayList<String>();
		for (int i = 0; i < odList.size(); i++) {
			deptIdList.add(odList.get(i).getDEPT_ID());
		}
		
		TbAuthPersonnelExample apExample = new TbAuthPersonnelExample();
		TbAuthPersonnelExample.Criteria cr = apExample.createCriteria();
		cr.andEXPIRE_DATEIsNull();
		cr.andDEPT_IDIn(deptIdList);
		return pay006Service.selectAuthPersonnelByExample(apExample);
	}

	/**
	 * 手動設定jqGrid換頁功能
	 * @param inputPage
	 * @param inputSize
	 * @param list
	 * @return
	 */
	private JqGridData<Object> getJqGridData(String inputPage, String inputSize, List<Object> list) {
		if (list.size() == 0) {
			return new JqGridData<Object>(list.size(), list);
		}

		int page = Integer.valueOf(inputPage == null || inputPage.equals("") ? "1" : inputPage);
		int size = Integer.valueOf(inputSize == null || inputSize.equals("") ? "10" : inputSize);
		int startRow = (page - 1) * size;
		int endRow = (page * size) - 1;

		List<Object> results = new ArrayList<Object>();
		while (startRow <= endRow && startRow < list.size()) {
			results.add(list.get(startRow++));
		}

		return new JqGridData<Object>(list.size(), results);
	}
}
