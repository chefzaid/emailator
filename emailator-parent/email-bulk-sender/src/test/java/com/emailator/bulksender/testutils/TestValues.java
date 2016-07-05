package com.emailator.bulksender.testutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class TestValues {

	@Value("${test.smtp.host}")
	private String smtpHost;
	@Value("${test.smtp.port}")
	private Integer smtpPort;
	@Value("${test.smtp.username}")
	private String smtpUsername;
	@Value("${test.smtp.password}")
	private String smtpPassword;
	@Value("${test.smtp.enableAuthentication}")
	private Boolean smtpEnableAuthentication;
	@Value("${test.smtp.enableStartTls}")
	private Boolean smtpEnableStartTls;

	@Value("${test.email.address1}")
	private String emailAddress1;
	@Value("${test.email.address2}")
	private String emailAddress2;
	@Value("${test.email.address3}")
	private String emailAddress3;
	@Value("${test.email.sender}")
	private String emailSender;
	@Value("${test.email.subject}")
	private String emailSubject;
	@Value("${test.email.body}")
	private String emailBody;
	@Value("${test.email.uuid}")
	private String emailUuid;
	@Value("${test.email.uuid.regex}")
	private String emailUuidRegex;

}
