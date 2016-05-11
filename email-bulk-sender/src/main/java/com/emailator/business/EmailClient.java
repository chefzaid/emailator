package com.emailator.business;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.emailator.beans.BulkEmail;
import com.emailator.beans.Email;
import com.emailator.beans.SmtpConfiguration;
import com.emailator.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class EmailClient {

	public boolean send(BulkEmail bulkEmail){
		log.info("Trying to send email...");
		boolean isSuccessfullySent = true;
		try {
			SmtpConfiguration smtpConf = bulkEmail.getSmtpConfiguration();
			Properties props = System.getProperties();
			props.put(Constants.KEY_SMTP_HOST, smtpConf.getHost());
			props.put(Constants.KEY_SMTP_TLS_ENABLE, smtpConf.getEnableStartTls());
			if(smtpConf.getPort() != null){
				props.put(Constants.KEY_SMTP_PORT, smtpConf.getPort());	
			}
			
			Authenticator auth = null; 
			if(smtpConf.getEnableAuthentication() == Boolean.TRUE){
				auth	= new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpConf.getUsername(), smtpConf.getPassword());
					}
				};
			}
			Session session = Session.getDefaultInstance(props, auth);

			Email email = bulkEmail.getEmail();
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email.getSender()));
			msg.setSubject(email.getSubject());
			msg.setText(email.getBody());
			msg.setSentDate(new Date());
			// TODO attached files
			
			for(String recipient : bulkEmail.getRecipients()){
				Address[] recipientArray = new Address[]{ new InternetAddress(recipient) };
				msg.setRecipients(Message.RecipientType.TO, recipientArray);
				Transport.send(msg);
			}
			
		} catch (Exception e) {
			isSuccessfullySent = false;
			log.error("Error while sending an email", e);
		}
		return isSuccessfullySent;
	}

}
