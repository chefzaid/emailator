package com.emailator.bulksender.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.business.BulkEmailClient;
import com.emailator.bulksender.dao.BulkEmailDao;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class BulkEmailService {

	@Autowired
	private BulkEmailDao bulkEmailDao;
	@Autowired
	private BulkEmailClient bulkEmailClient;

	public String send(BulkEmail bulkEmail) {
		log.debug("Saving and sending...");
		String uuid = UUID.randomUUID().toString();
		bulkEmail.setUuid(uuid);
		bulkEmailDao.save(bulkEmail);
		bulkEmailClient.send(bulkEmail);
		return uuid;
	}

}
