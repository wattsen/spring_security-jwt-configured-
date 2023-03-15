package io.linear.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException{

	private final List<String> errorMessages;

	public ValidationException(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}


}
