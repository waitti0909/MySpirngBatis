package com.foya.noms.web.controller.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.exception.NomsException;
import com.foya.noms.enums.LsEnumCommon.AppStatusEnum;
import com.foya.noms.enums.LsEnumCommon.LsStatusEnum;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.ls.LS001M3Service;
import com.foya.noms.service.ls.LsCommonService;
import com.foya.noms.web.controller.BaseController;


/**
 * 提供簽核流程完結之後，以REST方式由外部呼叫進入，在此處理租約相關流程簽核完成後要做的事(含核可&駁回)
 *
 */
@Controller
public class LeaseFlowRestController extends BaseController {
	
	@Autowired
	private LsCommonService lsCommonService;
	
	@Autowired
	private LS001M3Service lS001M3Service;
	
	/**
	 * 租約_新約  主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doNewLeaseApply")
	@ResponseBody
	public String doNewLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus2.getAppStatus(), 
					LsStatusEnum.lsStatus01.getLsStatus(), appSeq);
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 租約_續約  主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doContinueLeaseApply")
	@ResponseBody
	public String doContinueLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lS001M3Service.doContinueLeaseApply(appSeq,getLoginUser().getUsername());
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 租約_換約 主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doChangeLeaseApply")
	@ResponseBody
	public String doChangeLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lS001M3Service.doChangeLeaseApply(appSeq,getLoginUser().getUsername());			
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 租約_一解一簽 主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doReSignLeaseApply")
	@ResponseBody
	public String doReSignLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus2.getAppStatus(), 
					LsStatusEnum.lsStatus01.getLsStatus(), appSeq);
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 租約_解約  主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doCancelLeaseApply")
	@ResponseBody
	public String doCancelLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lS001M3Service.doCancelLeaseApply(appSeq,getLoginUser().getUsername());
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 租約_增補協議 主管最後核可/駁回申請 透過REST進入
	 * 
	 * @param orderId 申請單號
	 * @param action 狀態
	 * @return
	 * @throws Exception
	 * @throws NomsException
	 */
	@RequestMapping(value = "/ls/flowRest/doExtraLeaseApply")
	@ResponseBody
	public String doExtraLeaseApply(@RequestParam("orderId") String appSeq, @RequestParam("action") String action) throws NomsException, Exception {
		log.debug("appSeq : " + appSeq);
		
		if (AppConstants.WORKFLOW_REST_REJECT.equals(action)) {
			//申請駁回
			lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus9.getAppStatus(), "", appSeq);
		} else if (StringUtils.equals(AppConstants.WORKFLOW_REST_APPROVAL, action)) {
			//申請核可
			lS001M3Service.doExtraLeaseApply(appSeq ,this.getLoginUser().getUsername());
		} else {
			throw new NomsException("Unvaliable action value:" + action + ", Please check flow result.");
		}
		return AJAX_SUCCESS;
	}
}
