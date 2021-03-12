package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class PlanoContaBuilder {
    private Integer id;
    private String nome;
    private TipoPlanoConta tipo;

    public PlanoContaBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public PlanoContaBuilder comTipo(TipoPlanoConta tipo) {
        this.tipo = tipo;

        return this;
    }

    public PlanoConta build(Usuario usuario) {
        return new PlanoConta(this.id, this.nome, this.tipo, usuario);
    }
}
