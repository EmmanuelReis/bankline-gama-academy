package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Transacao {

	public Transacao() {
		this.data = LocalDate.now();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double valor;
	private LocalDate data;

	@Enumerated(EnumType.STRING)
	private PlanoConta plano;

	@ManyToOne(targetEntity = Conta.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "numero_conta_origem", referencedColumnName = "numero", foreignKey = @ForeignKey(name = "fk_transacao_conta_origem"))
	private Conta contaOrigem;

	@ManyToOne(targetEntity = Conta.class, fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "numero_conta_destino", referencedColumnName = "numero", foreignKey = @ForeignKey(name = "fk_transacao_conta_destino"))
	private Conta contaDestino;
}
