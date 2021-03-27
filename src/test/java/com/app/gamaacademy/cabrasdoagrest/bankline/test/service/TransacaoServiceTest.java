package com.app.gamaacademy.cabrasdoagrest.bankline.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.ErrorCode;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioService;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.ContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.PlanoContaBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.TransacaoBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.UsuarioBuilder;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TransacaoServiceTest {

	@Autowired
	private TransacaoServiceImpl service;

	@Autowired
	private TransacaoBuilder umaTransacao;

	@Autowired
	private ContaBuilder umaConta;

	@Autowired
	private PlanoContaBuilder umPlano;

	@Autowired
	private UsuarioBuilder umUsuario;

	@MockBean
	private TransacaoRepository repositoryMock;

	@MockBean
	private ContaRepository contaRepositoryMock;

	@MockBean
	private UsuarioService usuarioServiceMock;

	@BeforeEach
	public void reset() {
		umaTransacao.inicial();
		umaConta.inicial();
		// when(repositoryMock.findByLoginOrCpfEquals(any(String.class),
		// any(String.class))).thenReturn(null);
	}

	@Test
	@Order(1)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação nula")
	public void criandoTransacaoNula() {
		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			service.salvar(null);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0001);
	}

	@Test
	@Order(2)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação sem conta origem")
	public void criandoTransacaoSemContaOrigem() {
		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			service.salvar(umaTransacao.comValor(10.0).buildDto());
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0002);
		assertEquals(exception.getProperty(), "contaOrigem");
	}

	@Test
	@Order(3)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação conta origem inexistente")
	public void criandoTransacaoComContaOrigemInexistente() {
		Conta conta = umaConta.build();

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.daConta(conta).comValor(10.0).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0003);
		assertEquals(exception.getProperty(), "contaOrigem");
	}

	@Test
	@Order(4)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação com valor menor igual a zero")
	public void criandoTransacaoComValorZero() {
		Conta conta = umaConta.build();

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.daConta(conta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0004);
		assertEquals(exception.getProperty(), "valor");
	}

	@Test
	@Order(5)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação com plano de conta nulo")
	public void criandoTransacaoComPlanoNulo() {
		Conta conta = umaConta.build();

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.comValor(10.0).comPlano(null).daConta(conta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0002);
		assertEquals(exception.getProperty(), "plano");
	}

	@Test
	@Order(6)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação com plano de conta inválido")
	public void criandoTransacaoComPlanoInvalido() {
		Conta conta = umaConta.build();

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.comValor(10.0).comPlano(umPlano.build()).daConta(conta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0002);
		assertEquals(exception.getProperty(), "tipo");
	}

	@Test
	@Order(7)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação com plano de conta não pertencente ao usuário")
	public void criandoTransacaoComPlanoNaoPertencenteAoUsuario() {
		Conta conta = umaConta.doUsuario(umUsuario.comId().build()).build();
		List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));
		when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos);

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			PlanoConta planoConta = umPlano.comId(1).comTipo(TipoOperacao.RECEITA).build();

			TransacaoDTO transacao = umaTransacao.comValor(10.0).comPlano(planoConta).daConta(conta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0003);
		assertEquals(exception.getProperty(), "tipo");
	}

	/*
	 * @Test
	 * 
	 * @Order(8)
	 * 
	 * @DisplayName("Deve lançar uma exceção ao tentar fazer uma transação com plano de conta com nome inválido"
	 * ) public void criandoTransacaoComPlanoDeNomeInvalido() { Conta conta =
	 * umaConta.doUsuario(umUsuario.comId().build()).build(); PlanoConta planoConta
	 * = umPlano.comId(1).comNome("Luz").comTipo(TipoOperacao.DESPESA).build();
	 * 
	 * List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();
	 * listaPlanos.add(umPlano.buildDto());
	 * 
	 * when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.
	 * Optional.of(conta));
	 * when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos)
	 * .thenReturn(new ArrayList<PlanoContaDTO>());
	 * 
	 * Throwable exception = assertThrows(DataRetrievalFailureException.class, () ->
	 * { TransacaoDTO transacao =
	 * umaTransacao.comValor(-10.0).daConta(conta).comPlano(planoConta).buildDto();
	 * 
	 * service.salvar(transacao); });
	 * 
	 * String mensagemRecebida = exception.getMessage();
	 * System.out.println(mensagemRecebida); String mensagemEsperada =
	 * "Nome do Plano de conta não está cadastrado para o usuário";
	 * 
	 * assertTrue(mensagemRecebida.contains(mensagemEsperada)); }
	 */

	@Test
	@Order(9)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação do tipo transferência com conta destino nula")
	public void criandoTransacaoTransferenciaContaDestinoNula() {
		Conta conta = umaConta.doUsuario(umUsuario.comId().build()).build();
		PlanoConta planoConta = umPlano.comId(1).comNome("PIX").comTipo(TipoOperacao.TRANSFERENCIA).build();

		List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();
		listaPlanos.add(umPlano.buildDto());

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));
		when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos);

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.comValor(-10.0).daConta(conta).comPlano(planoConta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0002);
		assertEquals(exception.getProperty(), "contaDestino");
	}

	@Test
	@Order(10)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação do tipo transferência com conta destino igual a conta origem")
	public void criandoTransacaoTransferenciaContaDestinoIgualContaOrigem() {
		Conta conta = umaConta.doUsuario(umUsuario.comId().build()).build();
		PlanoConta planoConta = umPlano.comId(1).comNome("PIX").comTipo(TipoOperacao.TRANSFERENCIA).build();

		List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();
		listaPlanos.add(umPlano.buildDto());

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta));
		when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos);

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			TransacaoDTO transacao = umaTransacao.comValor(-10.0).daConta(conta).paraConta(conta).comPlano(planoConta)
					.buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0005);
	}

	@Test
	@Order(11)
	@DisplayName("Deve lançar uma exceção ao tentar fazer uma transação do tipo transferência com conta destino não existente")
	public void criandoTransacaoTransferenciaContaDestinoaoExistente() {
		Conta conta = umaConta.doUsuario(umUsuario.comId().build()).build();
		PlanoConta planoConta = umPlano.comId(1).comNome("PIX").comTipo(TipoOperacao.TRANSFERENCIA).build();

		List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();
		listaPlanos.add(umPlano.buildDto());

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta))
				.thenReturn(java.util.Optional.empty());
		when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos);

		BanklineBusinessException exception = assertThrows(BanklineBusinessException.class, () -> {
			umaConta.inicial();
			Conta contaDestino = umaConta.doUsuario(null).build();

			TransacaoDTO transacao = umaTransacao.comValor(-10.0).daConta(conta).paraConta(contaDestino)
					.comPlano(planoConta).buildDto();

			service.salvar(transacao);
		});

		assertEquals(exception.getErrorCode(), ErrorCode.E0003);
		assertEquals(exception.getProperty(), "contaDestino");
	}

	@Test
	@Order(12)
	@DisplayName("Deve retornar o id da transação em caso de sucesso")
	public void criandoTransacaoValida() throws Exception {
		Conta conta = umaConta.doUsuario(umUsuario.comId().build()).build();
		umaConta.inicial();
		Conta contaDestino = umaConta.doUsuario(umUsuario.comId().build()).build();
		PlanoConta planoConta = umPlano.comId(1).comNome("PIX").comTipo(TipoOperacao.TRANSFERENCIA).build();

		List<PlanoContaDTO> listaPlanos = new ArrayList<PlanoContaDTO>();
		listaPlanos.add(umPlano.buildDto());

		when(contaRepositoryMock.findById(any(Long.class))).thenReturn(java.util.Optional.of(conta))
				.thenReturn(java.util.Optional.of(contaDestino));
		when(usuarioServiceMock.obterPlanoContas(anyInt())).thenReturn(listaPlanos);

		Transacao transacao = umaTransacao.comId(1).comValor(-10.0).daConta(conta).paraConta(contaDestino)
				.comPlano(planoConta).build();

		when(repositoryMock.save(any(Transacao.class))).thenReturn(transacao);

		Integer idRecebido = service.salvar(Mapper.convertTransacaoEntityToDto(transacao));
		Integer idEsperado = transacao.getId();

		assertEquals(idEsperado, idRecebido);
	}
}
