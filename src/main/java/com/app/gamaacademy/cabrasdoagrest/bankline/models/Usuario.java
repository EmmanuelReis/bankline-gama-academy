package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.app.gamaacademy.cabrasdoagrest.bankline.listeners.UsuarioListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(UsuarioListener.class)
public class Usuario {

	public final static int LOGIN_MAX_LENGTH = 20;
	public final static int CPF_MAX_LENGTH = 11;
	public final static int SENHA_MAX_LENGTH = 20;
	public final static int NOME_MAX_LENGTH = 50;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false, precision = LOGIN_MAX_LENGTH)
	private String login;

	@Column(unique = true, nullable = false, precision = CPF_MAX_LENGTH)
	private String cpf;

	@Column(precision = SENHA_MAX_LENGTH)
	private String senha;

	@Column(precision = NOME_MAX_LENGTH)
	private String nome;

	@JsonIgnore
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Conta conta;
}
