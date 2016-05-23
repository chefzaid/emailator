package com.emailator.bulksender.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.dao.BulkEmailDao;
import com.emailator.bulksender.dao.ProgressDao;
import com.emailator.bulksender.dao.RecipientDao;

import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class ProgressManager {

	@Autowired
	private ProgressDao progressDao;
	@Autowired
	private RecipientDao recipientDao;
	@Autowired
	private BulkEmailDao bulkEmailDao;

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

	public List<Progress> findAll(String bulkEmailUuid) {
		log.debug("Finding Progress for each email address in " + bulkEmailUuid);
		List<Progress> result = new ArrayList<>();
		BulkEmail bulkEmail = bulkEmailDao.findByUuid(bulkEmailUuid);
		for (Recipient recipient : bulkEmail.getRecipients()) {
			result.add(recipient.getProgress());
		}
		log.debug("Returning result, size=" + result.size());
		return result;
	}

	public Progress findOne(String bulkEmailUuid, String emailAddress) {
		log.debug("Finding Progress for email address " + emailAddress + " of " + bulkEmailUuid);
		Recipient recipient = recipientDao.findByUuidAndEmailAddress(bulkEmailUuid, emailAddress);
		log.debug("Returning result");
		return recipient.getProgress();
	}
	
	public void purgeData(String bulkEmailUuid){
		log.debug("Purging data for " + bulkEmailUuid);
		BulkEmail bulkEmail = bulkEmailDao.findByUuid(bulkEmailUuid);
		for (Recipient recipient : bulkEmail.getRecipients()) {
			progressDao.delete(recipient.getProgress());
		}
		log.debug("Data purged");
	}

}
