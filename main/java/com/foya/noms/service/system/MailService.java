package com.foya.noms.service.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.Mail;
import com.foya.noms.util.MailResponse;
import com.google.common.collect.Iterables;

/**
 * 寄送Mail
 * 
 * @author Charlie Woo
 * 
 */
public abstract class MailService extends BaseService {

	private String encoding = "UTF-8";
	
	protected static final String SEMICOLON = ";";
	
	protected static final String COMMA = ",";

	@Async
	public MailResponse sendMail(Mail mail) {
		Assert.notNull(mail, "mail must not be null.");

		log.debug("sending mail... {}", mail);
		MailResponse response = new MailResponse(mail);

		try {
			Message message = new MimeMessage(getMailSession(mail));
			message.setFrom(new InternetAddress(mail.getMailFrom(), AppConstants.MAIL_FROM));
			message.addRecipients(Message.RecipientType.TO, mail.getMailToInternetAddresses());
			if (mail.getCcTo() != null && !Iterables.isEmpty(mail.getCcTo())) {
				message.addRecipients(Message.RecipientType.CC, mail.getCcToInternetAddresses());
			}
			message.setSubject(MimeUtility.encodeText(mail.getSubject(), encoding, null));
			message.setSentDate(DateTime.now().toDate());

			Multipart multiPart = new MimeMultipart();
			MimeBodyPart part = new MimeBodyPart();
			part.setText(mail.getContent(), encoding, "html");
			multiPart.addBodyPart(part);

			// 附件
			if (mail.getAttachments() != null) {
				for (File txt : mail.getAttachments()) {
					part = new MimeBodyPart();
					String fileName = txt.getName();
					log.debug("fileName is {}", fileName);
					DataSource source = new FileDataSource(txt);
					part.setDataHandler(new DataHandler(source));
					part.setFileName(MimeUtility.encodeText(fileName, encoding, null));
					multiPart.addBodyPart(part);
				}
			}

			// message.setContent(multiPart, "text/html;charset=UTF-8");
			message.setContent(multiPart);
			log.debug("message={}", message);
			Transport.send(message);

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setThrowable(e);
		}

		log.debug("send done.");

		return response;
	}
	
	private Session getMailSession(Mail mail) {
		if (AppConstants.MAIL_SMTP_AUTHENTICATION) {			
			return Session.getInstance(mail.getProps(), new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(AppConstants.MAIL_SMTP_AUTH_USER, AppConstants.MAIL_SMTP_AUTH_PASSWORD);
				}
			});
		} else {
			return Session.getInstance(mail.getProps());
		}
	}
	
	/**
	 * 取得EMAIL對象清單
	 * @param mails
	 * @return
	 */
	protected List<String> getEmailPersonList(String mails) {
		return getEmailPersonList(new ArrayList<String>(), mails, null);
	}
	
	protected List<String> getEmailPersonList(List<String> mailList, String mails, String splitToken) {
		
		if (StringUtils.isEmpty(mails)) return mailList;
		
		if (StringUtils.isNotEmpty(splitToken) && StringUtils.contains(mails, splitToken)) {
			for (String mailTo : StringUtils.split(mails, splitToken)) {
				mailList.add(mailTo);
			}
		} else {
			mailList.add(mails);
		}
		return mailList;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
