package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Transacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double valor;

	@OrderBy("data DESC")
	private LocalDateTime data;

	@Column(name = "plano_conta")
	private TipoPlanoConta planoConta;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "numero_conta_origem", foreignKey = @ForeignKey(name = "fk_transacao_conta_origem"))
	private Conta contaOrigem;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "numero_conta_destino", foreignKey = @ForeignKey(name = "fk_transacao_conta_destino"))
	private Conta contaDestino;

	public Transacao() {
		this.data = LocalDateTime.now(ZoneOffset.UTC);
	}
}
