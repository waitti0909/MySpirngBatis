package com.foya.noms.service.workflow;

import org.springframework.stereotype.Component;

@Component
public interface WorkflowRestService {

	public String callByRest(String type, String docNo, String action);
}
