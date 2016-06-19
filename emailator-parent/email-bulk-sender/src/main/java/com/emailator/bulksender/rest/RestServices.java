package com.emailator.bulksender.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/api/bulkEmail")
public class RestServices extends ResourceConfig {

	public RestServices() {
		register(BulkEmailRest.class);
		register(ProgressRest.class);
	}
}
