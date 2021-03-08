package com.app.gamaacademy.cabrasdoagrest.bankline.models;

public enum TipoContaEnum {
	BANCO(1, "BANCO"), CREDITO(2, "CREDITO");

	public int id;
	public String nome;

	private TipoContaEnum(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public TipoConta toObj() {
		return new TipoConta(this.id, this.nome);
	}
}
