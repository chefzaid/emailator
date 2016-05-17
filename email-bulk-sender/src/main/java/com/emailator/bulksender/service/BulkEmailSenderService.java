package com.emailator.bulksender.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.business.BulkEmailClient;
import com.emailator.bulksender.business.ProgressManager;
import com.emailator.bulksender.dao.BulkEmailDao;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class BulkEmailSenderService {

	@Autowired
	private BulkEmailDao bulkEmailDao;
	@Autowired
	private ProgressManager progressManager;
	@Autowired
	private BulkEmailClient bulkEmailClient;

	public void send(BulkEmail bulkEmail) {
		log.debug("Saving and sending...");
		// Set bulkEmail ID
		bulkEmail.setUuid(UUID.randomUUID().toString());
		// Init the progress state of all emails to PENDING
		progressManager.initStates(bulkEmail);
		// Save bulkEmail non-sensitive data to DB
		bulkEmailDao.save(bulkEmail);
		// Send bulkEmail
		bulkEmailClient.send(bulkEmail);
	}

}
