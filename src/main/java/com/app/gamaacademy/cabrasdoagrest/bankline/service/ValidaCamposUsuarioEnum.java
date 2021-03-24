package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public enum ValidaCamposUsuarioEnum {

	cpf(11, Usuario.CPF_MAX_LENGTH), login(4, Usuario.LOGIN_MAX_LENGTH), senha(3, Usuario.NOME_MAX_LENGTH),
	nome(6, Usuario.SENHA_MAX_LENGTH);

	private int min;
	private int max;

	private ValidaCamposUsuarioEnum(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}
