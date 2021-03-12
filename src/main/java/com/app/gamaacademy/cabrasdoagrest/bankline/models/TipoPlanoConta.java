package com.app.gamaacademy.cabrasdoagrest.bankline.models;

public enum TipoPlanoConta {
	RECEITA("R", 1), DESPESA("D", -1), TRANSFERENCIA("T", -1);

	private String codigo;
    private Integer multiplicador;

    private TipoPlanoConta(String codigo, Integer multiplicador) {
        this.codigo = codigo;
        this.multiplicador = multiplicador;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getMultiplicador() {
        return multiplicador;
    }
}
