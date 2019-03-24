package com.recipe.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Wrappes Json marshaling and unmarshaling exceptions
 * 
 * @author supun
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class JsonDataException extends Exception {

	private static final long serialVersionUID = -6474748146279686842L;

	private static final String JSON_DATA_ERROR_MESSAGE = "Error in Json data";

	public JsonDataException(Throwable cause) {
		super(JSON_DATA_ERROR_MESSAGE, cause);
	}

	public JsonDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
