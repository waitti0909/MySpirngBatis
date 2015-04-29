package com.foya.noms.print.maker.ls;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.foya.noms.enums.PrintPdfDoc;
import com.foya.noms.print.maker.DefaultMaker;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;

/**
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
 * <td>2015/2/4</td>
 * <td>手機收受簽收確認書(初版HardCode)</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class LSF10PdfMaker extends DefaultMaker {

	/**
	 * 對此Maker進行註冊管理
	 */
	protected void regist() {
		getFactory().regist(PrintPdfDoc.LSF10, this);
	}

	@Override
	protected PdfContentByte makeContent(PdfContentByte cb, Map<String, String> keyMap) throws DocumentException, IOException {
		
		log.debug("LSF10Maker....processing");
		cb.beginText();	// 壓印開始
		
		cb.setColorFill(Color.BLACK); // 字體顏色
		cb.setFontAndSize(bfkaiu, 12);// 字型及字體大小
		
		// FIXME 待實作
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "LS001", 500, 805, 0);	// 合約編號
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "L100014", 500, 788, 0);	// 站台編號
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "查理王", 130, 640, 0);	// 出租人
//		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "查理王/查理吳/查理哥", 90, 640, 0);	// 多出租人
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "2015", 285, 640, 0);	// 簽約年
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "01", 355, 640, 0);	// 簽約月
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "12", 420, 640, 0);	// 簽約日
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "LS001", 290, 600, 0);	// 合約編號
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "2015", 90, 570, 0);	// 手機年
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "01", 165, 570, 0);	// 手機月
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "13", 240, 570, 0);	// 手機日
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "2", 420, 570, 0);	// 手機支數
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "HTC new one mobile M8", 105, 530, 0);	// 機型
		
		cb.endText();	// 壓印結束
		return cb;
	}

}
