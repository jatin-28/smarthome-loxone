package com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.dto;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class SmartHomeRequest {
	private String requestId;
	private List<PayloadRequest> inputs;
}
