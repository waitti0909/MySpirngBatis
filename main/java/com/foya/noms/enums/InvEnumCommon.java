package com.foya.noms.enums;

import java.util.ArrayList;

public class InvEnumCommon {

	/** @author Arvin.Tsai */

	/**
	 * 調撥進度<BR>
	 * 1:草稿[DRAFT]<BR>
	 * 2:送審中[SUBMITING]<BR>
	 * 3:核可[APPROVED]<BR>
	 * 4:部份調出[PART_EXIT]<BR>
	 * 5:調出[EXIT]<BR>
	 * 6:調入[IN]<BR>
	 * 7:結案[CLOSED]<BR>
	 * 8:刪除[DELETED]<BR>
	 * 9:結案(有返回件)[CLOSE_LOST]
	 * 
	 */
	public static enum Allocationstatus {

		NO_DEFINED(-1, "未定義", ""),

		DRAFT(1, "草稿", "draft"),

		SUBMITING(2, "送審中", "submiting"),

		APPROVED(3, "核可", "approved"),

		PART_EXIT(4, "部份調出", "partExit"),

		EXIT(5, "調出", "exit"),

		IN(6, "調入", "callIn"),

		CLOSED(7, "結案", "closed"),

		DELETED(8, "刪除", "deleted"),

		CLOSE_LOST(9, "結案(有返回件)", "closeLost"),
		
		RETURN(10,"駁回","return");

		private final String value;

		private final int code;

		private final String name;

		private Allocationstatus(int code, String value, String name) {
			this.code = code;
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static Allocationstatus toSource(int code) {

			for (Allocationstatus status : Allocationstatus.values()) {
				if (status.code == code) {
					return status;
				}
			}

			return NO_DEFINED;
		}

		/**
		 * 取得Code List
		 */
		public static Object[] getCodeList() {

			ArrayList<String> codeArray = new ArrayList<String>();

			for (Allocationstatus status : Allocationstatus.values()) {
				codeArray.add(String.valueOf(status.getCode()));
			}

			return codeArray.toArray();
		}

	}

	/**
	 * 物料狀態<BR>
	 * 1:可用品[NORMAL]<BR>
	 * 2:待送修[PENDING_REPAIR]<BR>
	 * 3:待報廢[PENDING_INVALID]
	 * 
	 */
	public static enum ProductStatus {

		NO_DEFINED(-1, "未定義", ""),

		NORMAL(1, "可用品", "normal"),

		PENDING_REPAIR(2, "待送修", "pendingRepair"),

		PENDING_INVALID(3, "待報廢", "pendingInvalid");

		private final String value;

		private final int code;

		private final String name;

		private ProductStatus(int code, String value, String name) {
			this.value = value;
			this.code = code;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static ProductStatus toSource(int code) {

			for (ProductStatus status : ProductStatus.values()) {
				if (status.code == code) {
					return status;
				}
			}

			return NO_DEFINED;
		}

		/**
		 * 取得Code List
		 */
		public static Object[] getCodeList() {

			ArrayList<String> codeArray = new ArrayList<String>();

			for (ProductStatus status : ProductStatus.values()) {
				codeArray.add(String.valueOf(status.getCode()));
			}

			return codeArray.toArray();
		}

	}

	/**
	 * 作業類型<BR>
	 * 1:發料[SEND_MATERIAL]<BR>
	 * 2:調出[EXIT]<BR>
	 * 3:拆下[REMOVE]
	 * 
	 */
	public static enum OnHandStatus {

		NO_DEFINED(-1, "未定義", ""),

		SEND_MATERIAL(1, "發料", "sendMaterial"),

		EXIT(2, "調出", "exit"),

		REMOVE(3, "拆下", "remove");

		private final String value;

		private final int code;

		private final String name;

		private OnHandStatus(int code, String value, String name) {
			this.value = value;
			this.code = code;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static OnHandStatus toSource(int code) {

			for (OnHandStatus status : OnHandStatus.values()) {
				if (status.code == code) {
					return status;
				}
			}

			return NO_DEFINED;
		}

		/**
		 * 取得Code List
		 */
		public static Object[] getCodeList() {

			ArrayList<String> codeArray = new ArrayList<String>();

			for (OnHandStatus status : OnHandStatus.values()) {
				codeArray.add(String.valueOf(status.getCode()));
			}

			return codeArray.toArray();
		}

	}

	/**
	 * 作業類型<BR>
	 * 0:到料入庫[IN_WAREHOUSING]<BR>
	 * 1:發料[SEND_MATERIAL]<BR>
	 * 2:收料[INCOME_MATERIAL]<BR>
	 * 3:調出[EXIT]<BR>
	 * 4:調入[IN]<BR>
	 * 5:拆下[STATUS_CHANGE]<BR>
	 * 6:返回調出倉[RETURN_EXPORT_DEPOT]<BR>
	 */
	public static enum DepotBusinessStatus {

		NO_DEFINED(-1, "未定義", ""),

		IN_WAREHOUSING(0, "到料入庫", "inWarehousing"),

		SEND_MATERIAL(1, "發料", "sendMaterial"),

		INCOME_MATERIAL(2, "收料", "incomeMaterial"),

		EXIT(3, "調出", "exit"),

		IN(4, "調入", "callIn"),

		STATUS_CHANGE(5, "拆下", "statusChange"),

		RETURN_EXPORT_DEPOT(6, "返回調出倉", "returnExportDepot");

		private final String value;

		private final int code;

		private final String name;

		private DepotBusinessStatus(int code, String value, String name) {
			this.value = value;
			this.code = code;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static DepotBusinessStatus toSource(int code) {

			for (DepotBusinessStatus status : DepotBusinessStatus.values()) {
				if (status.code == code) {
					return status;
				}
			}

			return NO_DEFINED;
		}

		/**
		 * 取得Code List
		 */
		public static Object[] getCodeList() {

			ArrayList<String> codeArray = new ArrayList<String>();

			for (DepotBusinessStatus status : DepotBusinessStatus.values()) {
				codeArray.add(String.valueOf(status.getCode()));
			}

			return codeArray.toArray();
		}

	}
	
	/**
	 * 送修/報廢原因<BR>
	 * 	送修報廢原因1 <BR>
	 *	送修報廢原因2 <BR>
	 *	送修報廢原因3 <BR>
	 *	送修報廢原因4 <BR>
	 *	送修報廢原因5 <BR>
	 */
	public static enum faultReason {

		NO_DEFINED("", "未定義", ""),

		REASON1("F001", "送修報廢原因1", "REASON1"),

		REASON2("F002", "送修報廢原因2", "REASON2"),

		REASON3("F003", "送修報廢原因3", "REASON3"),

		REASON4("F004", "送修報廢原因4", "REASON4"),

		REASON5("F005", "送修報廢原因5", "REASON5");

		private final String value;

		private final String code;

		private final String name;

		private faultReason(String code, String value, String name) {
			this.value = value;
			this.code = code;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static faultReason toSource(String code) {

			for (faultReason status : faultReason.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}

		/**
		 * 取得Code List
		 */
		public static Object[] getCodeList() {

			ArrayList<String> codeArray = new ArrayList<String>();

			for (faultReason status : faultReason.values()) {
				codeArray.add(status.getCode());
			}

			return codeArray.toArray();
		}

	}
	
}
