package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY016Service;
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
@RequestMapping(value = "/pay/pay016")
public class PAY016Controller extends BaseController {

	@Autowired
	private PAY016Service pay016Service;

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

		session.setAttribute("pay016_orgDomainList", domainService.getStandardDomainList()); // 維運區
		session.setAttribute("pay016_companyList", pay016Service.selectComCompanyByExample(new TbComCompanyExample())); // 業者
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo("OSA_STATUS");
		session.setAttribute("pay016_statusList", pay016Service.selectPayLookupCodeByExample(example)); // 申請狀態

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		model.put("cr_time_s", cal.getTime()); // 申請起日
		model.put("cr_time_e", new Date()); // 申請迄日

		return "ajaxPage/pay/PAY016";
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
		
		List<TbPayOutsourceAcceptanceDTO> list = pay016Service.selectPayOutsourceAcceptanceByExamplePage(example);
		PageList<TbPayOutsourceAcceptanceDTO> page = (PageList<TbPayOutsourceAcceptanceDTO>) list;
		return new JqGridData<TbPayOutsourceAcceptanceDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 儲存傳票號碼、日期
	 * @param request
	 * @param apNos 工程驗收序號
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<Object> save(HttpServletRequest request,
			@RequestParam("ap_nos") String apNos,
			@RequestParam("subpoena_nbrs") String subpoenaNbrs,
			@RequestParam("subpoena_dates") String subpoenaDates) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ap_nos", apNos);
			map.put("subpoena_nbrs", subpoenaNbrs);
			map.put("subpoena_dates", subpoenaDates);
			pay016Service.save(map);
			return new BaseJasonObject<Object>(true, "委外驗收傳票變更儲存成功");
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}
}
