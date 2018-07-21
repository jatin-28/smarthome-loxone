package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.TemplateEngine;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class SmartHomeToJsonConverterUTest {

	private static final String LED_LIGHT_NAME = "Spot Lights";
	private static final String ROOM_NAME = "Bedroom Master";

	private static final String SWITCH_ACTION = "<TODO>";
	private static final String DEVICE_1 = "{\n" +
			"        \"id\": \"" + SWITCH_ACTION + "\",\n" +
			"        \"type\": \"action.devices.types.LIGHT\",\n" +
			"        \"traits\": [\n" +
			"          \"action.devices.traits.OnOff\"\n" +
			"        ],\n" +
			"        \"name\": {\n" +
			"          \"name\": \"" + LED_LIGHT_NAME + "\"\n" +
			"        },\n" +
			"        \"roomHint\": \"" + ROOM_NAME + "\",\n" +
			"        \"willReportState\": false,\n" +
			"        \"customData\": {\n" +
			"          \"action\": \"" + SWITCH_ACTION + "\"\n" +
			"        }\n" +
			"      }";

	private static final String DIMMER_ACTION = "<TODO>";
	private static final String DIMMER_NAME = "Strips";
	private static final String DEVICE_2 = "{\n" +
			"        \"id\": \"" + DIMMER_ACTION + "\",\n" +
			"        \"type\": \"action.devices.types.LIGHT\",\n" +
			"        \"traits\": [\"action.devices.traits.OnOff\", \"action.devices.traits.Brightness\"],\n" +
			"        \"name\": {\n" +
			"          \"name\": \"" + DIMMER_NAME + "\"\n" +
			"        },\n" +
			"        \"roomHint\": \"" + ROOM_NAME + "\",\n" +
			"        \"willReportState\": false,\n" +
			"        \"customData\": {\n" +
			"          \"action\": \"" + DIMMER_ACTION + "\"\n" +
			"        }\n" +
			"      }";

	private SmartHomeToJsonConverter testObj = new SmartHomeToJsonConverter(new TemplateEngine());
	private List<SmartHomeDevice> listOfSmartDevices = Arrays.asList(
			new SmartHomeDevice("LightControllerV2_Dimmer", DIMMER_ACTION, DIMMER_NAME, ROOM_NAME),
			new SmartHomeDevice("LightControllerV2_Switch", SWITCH_ACTION, LED_LIGHT_NAME, ROOM_NAME)
			);

	@Test
	public void testConvertingDevicesToTemplate() {
		List<String> devices = testObj.convertToJson(listOfSmartDevices);

		assertThat(devices.get(0)).isEqualToIgnoringWhitespace(DEVICE_2);
		assertThat(devices.get(1)).isEqualToIgnoringWhitespace(DEVICE_1);
	}
}