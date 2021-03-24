package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {

	@ApiModelProperty(value = "Saldo atual da conta")
	private Double saldoAtual = 0.0;
	@ApiModelProperty(value = "Saldo do período informado")
	private Double saldoPeriodo = 0.0;
	@ApiModelProperty(value = "Data início do perído do extrato")
	private LocalDate dtInicio;
	@ApiModelProperty(value = "Data fim do perído do extrato")
	private LocalDate dtFim;
	@ApiModelProperty(value = "Transações do período")
	private List<TransacaoDTO> transacoes = new ArrayList<>();

}
