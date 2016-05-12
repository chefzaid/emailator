package com.emailator.bulksender.business;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.business.ConfigurationLoader;
import com.emailator.bulksender.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
public class ConfigurationLoaderTest {
	
	@Autowired
	private ConfigurationLoader configurationLoader;

	@Test
	public void testGetProperty() {
		String host = configurationLoader.getProperty(Constants.KEY_SMTP_HOST);
		Assert.assertNotNull(host);
		Assert.assertTrue(host.length() > 3);
	}

}
