package com.jss.smarthome.loxone.smarthomeloxone;

import com.jss.smarthome.loxone.smarthomeloxone.rest.FixtureHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmarthomeLoxoneApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmarthomeLoxoneApplicationTests {

	private static final String SYNC_COMMAND_FIXTURE = "fixtures/smarthome/sync_request.json";
	private static final String SYNC_RESPONSE_FIXTURE = "fixtures/smarthome/sync_response_expected.json";

	@LocalServerPort
	private int port;
	private String smartHomeUrlRequest;

	@Before
	public void setUp() throws Exception {
		smartHomeUrlRequest = String.format("http://localhost:%s/smarthome", port);
	}

	@Test
	public void testExceptionMapper() {
		when()
				.get(String.format("http://localhost:%s/smarthome", port))
				.then()
				.body(is("Something terrible and unexpected happened!"))
				.statusCode(is(500));
	}

	@Test
	public void testRequestForSync() {
		String requestUuid = UUID.randomUUID().toString();
		String requestBody = FixtureHelper.fixture(SYNC_COMMAND_FIXTURE).replace("${REQUEST_UUID}", requestUuid);
		String expectedBody = FixtureHelper.fixture(SYNC_RESPONSE_FIXTURE).replace("${REQUEST_UUID}", requestUuid);

		ExtractableResponse<Response> extractableResponse = given()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.body(requestBody)
				.when()
				.post(smartHomeUrlRequest)
				.then()
				.statusCode(is(200))
				.extract();

		assertDeviceExists(expectedBody, extractableResponse, "<TODO>");
		assertDeviceExists(expectedBody, extractableResponse, "<TODO>");

	}

	private void assertDeviceExists(String expectedBody, ExtractableResponse<Response> extractableResponse, String deviceId) {
		String pathForDevice = String.format("payload.devices.find {it.id == '%s'}", deviceId);
		Map<String,?> actualDevice = extractableResponse.body().jsonPath().get(pathForDevice);

		Map<String,?> expectedDevice1 = JsonPath.from(expectedBody).get(pathForDevice);

		assertThat(actualDevice).isEqualTo(expectedDevice1);
	}
}
