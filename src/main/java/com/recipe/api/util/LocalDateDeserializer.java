package com.recipe.api.util;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

	private static final long serialVersionUID = -9203574672841612936L;

	protected LocalDateDeserializer() {
		super(LocalDate.class);
	}

	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDate.parse(parser.readValueAs(String.class));
	}

}
