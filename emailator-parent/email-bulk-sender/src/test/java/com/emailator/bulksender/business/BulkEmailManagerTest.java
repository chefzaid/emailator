package com.emailator.bulksender.business;

import javax.mail.Message;

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
import com.emailator.bulksender.testutils.TestValues;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BulkEmailManagerTest {

	@Autowired
	private BulkEmailManager emailClient;
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

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(testValues.getEmailUuid());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
	}

	@Test
	public void testSend() {
		try {
			Message msg = emailClient.buildMessage(bulkEmail);
			Recipient recipient = new Recipient(testValues.getEmailAddress1());
			emailClient.send(msg, recipient);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error while sending the message");
		}
	}

}
