package com.foya.noms.web.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.dao.mybatis.model.TbWorkflowCfgExample;
import com.foya.exception.NomsException;
import com.foya.noms.service.workflow.WorkflowCfgService;
import com.foya.noms.util.RestClient;

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
 * <td>2014/12/3</td>
 * <td>本程式提供簽核流程完成後由外部呼叫</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
@Controller
public class FlowActionRestController extends BaseController implements ApplicationContextAware {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WorkflowCfgService workflowCfgService;

	private ApplicationContext applicationContext;

	private final static Set<String> existParam;
	static {
		existParam = new HashSet<>(3);
		existParam.add("type");
		existParam.add("docNo");
		existParam.add("action");
	}

	/**
	 * 透過 ApplicationContextAware get 目前的 ApplicationContext
	 * 
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(value = "/flowActionRest")
	@ResponseBody
	public String flowActionRest(HttpServletRequest request) {
		validateBaseArgument(request);

		String type = request.getParameter("type");

		String restResult = "";
		TbWorkflowCfgExample example = new TbWorkflowCfgExample();
		example.createCriteria().andPROCESS_TYPEEqualTo(type);
		List<TbWorkflowCfg> cfgs = workflowCfgService.findByCondition(example);

		if (!cfgs.isEmpty()) {
			for (TbWorkflowCfg cfg : cfgs) {
				if (cfg.getREST_IMP_URI() != null && cfg.getREST_IMP_URI().length() > 0) {
					try {
						return doAction(request, cfg.getREST_IMP_URI());
					} catch (MalformedURLException e) {
						throw new NomsException(e.getMessage());
					}
				} else {
					restResult = "Error! ProcessType: '" + type
							+ "' is not setted REST_IMP_URI in TB_WORKFLOW_CFG.";
				}
			}
		} else {
			restResult = "Error! No '" + type + "' process type setting in TB_WORKFLOW_CFG.";
		}
		// 沒設定 rest URL or process type
		throw new NomsException(restResult);

	}

	private String doAction(HttpServletRequest request, String restImpUri)
			throws MalformedURLException {
		String type = request.getParameter("type");
		String docNo = request.getParameter("docNo");
		String action = request.getParameter("action");
		Map<String, String> workflowParamMap = getExtraParam(request.getParameterMap());
		URL url = new URL(request.getRequestURL().toString());
		RestClient client = new RestClient(url.getProtocol() + "://" + url.getAuthority());
		client.setContextPath("noms");
		client.setLoginPath("loginProcess");
		String loginResult = client.login("SYSTEM", "1");
		log.debug("loginResult = {} ", loginResult);

		client.setMethod(HttpMethod.POST);
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
		paramMap.add("orderId", docNo);
		paramMap.add("type", type);
		paramMap.add("action", action);
		if (workflowParamMap != null) {
			paramMap.setAll(workflowParamMap);
		}
		ResponseEntity<String> respEntity = client.exchange(restImpUri, paramMap);
		String result = respEntity.getBody().toString();
		log.debug("docNo:+" + docNo + ", result=" + result);
		return result;
	}

	private void validateBaseArgument(HttpServletRequest request) {
		if (StringUtils.isBlank(request.getParameter("type"))) {
			throw new NomsException("Error! type is blank");
		}
		if (StringUtils.isBlank(request.getParameter("docNo"))) {
			throw new NomsException("Error! docNo is blank");
		}
		if (StringUtils.isBlank(request.getParameter("action"))) {
			throw new NomsException("Error! action is blank");
		}
	}

	private Map<String, String> getExtraParam(Map<String, String[]> paramArray) {
		Map<String, String> extraMap = new HashMap<>();
		for (Entry<String, String[]> entry : paramArray.entrySet()) {
			String key = entry.getKey();
			if (existParam.contains(key)) {
				continue;
			}
			String[] value = entry.getValue();
			if (value != null && value.length > 0) {
				extraMap.put(key, value[0]);
			}
		}
		return extraMap;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
