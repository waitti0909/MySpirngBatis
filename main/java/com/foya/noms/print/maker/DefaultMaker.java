package com.foya.noms.print.maker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.foya.noms.resources.AppConstants;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
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
 * <td>2015/2/3</td>
 * <td>此PDF將固定產生: <br/>
 * 1. 新的一頁<br/>
 * 2. 嵌入template<br/>
 * 3. 產生makeContent(PdfContentByte, Map<String, String>)的內容<br/></td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table>
 * 
 * @author Charlie Woo
 * 
 */
public abstract class DefaultMaker implements PdfMaker {

	protected transient Logger log = Logger.getLogger(getClass());
	protected static BaseFont bfkaiu = null;

	@Autowired
	private PdfMakerFactory factory;

	public PdfMakerFactory getFactory() {
		return factory;
	}

	/**
	 * 註冊Maker<br/>
	 * 開發一支新的Maker後,
	 * 應自行註冊到com.foya.noms.print.maker.PdfMakerFactory.regist(String, PdfMaker)中
	 */
	@PostConstruct
	protected abstract void regist();

	/**
	 * 若有需要改變列印單頁模式或其他特殊設定再自行Override
	 */
	@Override
	public void makePdf(Document document, PdfReader reader, PdfWriter writer, Map<String, String> map) throws DocumentException, IOException {
		PdfContentByte cb = writer.getDirectContent();

		// 字型(標楷體)
		bfkaiu = BaseFont.createFont(AppConstants.BASEFONT_TYPE_KAIU, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 新的一頁
		document.newPage();

		// 嵌入template
		PdfImportedPage page1 = writer.getImportedPage(reader, 1);
		cb.addTemplate(page1, 0, 0);

		// 產生壓印內容
		makeContent(cb, map);
	}

	/**
	 * 壓印內容
	 * 
	 * @param cb
	 * @param map
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 * @author Charlie Woo
	 */
	protected abstract PdfContentByte makeContent(PdfContentByte cb, Map<String, String> map) throws DocumentException, IOException;

	/**
	 * 壓印文字過長自動換行
	 * @param cb
	 * @param itemWords (過長文字)
	 * @param maxLength (換行長度)
	 * @param x
	 * @param y
	 */
	protected void autoLinefeedIfOverMaxlength(PdfContentByte cb, String itemWords, int maxLength, float x, float y) {

		List<String> itemList = new ArrayList<String>();
		String itemword = "";
		String wordflag = "";
		int wordCount = 0;
		float shiftNumY = 0;

		for (int i = 0; i < itemWords.length(); i++) {
			itemword = itemword + itemWords.substring(i, i + 1);
			wordflag = itemWords.substring(i, i + 1);
			if (isChinese(wordflag)) {
				wordCount += 2;
			} else {
				wordCount++;
			}

			if (wordCount >= maxLength) {
				itemList.add(itemword);
				itemword = "";
				wordCount = 0;
			}
		}
		itemList.add(itemword);
		for (String s : itemList) {
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, s, x, y + shiftNumY, 0);
			shiftNumY -= 10;
		}
	}

	private static boolean isChinese(String str) {
		if (str == null)
			return false;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		if (m.find())
			return true;
		return false;
	}
}
