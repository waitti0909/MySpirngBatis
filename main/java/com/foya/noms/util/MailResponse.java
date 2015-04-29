package com.foya.noms.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;

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
 * <td>2014/8/4</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class MailResponse {

	public MailResponse(Mail mail) {
		super();
		this.mail = mail;
	}

	private Mail mail;
	private Throwable throwable;

	public boolean isSuccess() {
		return throwable == null;
	}

	public Mail getMail() {
		return mail;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getErrorMessage() {
		if (isSuccess()) {
			return "";
		}

		return formatThrowableMessage();
	}

	private String formatThrowableMessage() {
		if (throwable instanceof MessagingException) {
			return formatMessagingException();
		}
		if (throwable instanceof UnsupportedEncodingException) {
			return formatUnsupportedEncodingException();
		}
		return Throwables.getStackTraceAsString(throwable);
	}

	private String formatUnsupportedEncodingException() {
		return "不支援的郵件編碼";
	}

	private String formatMessagingException() {
		if (throwable instanceof SendFailedException) {
			return "寄發郵件失敗：" + formatSendFailedException();
		}
		return Throwables.getStackTraceAsString(throwable);
	}

	private String formatSendFailedException() {
		if (isInvalidAddress()) {
			return formatInvalidAddressMessage();
		}
		return Throwables.getStackTraceAsString(throwable);
	}

	private String formatInvalidAddressMessage() {
		return "含無效的EMAIL地址。請檢查以下EMAIL是否皆正確：" + Joiner.on(";").join(mail.getAllMailAddresses());
	}

	private boolean isInvalidAddress() {
		return StringUtils.contains(throwable.getMessage(), "Invalid Addresses");
	}

}
