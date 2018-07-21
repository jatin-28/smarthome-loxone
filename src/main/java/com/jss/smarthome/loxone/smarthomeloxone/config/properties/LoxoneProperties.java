package com.jss.smarthome.loxone.smarthomeloxone.config.properties;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 *
 */
@Data
@Validated
public class LoxoneProperties {
	@NotEmpty private String address;
	@NotEmpty private String user;
	@NotEmpty private String password;
	@NotEmpty private String uipassword;
}
