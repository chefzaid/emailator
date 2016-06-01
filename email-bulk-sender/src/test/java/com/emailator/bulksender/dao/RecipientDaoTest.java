package com.emailator.bulksender.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.Recipient;
import com.emailator.bulksender.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@SqlGroup({
		@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "purge.sql") })
public class RecipientDaoTest {

	@Autowired
	private RecipientDao recipientDao;

	@Test
	public void testFindByUuidAndEmailAddress() {
		Recipient result = null;
		// Success
		result = recipientDao.findByUuidAndEmailAddress("azerty1234", "emailator.test1@mailinator.com");
		Assert.assertTrue(result.getId() == 1);
		// Failure
		result = recipientDao.findByUuidAndEmailAddress("nothing", "inexisting@mailinator.com");
		Assert.assertNull(result);
	}

}
