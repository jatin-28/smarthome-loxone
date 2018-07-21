package com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

/**
 *
 */
@Data
public class PayloadRequest {
	private String intent;

	@JsonRawValue
	private String payload;
}
