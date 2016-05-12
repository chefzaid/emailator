package com.emailator.bulksender.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.business.BulkEmailClient;
import com.emailator.bulksender.dao.BulkEmailDao;

@Service
public class BulkEmailService {
	
	@Autowired
	private BulkEmailDao bulkEmailDao;
	@Autowired
	private BulkEmailClient bulkEmailClient;

	public void send(BulkEmail bulkEmail) {
		// TODO Manage exceptions + logs
		// Set bulkEmail ID
		String bulkEmailId = UUID.randomUUID().toString();
		bulkEmail.setBulkEmailId(bulkEmailId);
		// Save bulkEmail non-sensitive data to DB
		bulkEmailDao.save(bulkEmail);
		// Send bulkEmail
		bulkEmailClient.send(bulkEmail);
	}

}
