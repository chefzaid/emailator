package com.emailator.bulksender.business;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
		smtpConf.setPort(587);
		smtpConf.setUsername("emailator.test@gmail.com");
		smtpConf.setPassword("emailator.test1990");
		
		Email email = new Email();
		email.setSubject("Test");
		email.setBody("Hello World!");
		email.setSender("emailator.test@gmail.com");
		
		List<String> recipients = new ArrayList<>();
		recipients.add("chefzaid@gmail.com");
		recipients.add("zaid-9@hotmail.fr");
		recipients.add("c.zaid@live.fr");
		
		BulkEmail bulkEmail = new BulkEmail();
		bulkEmail.setBulkEmailId(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
		bulkEmail.setRecipients(recipients);
		
		try {
			emailClient.send(bulkEmail);
		} catch (Exception e){
			Assert.fail("Exception thrown while sending");
		}
	}
	
	@Test
	public void testSendViaGmailError() {
		
	}

}
