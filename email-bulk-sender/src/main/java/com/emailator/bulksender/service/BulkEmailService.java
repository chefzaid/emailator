package com.emailator.bulksender.service;

import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.business.BulkEmailManager;
import com.emailator.bulksender.business.ProgressManager;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class BulkEmailService {

	@Autowired
	private BulkEmailManager bulkEmailClient;
	@Autowired
	private ProgressManager progressManager;

	public String send(BulkEmail bulkEmail) {
		String uuid = UUID.randomUUID().toString();
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
				progressManager.updateState(recipient, ProgressState.SENDING);
				bulkEmailClient.send(msg, recipient);
				progressManager.updateState(recipient, ProgressState.SENT);
			} catch (MessagingException e) {
				log.error("Error while sending email to: " + recipient.getEmailAddress(), e);
				progressManager.updateState(recipient, ProgressState.FAILED);
			}
		}
		return uuid;
	}

}
