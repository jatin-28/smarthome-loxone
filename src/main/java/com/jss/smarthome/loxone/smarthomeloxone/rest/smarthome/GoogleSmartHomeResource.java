package com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.dto.SmartHomeRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/smarthome")
public interface GoogleSmartHomeResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response smartHome(SmartHomeRequest smartHomeRequest);
}
