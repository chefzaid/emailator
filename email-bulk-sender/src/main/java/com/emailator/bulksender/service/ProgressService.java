package com.emailator.bulksender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.business.ProgressManager;

@Service
public class ProgressService {

	@Autowired
	private ProgressManager progressManager;

	public Progress findAll(String bulkEmailUuid) {
		return progressManager.findAll(bulkEmailUuid);
	}

	public Progress findOne(String bulkEmailUuid, String emailAddress) {
		return progressManager.findOne(bulkEmailUuid, emailAddress);
	}

}
