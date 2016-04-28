package com.emailator.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.EmailBulkSenderApplication;
import com.emailator.beans.BulkEmail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
public class EmailClientTest {
	
	@Autowired
	private EmailClient emailClient;

	@Test
	public void testSend() {
		BulkEmail bulkEmail = new BulkEmail();
		emailClient.send(bulkEmail);
	}

}
