package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class NoSuchElementsFoundException extends ResponseStatusException {

	private String object;
	private String property;
	private String value;
	private ErrorCode errorCode;

	public NoSuchElementsFoundException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
		this.errorCode = ErrorCode.E0001;
	}

	public NoSuchElementsFoundException(String object, String property, String value) {
		this("");
		this.object = object;
		this.property = property;
		this.value = value;
	}

	public String getMessage() {
		return this.toString();
	}

	@Override
	public String toString() {
		String message;

		if (!StringUtils.isBlank(this.object))
			message = String.format("%s - %s: Objeto: %s, campo: %s, valor %s.", this.errorCode.name(),
					this.errorCode.getDescricao(), object, property, value);
		else
			message = this.getMessage();

		return message;
	}

}
