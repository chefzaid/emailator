package com.emailator.bulksender.service;

import java.util.List;

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
import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.testutils.TestValues;
import com.emailator.bulksender.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@SqlGroup({
		@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "purge.sql") })
public class ProgressServiceTest {

	@Autowired
	private ProgressService progressService;
	@Autowired
	private TestValues testValues;

	@Test
	public void testFindAll() {
		List<Progress> result = progressService.findAll(testValues.getEmailUuid());
		for (Progress p : result) {
			Assert.assertTrue(p.getState() == ProgressState.PENDING);
		}
	}

	@Test
	public void testFindOne() {
		Progress result = progressService.findOne(testValues.getEmailUuid(), testValues.getEmailAddress1());
		Assert.assertTrue(result.getState() == ProgressState.PENDING);
	}

}
