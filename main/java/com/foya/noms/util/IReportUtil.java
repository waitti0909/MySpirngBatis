package com.foya.noms.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class IReportUtil {

	// ------------------------------------------------
	/**
	 * Jasper File path
	 */
	private final String jasperPath;

	/**
	 * HttpServletResponse Header Name
	 */
	private final String headerName = "Content-Disposition";

	/**
	 * HttpServletResponse Header Value
	 */
	private final String headerValue = "attachment; filename=";

	/**
	 * jasperName
	 */
	private String jasperName;

	// ------------------------------------------------
	// ------------------------------------------------
	// ------------------------------------------------

	/**
	 * 
	 * @param jasperName
	 *            jasper file name
	 */
	public IReportUtil(String jasperName,HttpServletRequest request) {

		this.jasperName = "/"+jasperName;
		
		this.jasperPath = request.getServletContext().getRealPath("WEB-INF/classes/jasper");
//		this.jasperPath = getClass().getClassLoader().getResource("jasper")
//				.getPath();
	}

	/**
	 * Get jasper file
	 * 
	 * @return jasper InputStream
	 */
	public InputStream getJasperInputStream() throws FileNotFoundException {

		return new FileInputStream(getJasperPath() + getJasperName()
				+ ".jasper");
//		return new FileInputStream(getJasperPath() + getJasperName()
//				+ ".jasper");
	}

	/**
	 * Get JasperReport
	 * 
	 * @return JasperReport
	 */
	public JasperReport getJasperReport() throws FileNotFoundException,
			JRException {

		return (JasperReport) JRLoader.loadObject(getJasperInputStream());
	}

	/**
	 * Get JRDataSource
	 * 
	 * @param beanCollection
	 *            Collection<?>
	 * 
	 * @return JRDataSource
	 */
	public JRDataSource getJRDataSource(Collection<?> beanCollection) {

		return new JRBeanCollectionDataSource(beanCollection);
	}

	/**
	 * mount JRPdfExporter
	 */
	public void mountJRPdfExporter(HttpServletResponse response,
			JasperPrint jasperPrint) throws IOException, JRException {

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(
				response.getOutputStream()));

		exporter.exportReport();
	}

	/***************************************************************************************/
	public String getJasperPath() {

		return jasperPath;
	}

	public String getHeaderName() {

		return headerName;
	}

	public String getHeaderValue(String downloadFileName) {

		return headerValue + downloadFileName;
	}

	public String getJasperName() {
		return jasperName;
	}

}
