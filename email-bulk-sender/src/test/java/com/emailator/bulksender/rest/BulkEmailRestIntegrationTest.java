package com.emailator.bulksender.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.beans.Email;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.beans.SmtpConfiguration;
import com.emailator.bulksender.testutils.RegexMatcher;
import com.emailator.bulksender.testutils.TestValues;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@IntegrationTest("server.port:0")
public class BulkEmailRestIntegrationTest {
	
	@Autowired
	private TestValues testValues;

	// Path
	@Value("${local.server.port}")
	private Integer serverPort;
	private static final String BASE_PATH = "/api/bulkEmail/sender";
	private static final String NORMAL_PATH = "/normal";
	private static final String ASYNCE_PATH = "/async";

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
		bulkEmail.setUuid(UUID.randomUUID().toString());
		bulkEmail.setEmail(email);
		bulkEmail.setSmtpConfiguration(smtpConf);
		bulkEmail.setRecipients(recipients);

		RestAssured.basePath = BASE_PATH;
		RestAssured.port = serverPort;
	}

	@Test
	public void testSendEmailNormal() {
		RestAssured
			.given().contentType(MediaType.APPLICATION_JSON).body(bulkEmail)
			.when().post(NORMAL_PATH)
			.then().statusCode(HttpStatus.SC_OK)
				.assertThat().body(RegexMatcher.matches(testValues.getEmailUuidRegex()));
	}

	@Test
	public void testSendEmailAsync() {
		RestAssured
			.given().contentType(MediaType.APPLICATION_JSON).body(bulkEmail)
			.when().post(ASYNCE_PATH)
			.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
				.assertThat().body(Matchers.equalTo("Service not yet implemented"));
	}

}
