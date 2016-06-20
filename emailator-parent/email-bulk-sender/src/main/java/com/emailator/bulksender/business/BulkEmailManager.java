package com.emailator.bulksender.business;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.dao.BulkEmailDao;
import com.emailator.bulksender.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class BulkEmailManager {

	@Autowired
	private BulkEmailDao bulkEmailDao;

	private Session buildSession(BulkEmail bulkEmail) {
		// Set SMTP server configuration
		SmtpConfiguration smtpConf = bulkEmail.getSmtpConfiguration();
		Properties props = new Properties();
		props.put(Constants.KEY_SMTP_HOST, smtpConf.getHost());
		props.put(Constants.KEY_SMTP_TLS_ENABLE, smtpConf.getEnableStartTls().toString());
		if (smtpConf.getPort() != null) {
			props.put(Constants.KEY_SMTP_PORT, smtpConf.getPort().toString());
		}

		// Set credentials if given
		Authenticator auth = null;
		if (smtpConf.getEnableAuthentication() == Boolean.TRUE) {
			props.put(Constants.KEY_SMTP_AUTH, smtpConf.getEnableStartTls().toString());
			auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpConf.getUsername(), smtpConf.getPassword());
				}
			};
		}
		return Session.getDefaultInstance(props, auth);
	}

	public Message buildMessage(BulkEmail bulkEmail) {
		Session session = buildSession(bulkEmail);
		Message msg = new MimeMessage(session);
		try {
			// Set basic details
			Email email = bulkEmail.getEmail();
			msg.setFrom(new InternetAddress(email.getSender()));
			msg.setSubject(email.getSubject());
			msg.setSentDate(new Date());

			// Init Message content
			Multipart multipart = new MimeMultipart();
			msg.setContent(multipart);

			// Add message body
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(email.getBody(), MediaType.TEXT_HTML);
			multipart.addBodyPart(messageBodyPart);

			// Add attachments (if any)
			if (email.getAttachmentsPaths() != null) {
				for (String attachmentFilePath : email.getAttachmentsPaths()) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.attachFile(attachmentFilePath);
					multipart.addBodyPart(attachmentPart);
				}
			}
		} catch (MessagingException | IOException e) {
			log.error("Error while building the email", e);
		}
		return msg;
	}

	public void send(Message msg, Recipient recipient) throws MessagingException {
		Address address = new InternetAddress(recipient.getEmailAddress());
		msg.setRecipient(Message.RecipientType.TO, address);
		log.debug("Sending message to " + recipient.getEmailAddress());
		Transport.send(msg);
		log.debug("Message sent to " + recipient.getEmailAddress());
	}

	@Transactional
	public void save(BulkEmail bulkEmail) {
		log.debug("Saving to database: " + bulkEmail.getUuid());
		bulkEmailDao.save(bulkEmail);
	}

}
