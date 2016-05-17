package com.emailator.bulksender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.Recipient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
public class BulkEmailSenderServiceTest {
	
	@Autowired
	private BulkEmailSenderService bulkEmailService;
	
	private BulkEmail bulkEmail;

	@Before
	public void setUp() {
		Email email = new Email();
		email.setSubject("Test");
		email.setBody("Hello World!");
		email.setSender("sender@test.com");

		List<Recipient> recipients = new ArrayList<>();
		recipients.add(new Recipient("chefzaid@gmail.com"));
		recipients.add(new Recipient("zaid-9@hotmail.fr"));
		recipients.add(new Recipient("c.zaid@live.fr"));

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setRecipients(recipients);
	}
	
	@Test
	public void testSend(){
		// TODO: Mock BulkEmailClient and assert on DB entries
		bulkEmailService.send(bulkEmail);
	}

}
