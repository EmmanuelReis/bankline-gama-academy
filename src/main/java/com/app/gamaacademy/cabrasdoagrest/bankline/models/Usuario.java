package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false)
	private String login;

	@Column(unique = true, nullable = false, precision = 11)
	private String cpf;

	@Column(precision = 20)
	private String senha;

	@Column(precision = 50)
	private String nome;

}
