package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cz.smarteon.loxone.LoxoneUuid;
import cz.smarteon.loxone.config.AlarmControl;
import cz.smarteon.loxone.config.Control;
import cz.smarteon.loxone.config.SwitchControl;
import cz.smarteon.loxone.config.UnknownControl;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoxoneConfigPayload {
	private Date lastModified;
	private Map<LoxoneUuid, Room> rooms;

	@JsonTypeInfo(defaultImpl = UnknownControl.class, use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(name = AlarmControl.NAME, value = AlarmControl.class),
			@JsonSubTypes.Type(name = SwitchControl.NAME, value = SwitchControl.class),
			@JsonSubTypes.Type(name = LightingControl.TYPE, value = LightingControl.class)
	})
	private Map<LoxoneUuid, Control> controls;
}
