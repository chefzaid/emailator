package com.emailator.bulksender.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.service.BulkEmailService;

@Path("")
public class BulkEmailRest {

	@Autowired
	private BulkEmailService bulkEmailService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void sendEmail(BulkEmail bulkEmail) {
		// TODO: REST conf + logs + exception management
		bulkEmailService.send(bulkEmail);
	}

}
