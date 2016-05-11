package com.emailator.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.EmailBulkSenderApplication;
import com.emailator.beans.BulkEmail;
import com.emailator.beans.Email;
import com.emailator.beans.SmtpConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
public class EmailClientTest {
	
	@Autowired
	private EmailClient emailClient;

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
