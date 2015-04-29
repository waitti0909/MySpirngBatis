package com.foya.noms.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * <td>2014/12/30</td>
 * <td>依據傳入設計常數，判斷需要實作的PDF class並以interface回傳</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class PdfCreateFactory {	// FIXME 好像可以再調整pattern

	@Autowired
	private DemoPDFService demo;
	
	public PdfExportCreator getPdfCreator(String type) {
		
		PdfExportCreator creator = null;
		
		if (StringUtils.equals("combo", type)) {
			creator = demo;
		} else if (StringUtils.equals("", type)) {
			// TODO
		}
		return creator;
	}
}
