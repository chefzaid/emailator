package com.emailator.bulksender.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.beans.BulkEmail;
import com.emailator.bulksender.service.BulkEmailService;

import lombok.extern.apachecommons.CommonsLog;

@Component
@Path("/sender")
@CommonsLog
public class BulkEmailRest {

	@Autowired
	private BulkEmailService bulkEmailService;

	@POST
	@Path("/normal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendEmailNormal(BulkEmail bulkEmail) {
		log.debug("Sending bulk email...");
		HttpStatus status = HttpStatus.OK;
		try {
			bulkEmailService.send(bulkEmail);
		} catch (Exception e) {
			log.error("Error while calling service", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return Response.status(status.value()).entity(status.getReasonPhrase()).build();
	}

	@POST
	@Path("/async")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendEmailAsync(BulkEmail bulkEmail) {
		String msg = "Service not yet implemented";
		log.error(msg);
		return Response.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).entity(msg).build();
	}

}
