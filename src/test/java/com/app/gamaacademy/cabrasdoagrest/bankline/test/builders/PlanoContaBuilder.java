package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

import org.springframework.stereotype.Component;

@Component
public class PlanoContaBuilder {
    private Integer id;
    private String nome;
    private TipoOperacao tipo;
    private Usuario usuario;

    public PlanoContaBuilder comId(Integer id) {
        this.id = id;

        return this;
    }

    public PlanoContaBuilder comNome(String nome) {
        this.nome = nome;

        return this;
    }

    public PlanoContaBuilder comTipo(TipoOperacao tipo) {
        this.tipo = tipo;

        return this;
    }

    public PlanoContaBuilder doUsuario(Usuario usuario) {
        this.usuario = usuario;

        return this;
    }

    public PlanoConta build() {
        return new PlanoConta(this.id, this.nome, this.tipo, this.usuario);
    }

    public PlanoContaDTO buildDto() {
        return Mapper.convertPlanoContaToDto(build());
    }
}
