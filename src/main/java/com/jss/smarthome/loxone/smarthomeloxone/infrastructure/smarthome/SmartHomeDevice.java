package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import lombok.Data;

/**
 *
 */
@Data
public class SmartHomeDevice {
	private final String templateName;
	private final String uuidAction;
	private final String lightName;
	private final String roomName;
}
