package com.emailator.bulksender.business;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.beans.Recipient;

import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class AsyncEmailSender {
	
	@Autowired
	private ProgressManager progressManager;

	@Async
	public void send(Message msg, Recipient recipient) {
		log.debug("Async sending message to " + recipient.getEmailAddress());
		progressManager.updateState(recipient, ProgressState.SENDING);
		try {
			Transport.send(msg);
			progressManager.updateState(recipient, ProgressState.SENT);
			log.debug("Message sent to " + recipient.getEmailAddress());
		} catch (MessagingException e) {
			log.debug("Failed sending message to " + recipient.getEmailAddress());
			progressManager.updateState(recipient, ProgressState.FAILED);
		}
	}

}
