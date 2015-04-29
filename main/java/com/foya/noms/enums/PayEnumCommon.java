/** @author Arvin.Tsai */
package com.foya.noms.enums;

public class PayEnumCommon {

	/**
	 * 處理類別<BR>
	 * ELE03 : 借電_其他<BR>
	 * ELE04 : 請電<BR>
	 * REN01 : 押金<BR>
	 * REN02 : 首期<BR>
	 * REN03 : 例行<BR>
	 * REN04 : 補請<BR>
	 * ELE01 : 借電_固定金額<BR>
	 * ELE02 : 借電_抄表<BR>
	 * LIN : 專線<BR>
	 * MIS : 雜項<BR>
	 * ELE05 : 押金<BR>
	 * ELE06 : 預付<BR>
	 */
	public static enum ProcessType {

		NO_DEFINED("-1", "未定義"),

		ELE03("ELE03", "借電_其他"),

		ELE04("ELE04", "請電"),

		REN01("REN01", "押金"),

		REN02("REN02", "首期"),

		REN03("REN03", "例行"),

		REN04("REN04", "補請"),

		ELE01("ELE01", "借電_固定金額"),

		ELE02("ELE02", "借電_抄表"),

		LIN("LIN", "專線"),

		MIS("MIS", "雜項"),

		ELE05("ELE05", "押金"),

		ELE06("ELE06", "預付");

		private final String value;

		private final String code;

		private ProcessType(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static ProcessType toSource(String code) {

			for (ProcessType status : ProcessType.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
	}

	/**
	 * 付款狀態<BR>
	 * P : 付款<BR>
	 * N : 正常<BR>
	 * D : 作廢<BR>
	 * E : 作廢重開<BR>
	 * R : 重匯<BR>
	 */
	public static enum PaymentStatus {

		NO_DEFINED("-1", "未定義"),

		ELE03("P", "付款"),

		ELE04("N", "正常"),

		REN01("D", "作廢"),

		REN02("E", "作廢重開"),

		REN03("R", "重匯");

		private final String value;

		private final String code;

		private PaymentStatus(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static PaymentStatus toSource(String code) {

			for (PaymentStatus status : PaymentStatus.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
	}

	/**
	 * 申請狀態<BR>
	 * A : 待審核<BR>
	 * B : 審核中<BR>
	 * C : 核可<BR>
	 * D : 駁回<BR>
	 * E : 會計駁回<BR>
	 * F : 送ＥＲＰ<BR>
	 * G : 付款<BR>
	 */
	public static enum CashReqStatus {

		NO_DEFINED("-1", "未定義"),

		A("A", "待審核"),

		B("B", "審核中"),

		C("C", "核可"),

		D("D", "駁回"),

		E("E", "會計駁回"),

		F("F", "送ERP"),

		G("G", "付款");

		private final String value;

		private final String code;

		private CashReqStatus(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static CashReqStatus toSource(String code) {

			for (CashReqStatus status : CashReqStatus.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
	}

	/**
	 * 專線用途<BR>
	 * B : 基站<BR>
	 * M : 機房<BR>
	 * P : POI<BR>
	 * D : 辦公室<BR>
	 * S : 門市<BR>
	 * V : 加值<BR>
	 * O : 其他<BR>
	 */
	public static enum CircuiTuses {

		NO_DEFINED("-1", "未定義"),

		B("B", "基站"),

		M("M", "機房"),

		P("P", "POI"),

		D("D", "辦公室"),

		S("S", "門市"),

		V("V", "加值"),

		O("O", "其他");

		private final String value;

		private final String code;

		private CircuiTuses(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static CircuiTuses toSource(String code) {

			for (CircuiTuses status : CircuiTuses.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
	}

	/**
	 * 憑證類型<BR>
	 * INVD : 二聯式發票 <BR>
	 * INVT : 三聯式發票 <BR>
	 * RECD : 二聯式收據 <BR>
	 * RECT : 三聯式收據 <BR>
	 * OTH : 其它
	 */
	public static enum VoucherType {

		NO_DEFINED("-1", "未定義"),

		INVD("INVD", "二聯式發票"),

		INVT("INVT", "三聯式發票"),

		RECD("RECD", "二聯式收據"),

		RECT("RECT", "三聯式收據"),

		OTH("OTH", "其它");

		private final String value;

		private final String code;

		private VoucherType(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getCode() {
			return code;
		}

		/**
		 * 以 code 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static VoucherType toSource(String code) {

			for (VoucherType status : VoucherType.values()) {
				if (status.code.equals(code)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
		
		/**
		 * 以 value 反轉回對應的 DataStatus
		 * 
		 * @param code
		 *            the code
		 * @return DataStatus
		 */
		public static VoucherType toSourceByValue(String value) {

			for (VoucherType status : VoucherType.values()) {
				if (status.value.equals(value)) {
					return status;
				}
			}

			return NO_DEFINED;
		}
	}

}
