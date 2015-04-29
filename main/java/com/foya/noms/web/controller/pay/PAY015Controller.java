package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY015Service;
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
@RequestMapping(value = "/pay/pay015")
public class PAY015Controller extends BaseController {

	@Autowired
	private PAY015Service pay015Service;

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

		session.setAttribute("pay015_orgDomainList", domainService.getStandardDomainList()); // 維運區
		session.setAttribute("pay015_companyList", pay015Service.selectComCompanyByExample(new TbComCompanyExample())); // 業者

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		model.put("ap_date_s", cal.getTime()); // 驗收起日
		model.put("ap_date_e", new Date()); // 驗收迄日

		return "ajaxPage/pay/PAY015";
	}

	/**
	 * 查詢工程驗收清單
	 * @param request
	 * @param domain 維運區
	 * @param coVatNo 業者
	 * @param poNo PO
	 * @param apDateS 驗收起日
	 * @param apDateE 驗收迄日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySiteOutsourcing")
	@ResponseBody
	public JqGridData<TbSiteOutsourcingDTO> query(
			HttpServletRequest request, @RequestParam("domain") String domain,
			@RequestParam("co_vat_no") String coVatNo,
			@RequestParam("po_no") String poNo,
			@RequestParam("ap_date_s") String apDateS,
			@RequestParam("ap_date_e") String apDateE) throws Exception {

		UTbSiteOutsourcingExample example = new UTbSiteOutsourcingExample();
		UTbSiteOutsourcingExample.Criteria cr = example.createCriteria();
		if (coVatNo != null && coVatNo != "") {
			cr.andCO_VAT_NOEqualTo(coVatNo);
		}
		if (apDateS != null && !apDateS.equals("") && apDateE != null && !apDateE.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			cr.andAP_DATEBetween(sdf.parse(apDateS + " 00:00:00.000"), sdf.parse(apDateE + " 23:59:59.999"));
		}
		if (poNo != null && poNo != "") {
			cr.andLike("B.PO_NO", "%" + poNo + "%");
		}
		if (domain != null && domain != "") {
			cr.andEqualTo("F.DOMAIN", domain);
		}
		cr.andSTATUSEqualTo("OS07"); // 已驗收
		cr.andEqualTo("isnull(A.PAY_FLG, 'N')", "N");

		List<TbSiteOutsourcingDTO> list = pay015Service.selectSiteOutsourcingByExamplePage(example);
		PageList<TbSiteOutsourcingDTO> page = (PageList<TbSiteOutsourcingDTO>) list;
		return new JqGridData<TbSiteOutsourcingDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢工單資訊
	 * @param request
	 * @param osId 站點工程代碼
	 * @param poId PO
	 * @return
	 */
	@RequestMapping(value = "/querySiteOsItem")
	@ResponseBody
	public JqGridData<TbSiteOsItemDTO> querySiteOsItem(
			HttpServletRequest request, @RequestParam("os_id") String osId,
			@RequestParam("po_id") String poId) {

		UTbSiteOsItemExample example = new UTbSiteOsItemExample();
		UTbSiteOsItemExample.Criteria cr = example.createCriteria();
		cr.andOS_IDEqualTo(osId);
		cr.andPO_IDEqualTo(Integer.valueOf(poId).longValue());

		List<TbSiteOsItemDTO> list = pay015Service.selectSiteOsItemByExamplePage(example);
		PageList<TbSiteOsItemDTO> page = (PageList<TbSiteOsItemDTO>) list;
		return new JqGridData<TbSiteOsItemDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 執行複驗
	 * @param request
	 * @param os_ids 工程驗收序號
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<Object> save(HttpServletRequest request,
			@RequestParam("os_ids") String osIds) {
		try {
			List<String> osIdList = new ArrayList<String>();
			String[] osId = osIds.split(",");
			for (int i = 0; i < osId.length; i++) {
				osIdList.add(osId[i]);
			}

			TbSiteOutsourcingDTO record = new TbSiteOutsourcingDTO();
			record.setPAY_FLG("Y");
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());

			UTbSiteOutsourcingExample example = new UTbSiteOutsourcingExample();
			UTbSiteOutsourcingExample.Criteria cr = example.createCriteria();
			cr.andIn("OS_ID", osIdList);
			pay015Service.updateSiteOutsourcinByExampleSelective(record, example);

			return new BaseJasonObject<Object>(true, "委外驗收複驗變更儲存成功");
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}
}
