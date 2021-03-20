package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

import org.springframework.stereotype.Component;

@Component
public class UsuarioBuilder {
    private Integer id;
	private String login;
	private String cpf;
	private String senha;
	private String nome;
	private Conta conta;

    public void valido() {
        login = "123123";
        cpf = "12312312312";
        senha = "123123";
        nome = "Foo Bar";
    }

    public UsuarioBuilder comId() {
        id = 1;

        return this;
    }

    public UsuarioBuilder comLoginInvalido() {
        login = "123";

        return this;
    }

    public UsuarioBuilder comCpfInvalido() {
        cpf = "123";

        return this;
    }

    public UsuarioBuilder comSenhaInvalida() {
        senha = "123";

        return this;
    }

    public UsuarioBuilder comNomeInvalido() {
        nome = "Aa";

        return this;
    }

    public Usuario build() {
        Usuario usuario = new Usuario(this.id, this.login, this.cpf, this. senha, this.nome, this.conta);
        
        this.conta = new ContaBuilder().build(usuario);

        return usuario;
    }

    public UsuarioDTO buildDto() {
        return Mapper.convertUsuarioToDto(build());
    }
}
