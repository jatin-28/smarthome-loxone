package com.jss.smarthome.loxone.smarthomeloxone.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 *
 */
@ConfigurationProperties(prefix = "smarthome")
@Validated
@Data
public class SmartHomeProperties {
	@NotNull
	private LoxoneProperties loxone;

}
