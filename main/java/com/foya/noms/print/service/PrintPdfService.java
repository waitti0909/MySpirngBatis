package com.foya.noms.print.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.exception.NomsException;
import com.foya.noms.enums.PrintPdfDoc;
import com.foya.noms.print.maker.PdfMaker;
import com.foya.noms.print.maker.PdfMakerFactory;
import com.foya.noms.service.BaseService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PrintPdfService extends BaseService implements PrintService {

	@Autowired
	private PdfMakerFactory factory;
	
	@Override
	public ByteArrayOutputStream makePdfs(TbLsAppForm pdf, Map<String, String> map) throws IOException, DocumentException, NomsException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document();
		PdfReader reader = null;
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		Rectangle pageSize = PageSize.A4; // 直向
//		Rectangle pageSizeRotate = pageSize.rotate(); // 橫向
		
		document.open();		
		PdfMaker maker = factory.create(PrintPdfDoc.valueOf(pdf.getFORM_ID()));
		if ( !PdfMakerFactory.isEmpty(maker) ) {
//			reader = new PdfReader(PdfUtil.getRealPath(pdf.getPATH()));  // reader讀取文件路徑
			reader = new PdfReader(new FileInputStream(new File(pdf.getPATH())));
			
			document.setPageSize(pageSize);	// 橫、直向
			
			maker.makePdf(document, reader, writer, map);
		}
		
		document.close();
		log.debug("baos.toByteArray.length::: " + baos.toByteArray().length);
		
		return baos;
	}
}
