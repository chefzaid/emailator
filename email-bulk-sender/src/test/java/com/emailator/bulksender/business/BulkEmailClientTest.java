package com.emailator.bulksender.business;

import java.util.UUID;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;

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
import com.emailator.bulksender.beans.SmtpConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class BulkEmailClientTest {

	@Autowired
	private BulkEmailClient emailClient;
	
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

		bulkEmail = new BulkEmail();
		bulkEmail.setUuid(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
	}

	@Test
	public void testSend() {
		try {
			Message msg = emailClient.buildMessage(bulkEmail);
			Address address = new InternetAddress("emailator.test1@mailinator.com");
			msg.setRecipient(Message.RecipientType.TO, address);
			emailClient.asyncSend(msg);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Error while sending the message");
		}
	}

}
