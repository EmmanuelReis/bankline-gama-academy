package com.app.gamaacademy.cabrasdoagrest.bankline.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.ContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.PlanoContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.TransacaoBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.UsuarioBuilder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransacaoRepositoryTest {
    private TransacaoRepository transacaoRepo = new TransacaoRepository();

	@Test
	@DisplayName("Testando criação e interação das classes do modelo")
	public void testaEstruturaModelo() {
        PlanoConta planoConta = new PlanoContaBuilder()
                                    .comNome("Luz")
                                    .comTipo(TipoPlanoConta.RECEITA)
                                    .build(Um.usuario.build());

		System.out.println(Uma.transacao.comPlano(planoConta));

        System.out.println(transacaoRepo.salvar(Uma.transacao.build()));
	}

    public static class Um {
        public static UsuarioBuilder usuario = new UsuarioBuilder();
    }

    public static class Uma {
        public static TransacaoBuilder transacao = new TransacaoBuilder();
        public static ContaBuilder Conta = new ContaBuilder();
    }
}
