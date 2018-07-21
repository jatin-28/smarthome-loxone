package com.jss.smarthome.loxone.smarthomeloxone.infrastructure;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.SneakyThrows;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 *
 */
public final class TemplateEngine {
	private final PebbleEngine engine = new PebbleEngine.Builder().build();

	@SneakyThrows
	public TemplateEngine() {
		engine.getTemplate("smarthome/synctemplates/LightControllerV2_Switch.json");
		engine.getTemplate("smarthome/synctemplates/LightControllerV2_Dimmer.json");
	}

	@SneakyThrows
	public String render(String templateName, Object context) {
		String filepath = format("smarthome/synctemplates/%s.json", templateName);
		PebbleTemplate compiledTemplate = engine.getTemplate(filepath);

		Map<String, Object> contextMap = new HashMap<>();
		contextMap.put("device", context);

		Writer writer = new StringWriter();

		compiledTemplate.evaluate(writer, contextMap);

		return writer.toString();
	}


	@SneakyThrows
	public String loadTemplate(String templateName) {
		String filepath = format("smarthome/synctemplates/%s.json", templateName);
		Path path = Paths.get(getClass().getClassLoader().getResource(filepath).toURI());

		StringBuilder data = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> data.append(line).append("\n"));
		lines.close();

		return data.toString();
	}


}
