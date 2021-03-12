package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioBuilder {
    private Integer id;
	private String login = "123";
	private String cpf = "12312312312";
	private String senha = "123";
	private String nome = "Foo Bar";
	private Conta conta;

    public Usuario build() {
        Usuario usuario = new Usuario(this.id, this.login, this.cpf, this. senha, this.nome, this.conta);
        
        this.conta = new ContaBuilder().build(usuario);

        return usuario;
    }
}
