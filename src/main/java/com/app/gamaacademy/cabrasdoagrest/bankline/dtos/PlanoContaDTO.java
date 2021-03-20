package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoContaDTO {

	private Integer id;
	private String nome;
	private TipoOperacao tipo;

}
