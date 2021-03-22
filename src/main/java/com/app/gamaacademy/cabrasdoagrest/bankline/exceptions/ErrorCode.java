package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

	E0001("Não foi possível encontrar o objeto com o id fornecido."), E0002("Exceção de Negócio");

	private ErrorCode(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

}
