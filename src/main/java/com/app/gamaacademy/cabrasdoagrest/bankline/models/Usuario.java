package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	private int id;
	private String login;
	private String senha;
	private String nome;
	private String cpf;
}
