package com.foya.noms.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTask {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void sayHello() {
		log.debug("MyTask.sayHello !!! ");
	}

}
