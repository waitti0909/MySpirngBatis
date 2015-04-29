package com.foya.noms.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionListener implements HttpSessionListener {

	
	private final transient Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.debug("***************Session Create :"+se.getSession().getId()+" ************");
		log.debug("***************Session Create :"+se.getSession().getId()+" ************");		

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.debug("***************Session destroy :"+se.getSession().getId()+" ************");
		log.debug("***************Session destroy :"+se.getSession().getId()+" ************");		
	}

}
