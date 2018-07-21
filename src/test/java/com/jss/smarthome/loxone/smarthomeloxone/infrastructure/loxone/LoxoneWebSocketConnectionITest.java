package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone;

import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LightingControl;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.Room;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols.SubControl;
import cz.smarteon.loxone.*;
import cz.smarteon.loxone.config.Control;
import cz.smarteon.loxone.message.LoxoneValue;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.Security;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static java.lang.System.getenv;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@Ignore
public class LoxoneWebSocketConnectionITest {

	public static final String LOX_ADDRESS = "LOX_ADDRESS";
	public static final String LOX_USER = "LOX_USER";
	public static final String LOX_PASS = "LOX_PASS";
	public static final String LOX_VISPASS = "LOX_VISPASS";

	LoxoneWebSocket loxoneWebSocket;
	LoxoneHttp loxoneHttp;
	CommandMemory commands;

	@Before
	public void setupSpec() {
		Security.addProvider(new BouncyCastleProvider());

		String address = getenv(LOX_ADDRESS);
		final LoxoneAuth loxoneAuth = new LoxoneAuth(new LoxoneHttp(address), getenv(LOX_USER), getenv(LOX_PASS), getenv(LOX_VISPASS));

		loxoneWebSocket = new LoxoneWebSocket(address + ":7070", loxoneAuth);

		commands = new CommandMemory();
		loxoneWebSocket.registerListener(commands);

	}

	@After
	public void cleanup() {
		loxoneWebSocket.close();
	}

	@Test
	public void turnOnMasterBedroomLights() throws Exception {
		LoxoneConfigPayload loxoneConfigPayload = loxoneHttp.get(Protocol.C_APP, loxoneWebSocket.getLoxoneAuth(), LoxoneConfigPayload.class);
		Map<LoxoneUuid, Room> rooms = loxoneConfigPayload.getRooms();

		for (Map.Entry<LoxoneUuid, Room> loxoneUuidRoomEntry : rooms.entrySet()) {
			if( loxoneUuidRoomEntry.getValue().equals(new Room("Bathroom Loft"))) {
				LoxoneUuid bedroomMasterUuid = loxoneUuidRoomEntry.getKey();

				Collection<Control> values = loxoneConfigPayload.getControls().values();
				for (Control control : values) {
					if(control instanceof LightingControl) {
						LightingControl lightingControl = (LightingControl) control;
						if( lightingControl.getRoom().equals(bedroomMasterUuid.toString())) {
							Collection<SubControl> subControls = lightingControl.getSubControls().values();
							for (SubControl subControl : subControls) {
								if( subControl.getName().equals("Light")) {
									loxoneWebSocket.sendCommand(Protocol.jsonControlOn(subControl.getUuidAction()) );
								}
							}
						}
					}
				}
			}
		}

	}

	@Test
	public void testConnectionDirect() {
		String controlId = "<TODO>";

		final CountDownLatch latch = new CountDownLatch(1);

		// set listener waiting for our command
		loxoneWebSocket.registerListener((command, value) -> {
			System.out.println("Got answer on command=" + command + " value=" + value);
			if (command.contains(controlId)) {
				latch.countDown();
			}
			return CommandListener.State.CONSUMED;
		});

		// send the command
		loxoneWebSocket.sendCommand(Protocol.jsonControlOff(controlId));
		//loxoneWebSocket.sendCommand(Protocol.jsonControlOff(controlId));
		//loxoneWebSocket.sendSecureCommand(Protocol.jsonControlOn(controlId));

		try {
			latch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace(); //ugly :(
		}

		loxoneWebSocket.close();
	}

	@Test
	public void fetchLoxoneConfigData() {
		LoxoneConfigPayload loxoneConfigPayload = loxoneHttp.get(Protocol.C_APP, loxoneWebSocket.getLoxoneAuth(), LoxoneConfigPayload.class);

		assertThat(loxoneConfigPayload.getRooms().values()).contains(new Room("Bedroom Master"));
		assertThat(loxoneConfigPayload.getControls().values()).contains(new LightingControl());
	}

	private static class CommandMemory implements CommandListener {

		LinkedHashMap<String, LoxoneValue> commands = new LinkedHashMap<>();
		String pattern;
		CountDownLatch latch;

		@Override
		public State onCommand(String command, LoxoneValue value) {
			if (latch != null && pattern != null)  {
				if (Objects.equals(command, pattern)) {
					latch.countDown();
				}
			}
			commands.put(command, value);
			return State.CONSUMED;
		}

		CountDownLatch expectCommand(String pattern) {
			this.pattern = pattern;
			this.latch = new CountDownLatch(1);
			return this.latch;
		}
	}
}
