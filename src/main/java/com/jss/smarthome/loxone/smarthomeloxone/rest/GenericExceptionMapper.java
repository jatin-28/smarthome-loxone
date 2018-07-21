package com.jss.smarthome.loxone.smarthomeloxone.rest;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 */
@Slf4j
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(Throwable throwable) {
		log.error("Error occurred:", throwable);
		return Response.serverError().entity("Something terrible and unexpected happened!").build();
	}
}
