package com.emailator.business;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.beans.BulkEmail;
import com.emailator.beans.Email;
import com.emailator.utils.Constants;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class EmailClient {
	
	@Autowired
	private ConfigurationLoader configuration;
	
	public boolean send(BulkEmail bulkEmail){
		log.info("Trying to send email");
		boolean isSuccessfullySent = true;
		try {
			Email email = bulkEmail.getEmail();
			Address sender = new InternetAddress(email.getSender());
			Properties props = System.getProperties();
			props.put(Constants.KEY_SMTP_HOST, configuration.getProperty(Constants.KEY_SMTP_HOST));
			Session session = Session.getDefaultInstance(props, null);

			Message msg = new MimeMessage(session);
			msg.setFrom(sender);
			msg.setSubject(email.getSubject());
			msg.setText(email.getBody());
			msg.setHeader("X-Mailer", "Emailator");
			msg.setSentDate(new Date());
			// TODO attached files
			
			for(String recipient : bulkEmail.getRecipients()){
				Address[] recipientArray = new Address[]{ new InternetAddress(recipient) };
				msg.setRecipients(Message.RecipientType.TO, recipientArray);
				Transport.send(msg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			isSuccessfullySent = false;
			log.error("Error while sending an email", e);
		}
		return isSuccessfullySent;
	}

}
