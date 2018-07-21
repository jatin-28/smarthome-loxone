package com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.domain.Intent;
import com.jss.smarthome.loxone.smarthomeloxone.domain.SyncResponse;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.SmartHomeService;
import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.dto.SmartHomeRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.ok;

/**
 *
 */
@Component
public class GoogleSmartHomeResourceImpl implements GoogleSmartHomeResource {

	private final SmartHomeService smartHomeService;

	@Inject
	public GoogleSmartHomeResourceImpl(SmartHomeService smartHomeService) {
		this.smartHomeService = smartHomeService;
	}

	@Override
	public Response smartHome(SmartHomeRequest smartHomeRequest) {
		Intent intent = Intent.lookUpLabel(smartHomeRequest.getInputs().get(0).getIntent());
		SyncResponse syncResponse = null;

		switch (intent) {
			case SYNC:
				syncResponse = smartHomeService.fetchDevicesFromSmartHome(smartHomeRequest.getRequestId(), "");
				break;
			case EXEC:
				break;
			case QUERY:
				break;
		}

		return ok( syncResponse ).build();
	}
}
