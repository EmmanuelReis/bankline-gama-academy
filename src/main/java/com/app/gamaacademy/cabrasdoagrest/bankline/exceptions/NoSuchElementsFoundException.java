package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

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

	public NoSuchElementsFoundException(String object, String property, String value) {
		super(HttpStatus.BAD_REQUEST);
		this.object = object;
		this.property = property;
		this.value = value;
		this.errorCode = ErrorCode.E0001;
	}

	public String getMessage() {
		return this.toString();
	}

	@Override
	public String toString() {
		return String.format("%s - %s: Objeto: %s, campo: %s, valor %s.", this.errorCode.name(),
				this.errorCode.getDescricao(), object, property, value);
	}

}
