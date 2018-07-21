package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone;

import com.google.common.collect.ImmutableMap;
import com.jss.smarthome.loxone.smarthomeloxone.config.properties.LoxoneProperties;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LightingControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.Room;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SwitchSubControl;
import cz.smarteon.loxone.LoxoneUuid;
import cz.smarteon.loxone.LoxoneUuids;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class LoxoneConnectionITest {

	private LoxoneConnection testObj;

	@Before
	public void setupSpec() {
		LoxoneProperties loxoneProperties = new LoxoneProperties();
		loxoneProperties.setAddress("<IPADDRESS>");
		loxoneProperties.setUser("<USERNAME>");
		loxoneProperties.setPassword("<PASSWORD>");
		loxoneProperties.setUipassword("<UIPASSWORD>");

		testObj = new LoxoneConnection(loxoneProperties);
	}

	@After
	public void cleanup() {
		testObj.shutDown();
	}

	@Test
	@Ignore
	public void turnOnMasterBedroomLights() throws Exception {
//		LoxoneConfigPayload loxoneConfigPayload = testObj.fetchLoxoneConfig();
//		Map<LoxoneUuid, Room> rooms = loxoneConfigPayload.getRooms();
//
//		for (Map.Entry<LoxoneUuid, Room> loxoneUuidRoomEntry : rooms.entrySet()) {
//			if( loxoneUuidRoomEntry.getValue().equals(new Room("Bathroom Loft"))) {
//				LoxoneUuid bedroomMasterUuid = loxoneUuidRoomEntry.getKey();
//
//				Collection<Control> values = loxoneConfigPayload.getControls().values();
//				for (Control control : values) {
//					if(control instanceof LightingControl) {
//						LightingControl lightingControl = (LightingControl) control;
//						if( lightingControl.getRoom().equals(bedroomMasterUuid.toString())) {
//							Collection<SubControl> subControls = lightingControl.getSubControls().values();
//							for (SubControl subControl : subControls) {
//								if( subControl.getName().equals("Light")) {
//									testObj.sendCommand
//									loxoneWebSocket.sendCommand(Protocol.jsonControlOn(subControl.getUuidAction()) );
//								}
//							}
//						}
//					}
//				}
//			}
//		}

	}

	@Test
	public void fetchLoxoneConfigData() {
		LoxoneConfigPayload loxoneConfigPayload = testObj.fetchLoxoneConfig();

		assertThat(loxoneConfigPayload.getRooms().values()).contains(new Room("Bedroom Master"));

		LightingControl expected = new LightingControl();
		String room = "<TODO>";
		expected.setRoom(room);

		SwitchSubControl expectedSubControl = new SwitchSubControl();
		expectedSubControl.setName("Light");
		expectedSubControl.setUuidAction("<TODO>");
		LoxoneUuids loxoneUuidsStates = new LoxoneUuids();
		loxoneUuidsStates.add(new LoxoneUuid("<TODO>"));
		expectedSubControl.setStates(ImmutableMap.of("active", loxoneUuidsStates));
		expected.setSubControls(ImmutableMap.of("<TODO>", expectedSubControl));


		LightingControl actual = ( LightingControl) loxoneConfigPayload.getControls().values()
				.stream()
				.filter(control -> control instanceof LightingControl)
				.filter(control -> ((LightingControl) control).getRoom().equals(room))
				.findFirst()
				.get();

		assertThat(actual.getSubControls().values().iterator().next().getUuidAction()).isEqualTo("<TODO>");
	}
}