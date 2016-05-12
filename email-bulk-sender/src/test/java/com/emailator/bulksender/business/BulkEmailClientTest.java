package com.emailator.bulksender.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.business.BulkEmailClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
public class BulkEmailClientTest {
	
	@Autowired
	private BulkEmailClient emailClient;

	@Test
	public void testSendViaGmailSuccess() {
		
		SmtpConfiguration smtpConf = new SmtpConfiguration();
		smtpConf.setEnableAuthentication(true);
		smtpConf.setEnableStartTls(true);
		smtpConf.setHost("smtp.gmail.com");
		smtpConf.setUsername("");
		smtpConf.setPassword("");
		
		Email email = new Email();
		email.setSubject("Test");
		email.setBody("Hello World!");
		email.setSender("");
		
		List<String> recipients = new ArrayList<>();
		recipients.add("");
		recipients.add("");
		recipients.add("");
		
		BulkEmail bulkEmail = new BulkEmail();
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
		bulkEmail.setRecipients(recipients);
		
		try {
			emailClient.send(bulkEmail);
		} catch (Exception e){
			Assert.fail("Exception thrown during the sending");
		}
	}
	
	@Test
	public void testSendViaGmailError() {
		
	}

}
