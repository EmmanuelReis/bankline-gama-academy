package com.app.gamaacademy.cabrasdoagrest.bankline.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.DefaultService;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
public class TestApp {

	private UsuarioServiceImpl usuarioService = new UsuarioServiceImpl();
	DefaultService<Transacao> transacaoService = new TransacaoServiceImpl();
	ContaRepository contaRepository = new ContaRepository();

	private UsuarioBuilder ub = new UsuarioBuilder();

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

		u = usuarioService.buscaPorId(usuarioService.salvar(u));
		System.out
				.println(String.format("Usuario. login: %s ; cpf: %s ; id: %s .", u.getLogin(), u.getCpf(), u.getId()));
		assertTrue(u.getId() > 0);
	}

	@Test
	@Order(3)
	@DisplayName("Testando salvar novo usuário e verificar se o id está sendo retornado")
	public void criarNovoUsuarioDestino() throws Exception {
		boolean isValid = false;
		Usuario u = null;
		do {
			u = ub.loginRandom(Usuario.LOGIN_MAX_LENGTH).cpfRandom(Usuario.CPF_MAX_LENGTH)
					.nomeRandom(Usuario.NOME_MAX_LENGTH).senhaRandom(Usuario.SENHA_MAX_LENGTH).build();

			isValid = usuarioService.validaLoginCpfUnicos(u.getLogin(), u.getCpf());

		} while (!isValid);

		u = usuarioService.buscaPorId(usuarioService.salvar(u));
		System.out
				.println(String.format("Usuario. login: %s ; cpf: %s ; id: %s .", u.getLogin(), u.getCpf(), u.getId()));
		assertTrue(u.getId() > 0);
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

		receita.setContaOrigem(contaOrigem);
		receita.setPlano(PlanoConta.RECEITA);
		receita.setValor(100.0);

		receita = transacaoService.buscaPorId(transacaoService.salvar(receita));

		// Ver se o Gleyson consegue dar uma ajuda pq essa linha abaixo não está
		// funcionando.
		// Aparentemente no banco está alterando o saldo da conta, mas a busca do JPA
		// não está
		// retornando o objeto atualizado

		assertEquals(receita.getContaOrigem().getSaldo(), 100.0);

		despesa.setContaOrigem(contaOrigem);
		despesa.setPlano(PlanoConta.DESPESA);
		despesa.setValor(-25.0);

		despesa = transacaoService.buscaPorId(transacaoService.salvar(despesa));

		assertEquals(despesa.getContaOrigem().getSaldo(), 75.0);

		transferencia.setContaOrigem(contaOrigem);
		transferencia.setContaDestino(contaDestino);
		transferencia.setPlano(PlanoConta.TRANSFERENCIA);
		transferencia.setValor(30.0);

		transferencia = transacaoService.buscaPorId(transacaoService.salvar(transferencia));

		assertEquals(transferencia.getContaOrigem().getSaldo(), 45.0);
		assertEquals(transferencia.getContaDestino().getSaldo(), 30.0);

	}

}
