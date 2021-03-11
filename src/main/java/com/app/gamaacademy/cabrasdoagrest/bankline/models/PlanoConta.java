package com.app.gamaacademy.cabrasdoagrest.bankline.models;

public enum PlanoConta {
	RECEITA("R"), DESPESA("D"), TRANSFERENCIA("T");

	private String code;

    private PlanoConta(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
