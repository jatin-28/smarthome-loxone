package com.jss.smarthome.loxone.smarthomeloxone;

import com.jss.smarthome.loxone.smarthomeloxone.config.GoogleSmartHomeConfig;
import com.jss.smarthome.loxone.smarthomeloxone.config.JerseyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SmarthomeLoxoneApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(
				SmarthomeLoxoneApplication.class,
				GoogleSmartHomeConfig.class,
				JerseyConfig.class
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(
				SmarthomeLoxoneApplication.class, args);
	}
}
