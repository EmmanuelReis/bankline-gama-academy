package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtratoDTO {

	private Double saldo = 0.0;
	private LocalDate inicio;
	private LocalDate fim;
	private List<TransacaoDTO> transacoes = new ArrayList<>();

}
