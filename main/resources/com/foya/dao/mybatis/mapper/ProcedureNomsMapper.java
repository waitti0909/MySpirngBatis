package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface ProcedureNomsMapper {

	/**
	 * 用電方式=借電_抄表時 取得請款金額
	 * @param map
	 */
	void PAY_PC_GET_ELEC_AMT(Map<String, Object> map);
	
	/**
	 * 自動產生單號
	 * @param map
	 */
	void PAY_PC_GET_SEQNO(Map<String, Object> map);
	/**
	 * 計算所得稅/健保費/營業稅   PAY_FN_GET_TAX
	 * @param map
	 */
	void PAY_FN_GET_TAX(Map<String, Object> map);
	/**
	 * 租約異動-取得資料   PAY_PC_GET_APP_AMT
	 * @param map :
	 * 	PI_DOMAIN          -- 維運區代碼          
		PI_CONTRACT_NO     -- 合約編號
		PI_LOCATION_ID     -- LOCATION_ID                          
		PI_PAYMENT_ITEM    -- R:租金/RD:租金押金/E:用電/ED:用電押金
		PI_REQUEST_DATES   -- 請款日期(起)                         
		PI_REQUEST_DATEE   -- 請款日期(迄)                         
		PI_APP_DATE        -- 執行請款日期                         
		PI_AP_YYYYMM       -- 請款年月 YYYYMM                      
		PI_APP_TYPE        -- 首期(F)/例行(R)/補請(B)              
		PI_PAYMENT_PERIOD  -- 付款週期                             
		PO_TOT_APP_AMT     -- 總含稅金額                           
		PO_TOT_BS_TAX_AMT  -- 總營業稅金額                         
		PO_TOT_IN_TAX_AMT  -- 總所得稅金額                         
		PO_TOT_HS_AMT        -- 總健保補充保費                     
		PO_APP_START_DATE                                          
		PO_APP_END_DATE                                            
		PO_ERR_CDE                                                 
		PO_ERR_MSG  
		
	 * @param list of cursor : 
	 * LS_NO              NVARCHAR(20),
		LS_VER             NVARCHAR(2),
		LOCATION_ID        NVARCHAR(20),
		RECIPIENT_ID       NVARCHAR(20),
		LESSOR_NAME        NVARCHAR(50),
		RECIPIENT_NAME     NVARCHAR(50),
		LESSOR_ID          NVARCHAR(20),
		PAYMENT_MODE       NVARCHAR(5),
		UNIT_CODE          NVARCHAR(10),
		SUB_UNIT_CODE      NVARCHAR(10),
		ACCOUNT_NO         DECIMAL(10,0) =>Long,
		BUSINESS_TAX_AMT   DECIMAL(10,0),
		INCOM_TAX_AMT      DECIMAL(10,0),
		HS_REFILL_AMT      DECIMAL(10,0),
		PAY_AMT            DECIMAL(10,0),
		PAY_ITEM           NVARCHAR(10),
		PAY_TYPE           NVARCHAR(10),
		DOCUMENT_NO        NVARCHAR(50),
		ENERGY_METER       NVARCHAR(30),
		PAY_ATTACH         NVARCHAR(10)
	 */
	List<Map<String, Object>>  PAY_PC_GET_APP_AMT(Map<String, Object> map);
	/**
	 * 租約異動-新增資料   PAY_PC_GET_APP_AMT
	 * @param map :
	 * 	@PI_TIME_STAMP  -- 暫存資料KEY
		@PI_PROCESS_TYPE -- 處理類別
		@PI_PAYMENT_PERIOD -- 付款週期
		@PI_APP_YYYYMM    -- 請款年月
		@PI_APP_USER     -- 申請人員代碼
		@PI_APP_DATE     -- 申請日期    
		*/
	void PAY_PC_PROC_APP_AMT(Map<String, Object> map);
	
	void  PAY_PC_SUSPEND_APP(Map<String, Object> map);
}
