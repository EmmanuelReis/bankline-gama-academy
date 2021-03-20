package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {

	private Integer id = 0;
	private Double valor;
	private LocalDateTime data;
	private PlanoContaDTO planoConta;
	private ContaDTO contaOrigem;
	private ContaDTO contaDestino;
	private Map<String, Double> subtotal;

}
