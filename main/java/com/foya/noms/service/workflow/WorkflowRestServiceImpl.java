package com.foya.noms.service.workflow;

import org.springframework.stereotype.Service;

import com.foya.noms.service.BaseService;

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
 * <td>2014/12/4</td>
 * <td>This is just a fucking example. Do not COPY but just know how to implements rest way.</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class WorkflowRestServiceImpl extends BaseService implements WorkflowRestService {

	@Override
	public String callByRest(String type, String docNo, String action) {
		if ("SearchSiteOutsourcingApply".equals(type)) {
			return "Outsourcing Apply OK";
		} else if ("SearchSiteOutsourcingAccept".equals(type)) {
			return "Outsourcing Accept OK";
		}
		return "Outsourcing failed";
	}
}
