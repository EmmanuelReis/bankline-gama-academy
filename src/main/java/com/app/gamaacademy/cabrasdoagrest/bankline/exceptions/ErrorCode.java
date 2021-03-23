package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

	/**
	 * Elemento Raiz nulo
	 */
	E0001("Elemento não informado ou nulo"),
	/**
	 * Propriedade obrigatória de objeto não informada ou nula
	 */
	E0002("Propriedade obrigatória de objeto não informada ou nula"),
	/**
	 * Não foi possível encontrar o objeto com o id fornecido
	 */
	E0003("Não foi possível encontrar o objeto com o id fornecido"),
	/**
	 * Valor inválido para o campo
	 */
	E0004("Valor inválido para o campo"),
	/**
	 * Para transferência Conta destino tem de ser diferente da conta de origem *
	 */
	E0005("Para transferência Conta destino tem de ser diferente da conta de origem"),
	/**
	 * Valor do campo já cadastrado, não é permitido duplicados
	 */
	E0006("Valor do campo já cadastrado, não é permitido duplicados"),
	/**
	 * Valor do campo informado está fora do limite permitido
	 */
	E0007("Valor do campo informado está fora do limite permitido"),
	/**
	 * Tamanho do campo informado está fora do limite permitido
	 */
	E0008("Tamanho do campo informado está fora do limite permitido");

	private ErrorCode(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

}
