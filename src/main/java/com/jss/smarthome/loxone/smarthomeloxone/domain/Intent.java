package com.jss.smarthome.loxone.smarthomeloxone.domain;

import java.util.Optional;

import static java.util.Arrays.stream;

/**
 *
 */
public enum Intent {
	SYNC ("action.devices.SYNC"),
	EXEC ("action.devices.EXECUTE"),
	QUERY("action.devices.QUERY");

	private String label;

	Intent(String label) {
		this.label = label;
	}

	public static Intent lookUpLabel(String label) {
		Optional<Intent> intent = stream(values()).findFirst().filter(v -> v.label.equalsIgnoreCase(label));
		return intent.orElse(null);
	}
}
