package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private Integer id;
	private String login;
	private String cpf;
	private String senha;
	private String nome;
	private ContaDTO conta;
	private List<PlanoContaDTO> planos;

}
