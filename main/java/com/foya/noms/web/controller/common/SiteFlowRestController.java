package com.foya.noms.web.controller.common;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.common.CM004Service;
import com.foya.noms.service.st.MeterialOptService;
import com.foya.noms.service.st.OutsourcingService;
import com.foya.noms.service.st.ST002Service;
import com.foya.noms.service.st.ST003Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST005Service;
import com.foya.noms.service.st.ST009Service;
import com.foya.noms.service.st.ST010Service;
import com.foya.noms.service.st.line.LineApplyService;
import com.foya.noms.web.controller.BaseController;

/**
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
 * <td>2014/12/27</td>
 * <td>提供簽核流程完結之後，以REST方式由外部呼叫進入，在此處理各站台相關流程簽核完成後要做的事(含核可&駁回)</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
@Controller
public class SiteFlowRestController extends BaseController {

	@Autowired
	private ST002Service sT002Service;

	@Autowired
	private ST003Service sT003Service;

	@Autowired
	private ST004Service sT004Service;

	@Autowired
	private CM004Service cM004Service;

	@Autowired
	private OutsourcingService outsourcingService;

	@Autowired
	private LineApplyService lineApplyService;

	@Autowired
	private MeterialOptService meterialOptService;

	@Autowired
	private ST005Service sT005Service;

	@Autowired
	private ST009Service sT009Service;

	@Autowired
	private ST010Service sT010Service;

	/**
	 * 尋點申請 主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId
	 * @param action
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/st/st002/doFinalApply")
	@ResponseBody
	public String finishSiteHuntApply(@RequestParam("orderId") String workId, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("workId : " + workId);
		// throw new NomsException("test");
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			sT002Service.updateApprResult(workId, false);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			sT002Service.updateApprResult(workId, true);
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}

		return AJAX_SUCCESS;

		// return "YES";
	}

	/**
	 * 尋點點完工簽核New Site Request
	 * 
	 * @param orderId
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/st/st003/finishNewSiteRequestOrder")
	@ResponseBody
	public String finishNewSiteRequestOrder(@RequestParam("orderId") String orderId, @RequestParam("action") String action) {
		try {
			sT003Service.finishNewSiteRequestOrder(orderId, action);
		} catch (NomsException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("New Site Request Completion Fail , orderId = " + orderId + " , action = " + action);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("New Site Request Completion Fail , orderId = " + orderId + " , action = " + action);
		}
		return AJAX_SUCCESS;
	}

	/**
	 * 尋點點完工簽核Site Hunt
	 * 
	 * @param srId
	 * @param srSeq
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/st/st003/finishSiteHuntOrder")
	@ResponseBody
	public String finishSiteHuntOrder(@RequestParam("orderId") String orderId, @RequestParam("srId") String srId,
			@RequestParam("srSeq") String srSeq, @RequestParam("action") String action) {
		try {
			sT003Service.finishSiteHuntOrder(orderId, srId, srSeq, action);
		} catch (UpdateFailException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("Site Hunt Completion Fail , orderId = " + orderId + " , action = " + action + " , srId = " + srId
					+ " , srSeq = " + srSeq);
		} catch (NomsException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("Site Hunt Completion Fail , orderId = " + orderId + " , action = " + action + " , srId = " + srId
					+ " , srSeq = " + srSeq);
		} catch (CreateFailException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("Site Hunt Completion Fail , orderId = " + orderId + " , action = " + action + " , srId = " + srId
					+ " , srSeq = " + srSeq);
		}
		return AJAX_SUCCESS;
	}

	/**
	 * 委外申請簽核
	 * 
	 * @param osId
	 * @param action
	 * @return
	 * @author Charlie Woo
	 */
	@RequestMapping(value = "/st/st003/finishOsApply")
	@ResponseBody
	public String finishOsApply(@RequestParam("orderId") String osId, @RequestParam("action") String action) {
		log.info("=====>  start  委外申請簽核  =====");
		log.info("osId = " + osId + " , action = " + action);
		Date currentDate = new Date();
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) { // 駁回
			TbSiteOutsourcing tbSiteOutsourcing = outsourcingService.getByPrimaryKey(osId);
			tbSiteOutsourcing.setMD_TIME(currentDate);
			tbSiteOutsourcing.setMD_USER(getLoginUser().getUsername());
			tbSiteOutsourcing.setSTATUS(OutSourceStatus.OS03.name());
			outsourcingService.updateByPrimaryKeySelective(tbSiteOutsourcing);
		} else { // 核可
			try {
				outsourcingService.finishOsApply(osId, getLoginUser().getUsername());
			} catch (NomsException ne) {
				log.error(ne.getMessage(), ne);
				log.info("=====>  end  委外申請簽核  =====");
				throw new NomsException(ExceptionUtils.getFullStackTrace(ne));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				log.info("=====>  end  委外申請簽核  =====");
				throw new NomsException(ExceptionUtils.getFullStackTrace(e));
			}
		}
		log.info("=====>  end  委外申請簽核  =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 委外驗收簽核
	 * 
	 * @param osId
	 * @param action
	 * @return
	 */
	@RequestMapping(value = "/st/st003/finishOsVerify")
	@ResponseBody
	public String finishOsVerify(@RequestParam("orderId") String osId, @RequestParam("action") String action) {

		log.info("=====>  start  委外驗收簽核  =====");
		log.info("osId = " + osId + " , action = " + action);
		Date currentDate = new Date();
		try {

			if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) { // 駁回
				TbSiteOutsourcing tbSiteOutsourcing = outsourcingService.getByPrimaryKey(osId);
				tbSiteOutsourcing.setMD_TIME(currentDate);
				tbSiteOutsourcing.setMD_USER(getLoginUser().getUsername());
				tbSiteOutsourcing.setAP_DATE(null);
				tbSiteOutsourcing.setAP_USER(null);
				tbSiteOutsourcing.setAP_AMOUNT(BigDecimal.ZERO);
				tbSiteOutsourcing.setPAY_AMOUNT(tbSiteOutsourcing.getAMOUNT()); // 金額退回申請時
				tbSiteOutsourcing.setSTATUS(OutSourceStatus.OS05.name());
				outsourcingService.update(tbSiteOutsourcing);
			} else { // 核可
				outsourcingService.finishOsVerify(osId, getLoginUser().getUsername(), OutSourceStatus.OS07.name());
			}
		} catch (NomsException ne) {
			log.error(ne.getMessage(), ne);
			log.info("=====>  end  委外驗收簽核   =====");
			throw new NomsException(ExceptionUtils.getFullStackTrace(ne));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.info("=====>  end  委外驗收簽核   =====");
			throw new NomsException(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("=====>  end  委外驗收簽核   =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 建站申請簽核
	 */
	@RequestMapping(value = "/st/st004/doFinalApply")
	@ResponseBody
	public String finishSiteBuildApply(@RequestParam("orderId") String workId, @RequestParam("action") String action) {

		log.debug("workId : " + workId);
		log.debug("=====建站申請簽核 start =====");

		sT004Service.finishSiteBuildApply(workId, action);

		log.debug("=====建站申請簽核  end =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 領料申請簽核
	 */
	@RequestMapping(value = "/st/st005/finishMtApply")
	@ResponseBody
	public String finishMtApply(@RequestParam("orderId") String optId, @RequestParam("action") String action) {

		log.debug("optId : " + optId);
		log.debug("=====領料申請簽核 start =====");

		meterialOptService.finishMtApply(optId, action);
		log.debug("=====領料申請簽核  end =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 專線申請簽核
	 */
	@RequestMapping(value = "/st/line/lineApply/finishSiteLineApply")
	@ResponseBody
	public String finishSiteLineApply(@RequestParam("orderId") String appId, @RequestParam("action") String action) {
		log.info("=====>  start  專線申請簽核  =====");
		log.info("appId = " + appId + " , action = " + action);
		
		lineApplyService.finishSiteLineApply(appId, action);
		log.info("=====>  end  專線申請簽核   =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 建站工單完工驗收簽核
	 */
	@RequestMapping(value = "/st/st005/doFinalApply")
	@ResponseBody
	public String finishSiteBuildOrder(@RequestParam("orderId") String orderId, @RequestParam("action") String action) {

		log.debug("orderId : " + orderId);
		log.debug("=====建站完工簽核 start =====");

		sT005Service.finishSiteBuildOrder(orderId, action, getLoginUser().getUsername());

		log.debug("=====建站完工簽核  end =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 單張工單申請簽核
	 */
	@RequestMapping(value = "/st/st009/doFinalApply")
	@ResponseBody
	public String finishSingleOrderApply(@RequestParam("orderId") String workId, @RequestParam("action") String action) {

		log.debug("workId : " + workId);
		log.debug("=====單張工單申請簽核 start =====");

		sT009Service.finishSiteBuildApply(workId, action);

		log.debug("=====單張工單申請簽核  end =====");
		return AJAX_SUCCESS;
	}

	/**
	 * 單張工單完工驗收簽核
	 */
	@RequestMapping(value = "/st/st010/doFinalApply")
	@ResponseBody
	public String finishSiteSingleOrder(@RequestParam("orderId") String orderId, @RequestParam("action") String action) {

		log.debug("orderId : " + orderId);
		log.debug("=====單張工單完工簽核 start =====");

		sT005Service.finishSiteBuildOrder(orderId, action, getLoginUser().getUsername());

		log.debug("=====單張工單完工簽核  end =====");
		return AJAX_SUCCESS;
	}

}
