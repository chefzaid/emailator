package com.emailator.bulksender.business;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class BulkEmailClient {

	public boolean send(BulkEmail bulkEmail) {
		log.info("Trying to send email [{" + bulkEmail.getId() + "}]");
		boolean isSuccessfullySent = true;
		try {
			// Set SMTP server configuration
			SmtpConfiguration smtpConf = bulkEmail.getSmtpConfiguration();
			Properties props = System.getProperties();
			props.put(Constants.KEY_SMTP_HOST, smtpConf.getHost());
			props.put(Constants.KEY_SMTP_TLS_ENABLE, smtpConf.getEnableStartTls());
			if (smtpConf.getPort() != null) {
				props.put(Constants.KEY_SMTP_PORT, smtpConf.getPort());
			}

			// Set credentials if given
			Authenticator auth = null;
			if (smtpConf.getEnableAuthentication() == Boolean.TRUE) {
				auth = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpConf.getUsername(), smtpConf.getPassword());
					}
				};
			}
			Session session = Session.getDefaultInstance(props, auth);

			// Set basic details
			Email email = bulkEmail.getEmail();
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email.getSender()));
			msg.setSubject(email.getSubject());
			msg.setSentDate(new Date());

			Multipart multipart = new MimeMultipart();

			// Add message body
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(email.getBody(), MediaType.TEXT_HTML);
			multipart.addBodyPart(messageBodyPart);

			// Add attachments
			for (String attachmentFilePath : email.getAttachmentsPaths()) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(attachmentFilePath);
				multipart.addBodyPart(attachmentPart);
			}
			msg.setContent(multipart);

			// Add and send to recipients, one by one
			for (String recipient : bulkEmail.getRecipients()) {
				Address[] recipients = new Address[] { new InternetAddress(recipient) };
				msg.setRecipients(Message.RecipientType.TO, recipients);
				Transport.send(msg);
			}

		} catch (Exception e) {
			isSuccessfullySent = false;
			log.error("Error while sending the email", e);
		}
		return isSuccessfullySent;
	}

}
