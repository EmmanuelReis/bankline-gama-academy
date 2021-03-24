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
public class UsuarioDTO extends UsuarioSimplesDTO {

	private ContaDTO conta;
	private List<PlanoContaDTO> planos;

	public UsuarioDTO(UsuarioSimplesDTO simples) {
		super(simples.getId(), simples.getLogin(), simples.getCpf(), simples.getSenha(), simples.getNome());
	}

}
