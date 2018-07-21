package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome;

import com.jss.smarthome.loxone.smarthomeloxone.domain.Payload;
import com.jss.smarthome.loxone.smarthomeloxone.domain.SyncResponse;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.LoxoneConnection;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.LoxoneConfigPayload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmartHomeServiceUTest {

	private static final String REQUEST_ID = "456456";
	private static final String AGENT_USER_ID = "someUser";

	@Mock private LoxoneConnection loxoneConnection;
	@Mock private LoxoneConfigPayload loxoneConfigPayload;
	@Mock private LoxoneToSmartDeviceAssembler loxoneToSmartDeviceAssembler;
	@Mock private SmartHomeToJsonConverter smartHomeToJsonConverter;
	@Mock private List<SmartHomeDevice> smartHomeDevices;
	@Mock private List<String> jsonDevices;

	@InjectMocks
	private SmartHomeService testObj;

	@Before
	public void setUp() throws Exception {
		when(loxoneConnection.fetchLoxoneConfig()).thenReturn(loxoneConfigPayload);
		when (loxoneToSmartDeviceAssembler.assembleFrom(loxoneConfigPayload)).thenReturn(smartHomeDevices);
		when (smartHomeToJsonConverter.convertToJson(smartHomeDevices)).thenReturn(jsonDevices);
	}


	@Test
	public void fetchDeviceFromSmartHomeForList() throws Exception {

		SyncResponse syncResponse = testObj.fetchDevicesFromSmartHome(REQUEST_ID, AGENT_USER_ID);

		SyncResponse expected = new SyncResponse(REQUEST_ID, new Payload(AGENT_USER_ID, jsonDevices));

		assertThat(syncResponse).isEqualTo(expected);
	}



}