package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import java.util.Random;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

import org.springframework.stereotype.Component;

@Component
public class ContaBuilder {
	private Long numero;
    private Double saldo = 0.0;
    private Usuario usuario;

    public void inicial() {
        this.numero = Math.abs(new Random().nextLong());
    }

    public ContaBuilder doUsuario(Usuario usuario) {
        this.usuario = usuario;

        return this;
    }

    public Conta build() {
        return new Conta(this.numero, this.saldo, this.usuario);
    }
}
