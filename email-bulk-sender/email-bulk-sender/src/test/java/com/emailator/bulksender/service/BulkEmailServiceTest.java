package com.emailator.bulksender.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.dao.BulkEmailDao;
import com.emailator.bulksender.testutils.TestValues;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@EnableAsync
@Configuration
public class BulkEmailServiceTest {

	@Autowired
	private BulkEmailService bulkEmailService;
	@Autowired
	private BulkEmailDao bulkEmailDao;
	@Autowired
	private TestValues testValues;

	private BulkEmail bulkEmail;

	@Before
	public void setUp() {
		SmtpConfiguration smtpConf = new SmtpConfiguration();
		smtpConf.setEnableAuthentication(testValues.getSmtpEnableAuthentication());
		smtpConf.setEnableStartTls(testValues.getSmtpEnableStartTls());
		smtpConf.setHost(testValues.getSmtpHost());
		smtpConf.setPort(testValues.getSmtpPort());
		smtpConf.setUsername(testValues.getSmtpUsername());
		smtpConf.setPassword(testValues.getSmtpPassword());

		Email email = new Email();
		email.setSubject(testValues.getEmailSubject());
		email.setBody(testValues.getEmailBody());
		email.setSender(testValues.getEmailSender());

		List<Recipient> recipients = new ArrayList<>();
		recipients.add(new Recipient(testValues.getEmailAddress1()));
		recipients.add(new Recipient(testValues.getEmailAddress2()));
		recipients.add(new Recipient(testValues.getEmailAddress3()));

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(testValues.getEmailUuid());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
		bulkEmail.setRecipients(recipients);
	}
	
	@After
	public void tearDown() {
		bulkEmailDao.deleteAll();
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
