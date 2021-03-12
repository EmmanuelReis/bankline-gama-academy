package com.app.gamaacademy.cabrasdoagrest.bankline.test.builders;

import java.time.LocalDateTime;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public class TransacaoBuilder {
    private Integer id = null;
    private Double valor = 200.5;
    private LocalDateTime data = LocalDateTime.now();
    private PlanoConta planoConta;
    private Conta contaOrigem = null;
    private Conta contaDestino = null;

    public TransacaoBuilder comAsContas(Conta contaOrigem, Conta contaDestino) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;

        return this;
    }

    public Transacao build() {
        return new Transacao(this.id, this.valor, this.data, this.planoConta, this.contaOrigem, this.contaDestino);
    }
}
