package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
	private int id;
	private double valor;
	private LocalDate data;
	private PlanoConta plano;
	private Conta contaOrigem;
	private Conta contaDestino;
}
