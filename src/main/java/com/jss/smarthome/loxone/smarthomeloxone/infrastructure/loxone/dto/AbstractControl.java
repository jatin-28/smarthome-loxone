package com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.smarteon.loxone.config.Control;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 */
// TODO should push this up to Control
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractControl extends Control {
	private String room;

	public abstract String getType();
}
