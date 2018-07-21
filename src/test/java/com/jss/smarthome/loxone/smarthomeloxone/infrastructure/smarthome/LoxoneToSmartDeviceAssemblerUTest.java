package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.google.common.collect.ImmutableMap;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LightingControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.Room;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.DimmerSubControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SwitchSubControl;
import cz.smarteon.loxone.LoxoneUuid;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class LoxoneToSmartDeviceAssemblerUTest {

	private static final String LED_LIGHT_NAME = "Spot Lights";
	private static final String DIMMER_STRIP_NAME = "Strips";
	private static final String ROOM_NAME = "Bedroom Master";
	private static final String UUID_ACTION_FOR_SWITCH = "<TODO>";
	private static final String UUID_ACTION_FOR_DIMMER = "<TODO>";
	private static final SmartHomeDevice SMART_HOME_SWITCH_DEVICE = new SmartHomeDevice(LightingControl.TYPE + "_" + "Switch", UUID_ACTION_FOR_SWITCH, LED_LIGHT_NAME, ROOM_NAME);
	private static final SmartHomeDevice SMART_HOME_DIMMER_DEVICE = new SmartHomeDevice(LightingControl.TYPE + "_" + "Dimmer", UUID_ACTION_FOR_DIMMER, DIMMER_STRIP_NAME, ROOM_NAME);

	private LoxoneToSmartDeviceAssembler testObj = new LoxoneToSmartDeviceAssembler();

	@Test
	public void testAssemblingLightingController() {
		List<SmartHomeDevice> smartHomeDevices = testObj.assembleFrom(createLoxonePayload());

		assertThat(smartHomeDevices).containsExactly(SMART_HOME_SWITCH_DEVICE, SMART_HOME_DIMMER_DEVICE);
	}

	private LoxoneConfigPayload createLoxonePayload() {
		LightingControl lightingControl = new LightingControl();
		String room = "0e7dde7f-0149-19d1-ffffb304361e1e65";
		lightingControl.setRoom(room);

		SwitchSubControl expectedSwitchSubControl = new SwitchSubControl();
		expectedSwitchSubControl.setName(LED_LIGHT_NAME);
		expectedSwitchSubControl.setUuidAction(UUID_ACTION_FOR_SWITCH);

		DimmerSubControl expectedDimmerSubControl = new DimmerSubControl();
		expectedDimmerSubControl.setName(DIMMER_STRIP_NAME);
		expectedDimmerSubControl.setUuidAction(UUID_ACTION_FOR_DIMMER);

		lightingControl.setSubControls(
				ImmutableMap.of(
						"<TODO>", expectedSwitchSubControl,
						"<TODO>", expectedDimmerSubControl
				));


		LoxoneConfigPayload loxoneConfigPayload = new LoxoneConfigPayload();
		loxoneConfigPayload.setControls(ImmutableMap.of(new LoxoneUuid("<TODO>"), lightingControl));
		loxoneConfigPayload.setRooms(ImmutableMap.of(new LoxoneUuid(room), new Room(ROOM_NAME)));


		return loxoneConfigPayload;

	}

}