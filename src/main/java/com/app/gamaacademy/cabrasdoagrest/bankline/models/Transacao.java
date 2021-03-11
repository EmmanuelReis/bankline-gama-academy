package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double valor;
	private LocalDate data;
	
	@Enumerated(EnumType.STRING)
	private PlanoConta plano;

	@ManyToOne(targetEntity = Conta.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conta_origem", referencedColumnName = "id")
	private int idContaOrigem;
	
	@ManyToOne(targetEntity = Conta.class, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_conta_destino", referencedColumnName = "id")
	private int idContaDestino;
}
