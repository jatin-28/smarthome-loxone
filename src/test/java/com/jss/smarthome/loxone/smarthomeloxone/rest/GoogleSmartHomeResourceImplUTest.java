package com.jss.smarthome.loxone.smarthomeloxone.rest;

import com.jss.smarthome.loxone.smarthomeloxone.domain.Payload;
import com.jss.smarthome.loxone.smarthomeloxone.domain.SyncResponse;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.SmartHomeService;
import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.GoogleSmartHomeResourceImpl;
import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Arrays;
import java.util.Map;

import static javax.ws.rs.client.Entity.json;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class GoogleSmartHomeResourceImplUTest extends JerseyTest {

	private static final String SYNC_COMMAND_FIXTURE = "fixtures/smarthome/sync_request.json";
	private static final String SYNC_RESPONSE_FIXTURE = "fixtures/smarthome/sync_response_expected.json";

	private static final String EXEC_COMMAND_FIXTURE = "fixtures/smarthome/exec_request.json";
	private static final String QUERY_COMMAND_FIXTURE = "fixtures/smarthome/query_request.json";

	private static final String SMARTHOME_PATH = "/smarthome";
	private static final String REQUEST_ID = "ff36a3cc-ec34-11e6-b1a0-64510650abcf";
	private static final String AGENT_USER_ID = "";

	private static final String DEVICE_ID = "108be7bf-0267-4418-ffffb304361e1e65/AI1";
	private static final String DEVICE_1 = "{\n" +
			"        \"id\": \"" + DEVICE_ID + "\",\n" +
			"        \"type\": \"action.devices.types.LIGHT\",\n" +
			"        \"traits\": [\n" +
			"          \"action.devices.traits.OnOff\"\n" +
			"        ],\n" +
			"        \"name\": {\n" +
			"          \"name\": \"Light\"\n" +
			"        },\n" +
			"        \"roomHint\": \"Bathroom Downstairs\",\n" +
			"        \"willReportState\": false,\n" +
			"        \"customData\": {\n" +
			"          \"action\": \"" + DEVICE_ID + "\"\n" +
			"        }\n" +
			"      }";

	@Mock private SmartHomeService smartHomeService;

	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);

		when(smartHomeService.fetchDevicesFromSmartHome(eq(REQUEST_ID), anyString())).thenReturn(
				new SyncResponse(REQUEST_ID,
						new Payload(AGENT_USER_ID, Arrays.asList(DEVICE_1))
				)
		);

		return new ResourceConfig()
				.register(new GoogleSmartHomeResourceImpl(smartHomeService))
				.register(new EndpointLoggingListener("/"))
				.register((ExceptionMapper<Throwable>) e -> {
					log.error("Error occurred:", e);
					return Response.serverError().build();
				})
				.property("jersey.config.server.tracing", "ALL")
				.property("jersey.config.server.tracing.threshold", "VERBOSE");

	}

	@Test
	public void testSyncCommandSuccess() throws Exception {
		String requestBody = FixtureHelper.fixture(SYNC_COMMAND_FIXTURE).replace("${REQUEST_UUID}", REQUEST_ID);
		String expectedBody = FixtureHelper.fixture(SYNC_RESPONSE_FIXTURE).replace("${REQUEST_UUID}", REQUEST_ID);

		Response actualResponse = target(SMARTHOME_PATH)
				//.register(new LoggingFeature(Logger.getLogger("sync command"), Level.ALL, LoggingFeature.Verbosity.PAYLOAD_ANY, Integer.MAX_VALUE))
				.request()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.post(json(requestBody));

		String actual = actualResponse.readEntity(String.class);

		String pathForDevice = String.format("payload.devices.find {it.id == '%s'}", DEVICE_ID);
		Map<String, ?> actualDevice1Map = JsonPath.from(actual).get(pathForDevice);
		Map<String, ?> expectedDeviceMap = JsonPath.from(expectedBody).get(pathForDevice);

		assertThat(actualDevice1Map).isEqualTo(expectedDeviceMap);
		assertThat(actualResponse.getStatus()).isEqualTo(SC_OK);
	}

	@Test
	@Ignore
	public void testExecuteCommandSuccess() throws Exception {
		String requestBody = FixtureHelper.fixture(EXEC_COMMAND_FIXTURE);

		Response actualResponse = target(SMARTHOME_PATH)
				.request()
				.post(json(requestBody));

		assertThat(actualResponse.getStatus()).isEqualTo(SC_OK);
		verifyZeroInteractions(smartHomeService);
	}

	@Test
	@Ignore
	public void testQueryCommandSuccess() throws Exception {
		String requestBody = FixtureHelper.fixture(QUERY_COMMAND_FIXTURE);

		Response actualResponse = target(SMARTHOME_PATH)
				.request()
				.post(json(requestBody));

		assertThat(actualResponse.getStatus()).isEqualTo(SC_OK);
		verifyZeroInteractions(smartHomeService);
	}

}