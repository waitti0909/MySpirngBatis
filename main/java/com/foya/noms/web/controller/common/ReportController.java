package com.foya.noms.web.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.noms.service.auth.PersonnelService;

@Controller
@RequestMapping("/report")
public class ReportController {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	@Autowired
	private PersonnelService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/report/")
	public ModelAndView list(ModelAndView modelAndView) {
		modelAndView = new ModelAndView("report/report");
		return modelAndView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/pdf")
	public ModelAndView generatePdfReport(ModelAndView modelAndView) {

		logger.debug("--------------generate PDF report----------");

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		List<TbAuthPersonnel> usersList = userService.getAllPsn();

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);

		parameterMap.put("datasource", JRdataSource);

		// pdfReport bean has ben declared in the jasper-views.xml file
		modelAndView = new ModelAndView("pdfReport", parameterMap);

		return modelAndView;

	}// generatePdfReport

	@RequestMapping(method = RequestMethod.GET, value = "/xls")
	public ModelAndView generateXlsReport(ModelAndView modelAndView) {

		logger.debug("--------------generate XLS report----------");

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		List<TbAuthPersonnel> usersList = userService.getAllPsn();

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);

		parameterMap.put("datasource", JRdataSource);

		// xlsReport bean has ben declared in the jasper-views.xml file
		modelAndView = new ModelAndView("xlsReport", parameterMap);

		return modelAndView;

	}// generatePdfReport

	@RequestMapping(method = RequestMethod.GET, value = "/csv")
	public ModelAndView generateCsvReport(ModelAndView modelAndView) {

		logger.debug("--------------generate CSV report----------");

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		List<TbAuthPersonnel> usersList = userService.getAllPsn();

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);

		parameterMap.put("datasource", JRdataSource);

		// xlsReport bean has ben declared in the jasper-views.xml file
		modelAndView = new ModelAndView("csvReport", parameterMap);

		return modelAndView;

	}// generatePdfReport

	@RequestMapping(method = RequestMethod.GET, value = "/html")
	public ModelAndView generateHtmlReport(ModelAndView modelAndView) {

		logger.debug("--------------generate HTML report----------");

		Map<String, Object> parameterMap = new HashMap<String, Object>();

		List<TbAuthPersonnel> usersList = userService.getAllPsn();

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(usersList);

		parameterMap.put("datasource", JRdataSource);

		// xlsReport bean has ben declared in the jasper-views.xml file
		modelAndView = new ModelAndView("htmlReport", parameterMap);

		return modelAndView;

	}// generatePdfReport

}// ReportController