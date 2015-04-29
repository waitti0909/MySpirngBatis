package com.foya.noms.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJob extends QuartzJobBean {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		log.debug("QuartzJob....................." + context.getJobRunTime());

	}
}
