package com.app.gamaacademy.cabrasdoagrest.bankline.test.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.ContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.PlanoContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.TransacaoBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.UsuarioBuilder;

@SpringBootTest
public class TransacaoRepositoryTest {
	@Autowired
	private TransacaoRepository transacaoRepo;

	@Test
	@DisplayName("Testando criação e interação das classes do modelo")
	public void testaEstruturaModelo() {
		PlanoConta planoConta = new PlanoContaBuilder().comNome("Luz").comTipo(TipoOperacao.RECEITA)
				.build(Um.usuario.build());

		System.out.println(Uma.transacao.comPlano(planoConta));

		System.out.println(transacaoRepo.save(Uma.transacao.build()));
	}

	public static class Um {
		public static UsuarioBuilder usuario = new UsuarioBuilder();
	}

	public static class Uma {
		public static TransacaoBuilder transacao = new TransacaoBuilder();
		public static ContaBuilder Conta = new ContaBuilder();
	}
}
