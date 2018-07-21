package com.jss.smarthome.loxone.smarthomeloxone.domain;

import lombok.Data;

/**
 *
 */
@Data
public class SyncResponse {
	private final String requestId;
	private final Payload payload;
}
