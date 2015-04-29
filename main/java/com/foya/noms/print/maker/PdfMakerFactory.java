package com.foya.noms.print.maker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.foya.exception.NomsException;
import com.foya.noms.enums.PrintPdfDoc;

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
 * <td>PDF Maker Factory</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class PdfMakerFactory {

	private Map<PrintPdfDoc, PdfMaker> makers = new HashMap<>();

	public PdfMaker create(PrintPdfDoc pdf) throws NomsException {
		PdfMaker maker = makers.get(pdf);
		if ( isEmpty(maker) ) {
			throw new NomsException("Unsupported.AppFormId[" + pdf + "]");
		}
		return maker;
	}

	public void regist(PrintPdfDoc pdf, PdfMaker maker) {
		makers.put(pdf, maker);
	}

	public static boolean isEmpty(Object obj) {
		boolean result = true;
		
		if ( obj instanceof Collection ) {
			result = (isNull(obj) || ((Collection<?>) obj).isEmpty());
		} else if ( obj instanceof Map ) {
			result = (isNull(obj) || ((Map<?, ?>) obj).isEmpty());
		} else if ( obj instanceof Object[] ) {
			result = ArrayUtils.isEmpty((Object[]) obj);
		} else if ( obj instanceof String ) {
			result = StringUtils.isEmpty((String) obj);
		} else {
			result = isNull(obj);
		}
		
		return result;
	}
	
	public static boolean isNull(Object obj) {
		return obj == null;
	}
}
