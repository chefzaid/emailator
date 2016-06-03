package com.emailator.bulksender.service;

import java.util.UUID;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.business.BulkEmailClient;
import com.emailator.bulksender.business.ProgressManager;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class BulkEmailService {

	@Autowired
	private BulkEmailClient bulkEmailClient;
	@Autowired
	private ProgressManager progressManager;

	public String send(BulkEmail bulkEmail) {
		String uuid = UUID.randomUUID().toString();
		log.debug("Trying to send email " + bulkEmail.getUuid());
		// Init and save bulkEmail to DB
		bulkEmail.setUuid(uuid);
		for (Recipient recipient : bulkEmail.getRecipients()) {
			recipient.setProgress(new Progress());
		}
		bulkEmailClient.save(bulkEmail);

		// Send message to each recipients, one by one		
		Message msg = bulkEmailClient.buildMessage(bulkEmail);
		for (Recipient recipient : bulkEmail.getRecipients()) {
			try {
				Address address = new InternetAddress(recipient.getEmailAddress());
				msg.setRecipient(Message.RecipientType.TO, address);
				progressManager.updateState(recipient, ProgressState.SENDING);
				log.debug("Async sending message to " + recipient.getEmailAddress());
				bulkEmailClient.asyncSend(msg);
				log.debug("Message sent to " + recipient.getEmailAddress());
				progressManager.updateState(recipient, ProgressState.SENT);
			} catch (MessagingException e) {
				log.error("Error while sending email to: " + recipient.getEmailAddress(), e);
				progressManager.updateState(recipient, ProgressState.FAILED);
			}
		}
		return uuid;
	}

}
