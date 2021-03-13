package com.app.gamaacademy.cabrasdoagrest.bankline.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
public class TestApp {

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private ContaRepository contaRepository = new ContaRepository();

	@Autowired
	private TransacaoServiceImpl transacaoService = new TransacaoServiceImpl();

	@Autowired
	private UsuarioBuilder ub = new UsuarioBuilder();

	@Autowired
	private UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();

	@Test
	@Order(1)
	@DisplayName("Testando criação e interação das classes do modelo")
	public void testaEstruturaModelo() {
		Usuario u = new Usuario();
		// u.setId(1);
		u.setCpf("12345678901");
		u.setLogin("gabriel");
		u.setNome("gabriel albuquerque");
		u.setSenha("123");

		Conta c = new Conta();
		c.setNumero(1101L);
		c.setSaldo(1500.32);
		c.setUsuario(u);

		PlanoConta pc = new PlanoConta();
		pc.setNome("LUZ");
		pc.setTipo(TipoPlanoConta.RECEITA);
		pc.setUsuario(u);

		Transacao t = new Transacao();
		t.setContaOrigem(c);
		t.setId(1);
		t.setPlanoConta(pc);
		t.setValor(200.72);

		System.out.println(t);
	}

	@Test
	@Order(2)
	@DisplayName("Testando salvar novo usuário e verificar se o id está sendo retornado")
	public void criarNovoUsuarioOrigem() throws Exception {
		boolean isValid = false;
		Usuario u = null;
		do {
			u = ub.loginRandom(Usuario.LOGIN_MAX_LENGTH).cpfRandom(Usuario.CPF_MAX_LENGTH)
					.nomeRandom(Usuario.NOME_MAX_LENGTH).senhaRandom(Usuario.SENHA_MAX_LENGTH).build();

			isValid = usuarioService.validaLoginCpfUnicos(u.getLogin(), u.getCpf());

		} while (!isValid);

		Usuario saveUser = userRepo.save(u);
		assertTrue(saveUser.getId() > 0);
	}

	@Test
	@Order(3)
	@DisplayName("Testando salvar novo usuário e verificar se o id está sendo retornado")
	public void alterarUsuario() throws Exception {
		Usuario u = userRepo.findByLoginEquals("gabriel");
		u.setNome("albuquerque gabriel");
		u.setSenha("456");

		userRepo.save(u);
		Usuario q = userRepo.findByLoginEquals("gabriel");
		assertEquals(q.getNome(), "albuquerque gabriel");
		assertEquals(q.getSenha(), "456");
	}

	@Test
	@Order(4)
	@DisplayName("Cadastra algumas transações em uma nova conta")
	public void processarTransacoes() throws Exception {

		Transacao receita = new Transacao();
		Transacao despesa = new Transacao();
		Transacao transferencia = new Transacao();
		List<Conta> listaContas = contaRepository.obterTodos().stream().filter(c -> c.getSaldo() == (double) 0).limit(2)
				.collect(Collectors.toList());

		Conta contaOrigem = listaContas.get(0);
		Conta contaDestino = listaContas.get(1);

		PlanoConta pc = new PlanoConta();

		pc.setNome("Luz");
		pc.setTipo(TipoPlanoConta.RECEITA);
		pc.setUsuario(contaOrigem.getUsuario());

		receita.setContaOrigem(contaOrigem);
		receita.setPlanoConta(pc);
		receita.setValor(100.0);

		receita = transacaoService.buscaPorId(transacaoService.salvar(receita));

		// Ver se o Gleyson consegue dar uma ajuda pq essa linha abaixo não está
		// funcionando.
		// Aparentemente no banco está alterando o saldo da conta, mas a busca do JPA
		// não está
		// retornando o objeto atualizado

		assertEquals(receita.getContaOrigem().getSaldo(), 100.0);

		PlanoConta pcd = new PlanoConta();

		pcd.setNome("Luz");
		pcd.setTipo(TipoPlanoConta.DESPESA);
		pcd.setUsuario(contaOrigem.getUsuario());

		despesa.setContaOrigem(contaOrigem);
		despesa.setPlanoConta(pcd);
		despesa.setValor(-25.0);

		despesa = transacaoService.buscaPorId(transacaoService.salvar(despesa));

		PlanoConta pct = new PlanoConta();

		pct.setNome("Luz");
		pct.setTipo(TipoPlanoConta.TRANSFERENCIA);
		pct.setUsuario(contaOrigem.getUsuario());

		assertEquals(despesa.getContaOrigem().getSaldo(), 75.0);

		transferencia.setContaOrigem(contaOrigem);
		transferencia.setContaDestino(contaDestino);
		transferencia.setPlanoConta(pct);
		transferencia.setValor(30.0);

		transferencia = transacaoService.buscaPorId(transacaoService.salvar(transferencia));

		assertEquals(transferencia.getContaOrigem().getSaldo(), 45.0);
		assertEquals(transferencia.getContaDestino().getSaldo(), 30.0);

	}

}
