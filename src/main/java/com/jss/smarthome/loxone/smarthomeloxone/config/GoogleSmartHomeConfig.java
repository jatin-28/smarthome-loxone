package com.jss.smarthome.loxone.smarthomeloxone.config;

import com.jss.smarthome.loxone.smarthomeloxone.config.properties.SmartHomeProperties;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.TemplateEngine;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.loxone.LoxoneConnection;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.LoxoneToSmartDeviceAssembler;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.SmartHomeService;
import com.jss.smarthome.loxone.smarthomeloxone.infrastructure.smarthome.SmartHomeToJsonConverter;
import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.GoogleSmartHomeResource;
import com.jss.smarthome.loxone.smarthomeloxone.rest.smarthome.GoogleSmartHomeResourceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 *
 */
@Configuration
@EnableConfigurationProperties(SmartHomeProperties.class)
public class GoogleSmartHomeConfig {

	private final SmartHomeProperties smartHomeProperties;

	@Inject
	public GoogleSmartHomeConfig(SmartHomeProperties smartHomeProperties) {
		this.smartHomeProperties = smartHomeProperties;
	}

	@Bean
	public GoogleSmartHomeResource googleSmartHomeResource() {
		return new GoogleSmartHomeResourceImpl(smartHomeService());
	}

	@Bean
	public LoxoneToSmartDeviceAssembler loxoneToSmartDeviceAssembler() {
		return new LoxoneToSmartDeviceAssembler();
	}

	@Bean
	public SmartHomeToJsonConverter smartHomeToJsonConverter() {
		return new SmartHomeToJsonConverter(templateEngine());
	}

	@Bean
	public TemplateEngine templateEngine() {
		return new TemplateEngine();
	}

	@Bean
	public SmartHomeService smartHomeService() {
		return new SmartHomeService(loxoneConnection(), loxoneToSmartDeviceAssembler(), smartHomeToJsonConverter());
	}

	@Bean(destroyMethod = "shutDown")
	public LoxoneConnection loxoneConnection() {
		return new LoxoneConnection(smartHomeProperties.getLoxone());
	}
}
