package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.domain.Payload;
import com.jss.smarthome.loxone.smarthomeloxone.domain.SyncResponse;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.LoxoneConnection;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;

import java.util.List;

/**
 *
 */
public class SmartHomeService {

	private final LoxoneConnection loxoneConnection;
	private final LoxoneToSmartDeviceAssembler loxoneToSmartDeviceAssembler;
	private final SmartHomeToJsonConverter smartHomeToJsonConverter;

	public SmartHomeService(LoxoneConnection loxoneConnection,
							LoxoneToSmartDeviceAssembler loxoneToSmartDeviceAssembler,
							SmartHomeToJsonConverter smartHomeToJsonConverter) {
		this.loxoneConnection = loxoneConnection;
		this.loxoneToSmartDeviceAssembler = loxoneToSmartDeviceAssembler;
		this.smartHomeToJsonConverter = smartHomeToJsonConverter;
	}

	public SyncResponse fetchDevicesFromSmartHome(String requestId, String agentUserId) {
		LoxoneConfigPayload loxoneConfigPayload = loxoneConnection.fetchLoxoneConfig();
		List<SmartHomeDevice> smartHomeDevices = loxoneToSmartDeviceAssembler.assembleFrom(loxoneConfigPayload);
		List<String> jsonDevices = smartHomeToJsonConverter.convertToJson(smartHomeDevices);

		return new SyncResponse(requestId, new Payload(agentUserId, jsonDevices));
	}

}
