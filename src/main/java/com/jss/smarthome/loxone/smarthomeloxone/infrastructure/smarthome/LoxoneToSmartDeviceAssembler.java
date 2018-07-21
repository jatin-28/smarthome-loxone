package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LightingControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.Room;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SubControl;
import cz.smarteon.loxone.LoxoneUuid;
import cz.smarteon.loxone.config.Control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class LoxoneToSmartDeviceAssembler {
	public List<SmartHomeDevice> assembleFrom(LoxoneConfigPayload loxoneConfigPayload) {

		Map<LoxoneUuid, Room> rooms = loxoneConfigPayload.getRooms();
		Map<LoxoneUuid, Control> controls = loxoneConfigPayload.getControls();

		List<SmartHomeDevice> listOfSmartHomeDevices = new ArrayList<>();

		controls.values()
				.stream()
				.filter(control -> control instanceof LightingControl)
				.forEach(control -> listOfSmartHomeDevices.addAll(getSmartHomeDevices(rooms, (LightingControl) control)));

		return listOfSmartHomeDevices;
	}

	private List<SmartHomeDevice> getSmartHomeDevices(Map<LoxoneUuid, Room> rooms, LightingControl lightingControl) {
		String roomName = rooms.get(new LoxoneUuid(lightingControl.getRoom())).getName();
		Map<String, SubControl> subControls = lightingControl.getSubControls();
		return subControls.values()
				.stream()
				.map(subControl -> new SmartHomeDevice(
						lightingControl.getType() + "_" + subControl.getType(),
						subControl.getUuidAction(),
						subControl.getName(),
						roomName
				)).collect(Collectors.toList());
	}
}
