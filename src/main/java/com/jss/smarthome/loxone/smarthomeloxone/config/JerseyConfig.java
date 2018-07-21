package com.jss.smarthome.loxone.smarthomeloxone.config;

import com.jss.smarthome.loxone.smarthomeloxone.rest.EndpointLoggingListener;
import com.jss.smarthome.loxone.smarthomeloxone.rest.GenericExceptionMapper;
import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.GoogleSmartHomeResourceImpl;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@Slf4j
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig(JerseyProperties jerseyProperties) {
		register(GoogleSmartHomeResourceImpl.class);
		register(new EndpointLoggingListener(jerseyProperties.getApplicationPath()));
		register(GenericExceptionMapper.class);
	}

}
