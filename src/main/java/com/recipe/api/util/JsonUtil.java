package com.recipe.api.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.api.exception.JsonDataException;

/**
 * Support class to Marshal and Unmarshal JSON data
 * 
 * @author supun
 *
 */
public class JsonUtil {

	/**
	 * Convert Json data from given input stream to object(s)
	 * 
	 * @param inputStream
	 * @param clazz
	 * @return Object
	 * @throws JsonDataException
	 */
	public static <T> T jsonFile2Object(InputStream inputStream, Class<T> clazz) throws JsonDataException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(inputStream, clazz);
		} catch (JsonParseException e) {
			throw new JsonDataException("Error parsing Json data", e);
		} catch (JsonMappingException e) {
			throw new JsonDataException("Error mapping Json data", e);
		} catch (IOException e) {
			throw new JsonDataException("Error in Json data", e);
		}
	}

	/**
	 * Convert given object to a Json string
	 * 
	 * @param obj
	 * @return String
	 * @throws JsonDataException
	 */
	public static <T> String object2Json(T obj) throws JsonDataException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JsonDataException("Error converting object to json data", e);
		}
	}

	/**
	 * Convert given Json string to an object
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return Object
	 * @throws JsonDataException
	 */
	public static <T> T json2Object(String jsonStr, Class<T> clazz) throws JsonDataException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			throw new JsonDataException("Error converting object to json data", e);
		}
	}
}
