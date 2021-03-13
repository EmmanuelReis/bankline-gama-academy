package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

	public final static int LOGIN_MAX_LENGTH = 20;
	public final static int CPF_MAX_LENGTH = 11;
	public final static int SENHA_MAX_LENGTH = 20;
	public final static int NOME_MAX_LENGTH = 50;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false, precision = LOGIN_MAX_LENGTH)
	private String login;

	@Column(unique = true, nullable = false, precision = CPF_MAX_LENGTH)
	private String cpf;

	@Column(precision = SENHA_MAX_LENGTH)
	private String senha;

	@Column(precision = NOME_MAX_LENGTH)
	private String nome;

	@OneToOne(mappedBy = "usuario")
	private Conta conta;
}
