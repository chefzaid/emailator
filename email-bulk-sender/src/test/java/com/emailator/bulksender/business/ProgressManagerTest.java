package com.emailator.bulksender.business;

import org.junit.After;
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
import com.emailator.bulksender.business.ProgressManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ProgressManagerTest {

	@Autowired
	private ProgressManager progressManager;

	@Before
	public void setUp() {
		// TODO: Create objects to persist
	}

	@After
	public void tearDown() {
		// TODO: Purge database
	}

	@Test
	public void testInitStates() {
		progressManager.initStates(new BulkEmail());
	}

	@Test
	public void testUpdateState() {

	}

	@Test
	public void testFindAll() {

	}

	@Test
	public void testFinOne() {

	}
	
	@Test
	public void testPurgeData() {

	}
}
