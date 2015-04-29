package com.foya.noms.resources;

import java.util.List;

/**
 * 此程式用於系統常用參數(可init by property or xml)
 * 
 * @author Charlie Woo
 * 
 */
public class AppConstants {
	// 環境
	public static final String DATE_PATTERN = "yyyy/MM/dd";
	public static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
	public static String ENVIRONMENT = "";
	public static String WORKFLOW_REST_HOST = "http://127.0.0.1:9090";
	public static String NOMS_HOST = "http://127.0.0.1:8080";
	
	// 共用
	public static Integer PWD_MIN_LENGTH;
	public static Integer PWD_MAX_LENGTH;
	public static Integer PWD_PERIOD;
	public static Integer PWD_KEEP_TIMES;
	public static Integer PWD_ERR_TIMES;
	public static Boolean PWD_MIX_ENG_NUM;

	public static String MAIL_SMTP_HOST;
	public static String MAIL_SMTP_PROTOCAL;
	public static Boolean MAIL_SMTP_AUTHENTICATION;
	public static String MAIL_SMTP_AUTH_USER;
	public static String MAIL_SMTP_AUTH_PASSWORD;
	public static String MAIL_FROM;

	public static List<String> MAIL_TO_IT;
	public static List<String> CC_TO_IT;


	public static final String INSERT = "新增";

	public static final String EDIT = "編輯";

	public static final String UPDATE = "更新";

	public static final String DELETE = "刪除";

	public static final String SUCCESS = "成功";

	public static final String FAILED = "失敗";

	public static final String WORKFLOW_REST_APPROVAL = "APPROVAL";
	public static final String WORKFLOW_REST_REJECT = "REJECT";
	public static String WORKFLOW_FAIL_HANDLER = "admin";
	public static String WORKFLOW_FAIL_HANDLER_TASK_NAME = "Rest fail handler";
	
	// ExcelPdf型態
	public static final String APPLICATION_PDF = "application/pdf";
	public static final String APPLICATION_EXCEL = "application/vnd.ms-excel";

	public static final String FILE_UPLOAD_ACTION = "UPLOAD";
	public static final String FILE_DOWNLOAD_ACTION = "DOWNLOAD";
	public static final String FILE_REPLACE_ACTION = "REPLACE";
	public static final String FILE_DELETE_ACTION = "DELETE";

	public static final int DEFAULTPAGENUMER = 1;
	public static final int DEFAULTDATASIZEPERPAGE = 10;

	/** 共站業者 */
	public static final String SHARECOM = "SHARECOM";
	/** 異動類別 */
	public static final String LINE_APPLY_TYPE = "LINE_APPLY_TYPE";
	/** 電路用途 */
	public static final String LINE_PURPOSE = "CIRCUIT_USES";
	/** 電路類別 */
	public static final String LINE_TYPE = "CIRCUIT_TYPE";
	/** 專線業者 */
	public static final String LINE_ISP = "LINE_ISP";
	
	/**使用類別*/
	public static final String LINE_USE_TYPE = "LINE_USE_TYPE";
	
	/**速率*/
	public static final String LINE_TYPE_ADSL = "CIRCUIT_TYPE_ADSL";
	public static final String LINE_TYPE_FIB = "CIRCUIT_TYPE_FIB";
	public static final String LINE_TYPE_SDH = "CIRCUIT_TYPE_SDH";
	public static final String LINE_TYPE_VPN = "CIRCUIT_TYPE_VPN";
	public static final String LINE_TYPE_WAVE = "CIRCUIT_TYPE_WAVE";
	public static final String LINE_TYPE_ELL = "CIRCUIT_TYPE_ELL";
	
	public static String WebRealPathRoot = "";
	public static String BASEFONT_TYPE_KAIU;
	
}