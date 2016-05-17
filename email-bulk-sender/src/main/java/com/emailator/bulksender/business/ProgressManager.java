package com.emailator.bulksender.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.dao.ProgressDao;

import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class ProgressManager {

	@Autowired
	private ProgressDao progressDao;

	public void initStates(BulkEmail bulkEmail) {
		log.debug("Init states to PENDING for all emails in " + bulkEmail.getUuid());
		Progress progress = new Progress();
		progress.setState(ProgressState.PENDING);
		progress.setCreationTime(new Date());
		progress.setLastUpdateTime(new Date());
		for (Recipient recipient : bulkEmail.getRecipients()) {
			progress.setRecipient(recipient);
			progressDao.save(progress);
			log.debug("Successfully saved for " + recipient.getEmailAddress());
		}
	}

	public void updateState(Recipient recipient, ProgressState state, String... details) {
		log.debug("Saving progress state of " + recipient.getEmailAddress());
		Progress progress = new Progress();
		progress.setRecipient(recipient);
		progress.setState(state);
		progress.setLastUpdateTime(new Date());
		progress.setDetails(details[0]);
		progressDao.save(progress);
		log.debug("Successfully updated to " + state.name());
	}

}
