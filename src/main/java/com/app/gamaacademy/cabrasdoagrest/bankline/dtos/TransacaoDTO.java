package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
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
	private Double valor = 0.0;
	@ApiModelProperty(value = "Data da transação")
	private LocalDateTime data;
	@ApiModelProperty(value = "Plano de Conta associado a transação")
	private PlanoContaDTO planoConta;
	@ApiModelProperty(value = "Conta origem da transação")
	private ContaDTO contaOrigem;
	@ApiModelProperty(value = "Conta destino da transação. Preencher em casos de transferência", required = false)
	private ContaDTO contaDestino;

}
