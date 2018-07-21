package com.jss.smarthome.loxone.smarthomeloxone.infrastructure;

import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.SmartHomeDevice;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateEngineUTest {
	private static final String TEMPLATE_NAME = "LightControllerV2_Switch";

	private static final String EXPECTED = "{\n" +
			"\"id\": \"xxxx/someaction\",\n" +
			"  \"type\": \"action.devices.types.LIGHT\",\n" +
			"  \"traits\": [\n" +
			"    \"action.devices.traits.OnOff\"\n" +
			"  ],\n" +
			"  \"name\": {\n" +
			"    \"name\": \"LIGHT_NAME\"\n" +
			"  },\n" +
			"  \"roomHint\": \"ROOM_NAME\",\n" +
			"  \"willReportState\": false,\n" +
			"  \"customData\": {\n" +
			"    \"action\": \"xxxx/someaction\"\n" +
			"  }\n" +
			"}";


	private TemplateEngine testObj = new TemplateEngine();

	@Test
	public void renderTemplate() {
		String rendered = testObj.render(TEMPLATE_NAME, new SmartHomeDevice(TEMPLATE_NAME, "xxxx/someaction", "LIGHT_NAME", "ROOM_NAME"));

		assertThat(rendered).isEqualToNormalizingWhitespace(EXPECTED);
	}

	@Test
	public void readTemplateFileName() {
		String deviceTemplate = testObj.loadTemplate(TEMPLATE_NAME);

		assertThat(deviceTemplate).contains("\"roomHint\": \"{{device.roomName}}\"");
	}
}