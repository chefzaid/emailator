package com.emailator.bulksender.rest;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emailator.bulksender.EmailBulkSenderApplication;
import com.emailator.bulksender.beans.ProgressState;
import com.emailator.bulksender.testutils.TestValues;
import com.emailator.bulksender.utils.Constants;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EmailBulkSenderApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@SqlGroup({
		@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "seed.sql"),
		@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = Constants.TEST_DB_SCRIPTS_PATH + "purge.sql") })
@IntegrationTest("server.port:0")
public class ProgressRestIntegrationTest {
	
	@Autowired
	private TestValues testValues;

	// Path
	@Value("${local.server.port}")
	private Integer serverPort;
	private static final String BASE_PATH = "/api/bulkEmail/progress";
	private static final String FIND_ALL_PATH = "/all/{uuid}";
	private static final String FIND_ONE_PATH = "/one/{uuid}/{emailAddress}";
	// Fields
	private static final String STATE_FIELD = "state";

	@Before
	public void setUp() {
		RestAssured.basePath = BASE_PATH;
		RestAssured.port = serverPort;
	}

	@Test
	public void testFindAll() {
		RestAssured
			.when().get(FIND_ALL_PATH, testValues.getEmailUuid())
			.then().statusCode(HttpStatus.SC_OK).body(STATE_FIELD, 
					Matchers.hasItems(
							ProgressState.PENDING.name(), 
							ProgressState.PENDING.name(), 
							ProgressState.PENDING.name()));
	}

	@Test
	public void testFindOne() {
		RestAssured
			.when().get(FIND_ONE_PATH, testValues.getEmailUuid(), testValues.getEmailAddress1())
			.then().statusCode(HttpStatus.SC_OK).body(STATE_FIELD, 
					Matchers.equalTo(ProgressState.PENDING.name()));
	}

}
