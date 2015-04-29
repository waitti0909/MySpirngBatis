/**
 * @author Arvin.Tsai
 */
package com.foya.noms.dto.pay;

import com.foya.dao.mybatis.model.TbPayFileFormat;
import com.foya.dao.mybatis.model.TbPayFileFormatDtl;

public class TbPayFileFormatDtlDTO extends TbPayFileFormatDtl {

	// -------------------------------------------------------------------
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// private Type [table][column][action]
	/********************************************************************
	 * TB_PAY_FILE_FORMAT [檔案格式主檔] : format<BR>
	 *********************************************************************/
	// ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※
	// -------------------------------------------------------------------

	/**
	 * 
	 */
	private TbPayFileFormat fileFormat;

	/**********************************************************************/
	public TbPayFileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(TbPayFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}
}
