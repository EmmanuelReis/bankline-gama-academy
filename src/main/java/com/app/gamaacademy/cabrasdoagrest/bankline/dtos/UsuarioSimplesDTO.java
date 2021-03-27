package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSimplesDTO {

	private Integer id = 0;
	private String login;
	private String cpf;	
	private String nome;

}
