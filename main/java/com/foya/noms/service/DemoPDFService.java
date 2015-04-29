package com.foya.noms.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsAppFormExample;
import com.foya.noms.dao.ls.AppFormDao;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

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
 * <td>2014/12/26</td>
 * <td>產生標準格線的範例PDF，This is just a sample.</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
@Service
public class DemoPDFService extends BaseService implements PdfExportCreator {

	@Autowired
	private AppFormDao appFormDao;
	
	int printY = 0;
	int printX = 290;

	@Override
	public ByteArrayOutputStream create(String pdfPath) {
		ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();

		Rectangle rt = PageSize.A4;

		// step 1
		Document document = new Document();

		try {
			// step 2: we set the ContentType and create an instance of the corresponding Writer
			PdfWriter writer = PdfWriter.getInstance(document, baosPDF);

			// step 3 open document.
			document.open();
			// step 4 add content
			PdfContentByte cb = writer.getDirectContent();
			// step 5 read template
//			PdfReader reader = new PdfReader(pdfPath);
			PdfReader reader = new PdfReader(new FileInputStream(new File(pdfPath)));
			PdfImportedPage page1 = writer.getImportedPage(reader, 1);
			cb.addTemplate(page1, 0, 0);
			
			// step 6 press content
			cb.beginText();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			// 浮水格線
            PdfGState gstate = new PdfGState();
            gstate.setFillOpacity(0.5f);
            gstate.setStrokeOpacity(0.5f);
            cb.setGState(gstate);
            // 字體顏色
			cb.setColorFill(Color.BLACK);
			cb.setLineWidth(0.3f);
			cb.setFontAndSize(bf, 6);
			
			for (; printY < rt.getHeight(); printY += 150) {				
				for (int x = 0; x < rt.getWidth(); x += 10) {
					if (x > 99) {
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "" + x / 10, x, printY, 0);
					} else {
						cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "" + x, x, printY, 0);
					}
				}
			}

			for (int y = 0; y < rt.getHeight(); y += 10) {
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "" + y, printX, y, 0);

				cb.moveTo(0, y);
				cb.lineTo(rt.getWidth(), y);
				cb.stroke();
			}
			
			for (int x = 0; x < rt.getWidth(); x += 10) {
				cb.moveTo(x, 0);
				cb.lineTo(x, rt.getHeight());
				cb.stroke();
			}

			for (int y = 0; y < rt.getHeight(); y += 10) {
				cb.moveTo(0, y);
				cb.lineTo(rt.getWidth(), y);
				cb.stroke();
			}

			cb.endText();

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		// Final: we close the document (the outputstream is also closed internally)
		document.close();

		return baosPDF;
	}
	
	public TbLsAppForm getAppFormByFormId(String formId) {
		TbLsAppFormExample example = new TbLsAppFormExample();
		example.createCriteria().andFORM_IDEqualTo(formId);
		List<TbLsAppForm> forms = appFormDao.findByExample(example);
		if (!forms.isEmpty()) {
			return appFormDao.findByExample(example).get(0);
		}
		return null;
	}
}
