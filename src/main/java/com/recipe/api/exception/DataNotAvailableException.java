package com.recipe.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DataNotAvailableException wrappes standard Java IOException when data cannot
 * be read from file system
 * 
 * @author supun
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotAvailableException extends Exception {

	private static final long serialVersionUID = 517065247962807381L;

	private final static String DATA_NOT_AVAILABLE_MESSAGE = "Data not available";

	public DataNotAvailableException(Throwable cause) {
		super(DATA_NOT_AVAILABLE_MESSAGE, cause);
	}

	public DataNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

}