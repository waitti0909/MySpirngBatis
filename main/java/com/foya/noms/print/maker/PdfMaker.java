package com.foya.noms.print.maker;

import java.io.IOException;
import java.util.Map;

import com.foya.exception.NomsException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public interface PdfMaker {

	/**
	 * 列印PDF API
	 * @param document
	 * @param reader
	 * @param writer
	 * @throws NomsException
	 * @throws DocumentException
	 * @throws IOException
	 * @author Charlie Woo
	 */
	public void makePdf(Document document, PdfReader reader, PdfWriter writer, Map<String, String> map) throws NomsException, DocumentException, IOException;

}
