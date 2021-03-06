package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {

	private Long numero = 0L;
	private Double saldo = 0.0;
	private UsuarioSimplesDTO usuario;

}
