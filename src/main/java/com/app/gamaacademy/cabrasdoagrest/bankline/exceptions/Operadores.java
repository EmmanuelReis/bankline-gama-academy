package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

public enum Operadores {

	MENOR("<"), MENOR_IGUAL("<="), MAIOR(">"), MAIOR_IGUAL(">=");

	private String operador;

	public String getOperador() {
		return operador;
	}

	private Operadores(String operador) {
		this.operador = operador;
	}

}
