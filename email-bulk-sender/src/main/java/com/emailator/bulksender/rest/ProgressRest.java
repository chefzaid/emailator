package com.emailator.bulksender.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.emailator.bulksender.beans.Progress;
import com.emailator.bulksender.service.ProgressService;

import lombok.extern.apachecommons.CommonsLog;

@Component
@Path("/progress")
@CommonsLog
public class ProgressRest {

	@Autowired
	private ProgressService progressManagerService;

	@GET
	@Path("/all/{bulkEmailUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll(@PathParam("bulkEmailUuid") String bulkEmailUuid) {
		log.debug("Calling service findAll....");
		HttpStatus status = HttpStatus.OK;
		List<Progress> result = null;
		try {
			result = progressManagerService.findAll(bulkEmailUuid);
		} catch (Exception e) {
			log.error("Error while calling service", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return Response.status(status.value()).entity(result).build();
	}

	@GET
	@Path("/one/{bulkEmailUuid}/{emailAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findOne(@PathParam("bulkEmailUuid") String bulkEmailUuid,
			@PathParam("emailAddress") String emailAddress) {
		log.debug("Calling service findOne...");
		HttpStatus status = HttpStatus.OK;
		Progress result = null;
		try {
			result = progressManagerService.findOne(bulkEmailUuid, emailAddress);
		} catch (Exception e) {
			log.error("Error while calling service", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return Response.status(status.value()).entity(result).build();
	}

}
