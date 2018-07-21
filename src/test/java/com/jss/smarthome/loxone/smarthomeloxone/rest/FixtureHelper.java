package com.jss.smarthome.loxone.smarthomeloxone.rest;

import com.google.common.io.Resources;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.nio.charset.Charset;

public class FixtureHelper {
	private FixtureHelper() {
	}

	public static String fixture(String filename) {
		return fixture(filename, Charsets.UTF_8);
	}

	private static String fixture(String filename, Charset charset) {
		try {
			return Resources.toString(Resources.getResource(filename), charset).trim();
		} catch (IOException var3) {
			throw new IllegalArgumentException(var3);
		}
	}
}