package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import java.util.Random;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class ContaBuilder {
	private Long numero;
    private Double saldo = 0.0;

    public ContaBuilder() {
        this.numero = new Random().nextLong();
    }

    public Conta build(Usuario usuario) {
        return new Conta(this.numero, this.saldo, usuario);
    }
}
