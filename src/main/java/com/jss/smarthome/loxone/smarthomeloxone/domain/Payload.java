package com.jss.smarthome.loxone.smarthomeloxone.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class Payload {
	private final String agentUserId;

	@JsonRawValue
	private final List<String> devices;
}
