package com.emailator.bulksender.unit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.service.BulkEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BulkEmailServiceTest {
	
	@Autowired
	private BulkEmailService bulkEmailService;
	
	private BulkEmail bulkEmail;

	@Before
	public void setUp() {
		Email email = new Email();
		email.setSubject("Test");
		email.setBody("Hello World!");
		email.setSender("emailator.test0@mailinator.com");

		List<Recipient> recipients = new ArrayList<>();
		recipients.add(new Recipient("emailator.test1@mailinator.com"));
		recipients.add(new Recipient("emailator.test2@mailinator.com"));
		recipients.add(new Recipient("emailator.test3@mailinator.com"));

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setRecipients(recipients);
	}
	
	@Test
	public void testSend(){
		// TODO: Mock BulkEmailClient, fail on exception
		bulkEmailService.send(bulkEmail);
	}

}
