package com.app.gamaacademy.cabrasdoagrest.bankline.test;

import org.apache.commons.lang3.RandomStringUtils;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioBuilder {

	private int id;
	private String login;
	private String cpf;
	private String senha;
	private String nome;
	private Conta conta;

	public UsuarioBuilder() {
	}

	UsuarioBuilder(int id, String login, String cpf, String senha, String nome, Conta conta) {
		this.id = id;
		this.login = login;
		this.cpf = cpf;
		this.senha = senha;
		this.nome = nome;
		this.conta = conta;
	}

	public UsuarioBuilder id(int id) {
		this.id = id;
		return UsuarioBuilder.this;
	}

	public UsuarioBuilder login(String login) {
		this.login = login;
		return UsuarioBuilder.this;
	}

	public UsuarioBuilder loginRandom(int length) {
		return login(RandomStringUtils.randomAlphabetic(length));
	}

	public UsuarioBuilder cpf(String cpf) {
		this.cpf = cpf;
		return UsuarioBuilder.this;
	}

	public UsuarioBuilder cpfRandom(int length) {
		return cpf(RandomStringUtils.randomNumeric(length));
	}

	public UsuarioBuilder senha(String senha) {
		this.senha = senha;
		return UsuarioBuilder.this;
	}

	public UsuarioBuilder senhaRandom(int length) {
		return senha(RandomStringUtils.randomAlphanumeric(length));
	}

	public UsuarioBuilder nome(String nome) {
		this.nome = nome;
		return UsuarioBuilder.this;
	}

	public UsuarioBuilder nomeRandom(int length) {
		return nome(RandomStringUtils.randomAlphabetic(length));
	}

	public UsuarioBuilder conta(Conta conta) {
		this.conta = conta;
		return UsuarioBuilder.this;
	}

	public Usuario build() {
		if (this.login == null) {
			throw new NullPointerException("The property \"login\" is null. " + "Please set the value by \"login()\". "
					+ "The properties \"login\", \"cpf\" are required.");
		}
		if (this.cpf == null) {
			throw new NullPointerException("The property \"cpf\" is null. " + "Please set the value by \"cpf()\". "
					+ "The properties \"login\", \"cpf\" are required.");
		}

		return construct(this);
	}

	private Usuario construct(UsuarioBuilder builder) {
		Usuario u = new Usuario();

		u.setId(builder.id);
		u.setLogin(builder.login);
		u.setCpf(builder.cpf);
		u.setSenha(builder.senha);
		u.setNome(builder.nome);
		u.setConta(builder.conta);

		return u;
	}
}
