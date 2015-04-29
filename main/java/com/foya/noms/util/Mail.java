package com.foya.noms.util;

import java.io.File;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import com.foya.noms.resources.AppConstants;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

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
 * <td>2014/8/4</td>
 * <td>新建檔案</td>
 * <td>Administrator</td>
 * </tr>
 * </table> 
 *
 * @author Administrator
 *
 */
public class Mail {

	public static final Function<String, InternetAddress> TO_INTERNET_ADDRESS = new Function<String, InternetAddress>() {
		@Override
		public InternetAddress apply(String s) {
			try {
				return new InternetAddress(s);
			} catch (AddressException e) {
				throw new RuntimeException(e);
			}
		}
	};

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("mailFrom", mailFrom).append("mailTo", mailTo)
				.append("ccTo", ccTo).append("subject", subject).append("content", content).append("attachments", attachments).toString();
	}

	public static Mail by(Iterable<String> mailTo, String subject, String content) {
		return by(mailTo, null, subject, content, null);
	}

	public static Mail by(Iterable<String> mailTo, String subject, String content, File[] attachments) {
		return by(mailTo, null, subject, content, attachments);
	}

	public static Mail by(Iterable<String> mailTo, Iterable<String> ccTo, String subject, String content) {
		return by(mailTo, ccTo, subject, content, null);
	}

	public static Mail by(Iterable<String> mailTo, Iterable<String> ccTo, String subject, String content, File[] attachments) {
		return new Mail(mailTo, ccTo, subject, content, attachments);
	}

	private Mail(Iterable<String> mailTo, Iterable<String> ccTo, String subject, String content, File[] attachments) {
		super();
		Assert.notNull(subject, "mail args subject cant not be null");
		Assert.notNull(content, "mail args content cant not be null");
		Assert.notNull(mailTo, "mail args mailTo can not be null");
		this.mailTo = mailTo;
		this.ccTo = ccTo;
		this.subject = subject;
		this.content = content;
		this.attachments = attachments;
		this.mailFrom = AppConstants.MAIL_FROM;
	}

	private String mailFrom;

	private Iterable<String> mailTo;

	private Iterable<String> ccTo;

	private String subject;

	private String content;

	private File[] attachments;

	public Iterable<String> getMailTo() {
		return mailTo;
	}

	public void setMailTo(Iterable<String> mailTo) {
		this.mailTo = mailTo;
	}

	public Iterable<String> getCcTo() {
		return ccTo;
	}

	public void setCcTo(Iterable<String> ccTo) {
		this.ccTo = ccTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

	public Properties getProps() {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", AppConstants.MAIL_SMTP_HOST);
		props.put("mail.transport.protocol", AppConstants.MAIL_SMTP_PROTOCAL);
		props.put("mail.smtp.auth", AppConstants.MAIL_SMTP_AUTHENTICATION);
		return props;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public InternetAddress[] getMailToInternetAddresses() {
		return Iterables.toArray(Iterables.transform(mailTo, TO_INTERNET_ADDRESS), InternetAddress.class);
	}

	public InternetAddress[] getCcToInternetAddresses() {
		if (ccTo == null) {
			return null;
		}
		return Iterables.toArray(Iterables.transform(ccTo, TO_INTERNET_ADDRESS), InternetAddress.class);
	}

	public Set<String> getAllMailAddresses() {
		Set<String> mails = Sets.newHashSet(mailTo);
		Iterables.addAll(mails, Optional.fromNullable(ccTo).or(Sets.<String> newHashSet()));
		return mails;
	}

}
