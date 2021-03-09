package com.app.gamaacademy.cabrasdoagrest.bankline.models;

public enum PlanoContaEnum {
	RECEITA(1, "R", "RECEITA"), DESPESA(2, "D", "DESPESA"), TRANSFERENCIA(3, "T", "TRANSFERENCIA");

	public int id;
	public String sigla;
	public String nome;

	private PlanoContaEnum(int id, String sigla, String nome) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
	}

	public PlanoConta toObj() {
		return new PlanoConta(this.id, this.sigla, this.nome);
	}

}
