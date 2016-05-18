package com.emailator.bulksender.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.emailator.bulksender.service.ProgressService;

import lombok.extern.apachecommons.CommonsLog;

@Path("")
@CommonsLog
public class ProgressRest {

	@Autowired
	private ProgressService progressManagerService;

	@GET
	@Path("all/{bulkEmailUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@PathParam("bulkEmailUuid") String bulkEmailUuid) {
		log.debug("Getting all....");
		progressManagerService.findAll(bulkEmailUuid);
		return null;
	}

	@GET
	@Path("one/{bulkEmailUuid}/{emailAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findOne(@PathParam("bulkEmailUuid") String bulkEmailUuid,
			@PathParam("emailAddress") String emailAddress) {
		log.debug("Getting one....");
		progressManagerService.findOne(bulkEmailUuid, emailAddress);
		return null;
	}

}
