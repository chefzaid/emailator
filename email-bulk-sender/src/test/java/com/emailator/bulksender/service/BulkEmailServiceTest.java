package com.emailator.bulksender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
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
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.dao.BulkEmailDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BulkEmailServiceTest {

	@Autowired
	private BulkEmailService bulkEmailService;
	@Autowired
	private BulkEmailDao bulkEmailDao;

	private BulkEmail bulkEmail;

	@Before
	public void setUp() {
		SmtpConfiguration smtpConf = new SmtpConfiguration();
		smtpConf.setEnableAuthentication(true);
		smtpConf.setEnableStartTls(true);
		smtpConf.setHost("smtp.gmail.com");
		smtpConf.setPort(587);
		smtpConf.setUsername("emailator.test");
		smtpConf.setPassword("emailator.test1990");

		Email email = new Email();
		email.setSubject("Test");
		email.setBody("Hello World!");
		email.setSender("emailator.test@gmail.com");

		List<Recipient> recipients = new ArrayList<>();
		recipients.add(new Recipient("emailator.test1@mailinator.com"));
		recipients.add(new Recipient("emailator.test2@mailinator.com"));
		recipients.add(new Recipient("emailator.test3@mailinator.com"));

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
		bulkEmail.setRecipients(recipients);
	}

	@Test
	public void testSend() {
		try {
			bulkEmailService.send(bulkEmail);
			Long result = bulkEmailDao.count();
			Assert.assertTrue(result == 1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error while sending the message");
		}
	}

}
