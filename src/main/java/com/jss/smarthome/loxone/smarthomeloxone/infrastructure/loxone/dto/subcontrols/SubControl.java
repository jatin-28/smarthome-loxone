package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto.subcontrols;

import com.fasterxml.jackson.annotation.*;
import cz.smarteon.loxone.LoxoneUuids;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SubControl {
	private String name;
	private String uuidAction;

	@JsonProperty("states") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	protected Map<String, LoxoneUuids> states;

	public abstract String getType();
}
