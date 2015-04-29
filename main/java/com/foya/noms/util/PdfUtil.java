package com.foya.noms.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.foya.noms.resources.AppConstants;

public class PdfUtil {

	public static String getRealPath(String path) {
		return FilenameUtils.normalize(AppConstants.WebRealPathRoot + path);
	}
	
	public static String getExportFileName(String path) {
		return "Combo_" + StringUtils.substringAfterLast(path, "/");
	}
}
