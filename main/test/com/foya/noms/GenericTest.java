package com.foya.noms;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations= {"classpath:spring/applicationContext.xml",
//		"classpath:spring/appServlet/mvc-controllers.xml","classpath:spring/appServlet/jasper-views.xml",
//		"classpath:spring/appServlet/mvc-servlet.xml"})
@ContextConfiguration(locations= {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback=true, transactionManager = "transactionManager")
@Transactional(isolation=Isolation.READ_UNCOMMITTED)
@Ignore(value="This is base test class with no test methods")
public abstract class GenericTest {

	protected Logger log = Logger.getLogger(this.getClass());
	
}
