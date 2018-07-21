package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.TemplateEngine;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class SmartHomeToJsonConverter {
	private TemplateEngine templateEngine;

	public SmartHomeToJsonConverter(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public List<String> convertToJson(List<SmartHomeDevice> listOfSmartDevices) {
		return listOfSmartDevices.stream()
				.map(
						smartHomeDevice -> templateEngine.render(smartHomeDevice.getTemplateName(), smartHomeDevice)
				).collect(Collectors.toList());
	}
}
