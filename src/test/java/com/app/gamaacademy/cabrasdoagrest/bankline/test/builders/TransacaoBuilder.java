package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

@Component
public class TransacaoBuilder {
	private Integer id;
	private Double valor;
	private LocalDateTime data;
	private PlanoConta planoConta;
	private Conta contaOrigem;
	private Conta contaDestino;

	public void inicial() {
		id = null;
		valor = 0.0;
		data = LocalDateTime.now();
		planoConta = null;
		contaOrigem = null;
		contaDestino = null;
	}

	public TransacaoBuilder comId(Integer id) {
		this.id = id;

		return this;
	}

	public TransacaoBuilder comValor(Double valor) {
		this.valor = valor;

		return this;
	}

	public TransacaoBuilder comPlano(PlanoConta planoConta) {
		this.planoConta = planoConta;

		return this;
	}

	public TransacaoBuilder daConta(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;

		return this;
	}

	public TransacaoBuilder paraConta(Conta contaDestino) {
		this.contaDestino = contaDestino;

		return this;
	}

	public Transacao build() {
		return new Transacao(this.id, this.valor, this.data, this.planoConta, this.contaOrigem, this.contaDestino);
	}

	public TransacaoDTO buildDto() {
		return Mapper.convertTransacaoEntityToDto(build());
	}
}
