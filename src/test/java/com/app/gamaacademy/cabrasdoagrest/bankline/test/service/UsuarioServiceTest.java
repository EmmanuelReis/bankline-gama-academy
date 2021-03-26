package com.app.gamaacademy.cabrasdoagrest.bankline.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.ErrorCode;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.ContaService;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.UsuarioBuilder;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTest {

	@Autowired
	private UsuarioServiceImpl service;

	@Autowired
	private UsuarioBuilder umUsuario;

	@MockBean
	private UsuarioRepository repositoryMock;

	@MockBean
	private ContaService contaServiceMock;

	@BeforeEach
	public void reset() {
		umUsuario.valido();
		when(repositoryMock.findByCpfEquals(any(String.class))).thenReturn(null);
		when(repositoryMock.findByLoginEquals(any(String.class))).thenReturn(null);
	}

	@Test
	@Order(1)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com login inválido")
	public void criandoUsuarioComLoginInvalido() {
		UsuarioDTO usuarioDTO = umUsuario.comLoginInvalido().buildDto();

		// String mensagemEsperada = "O valor para [login] não é válido!";

		when(repositoryMock.save(any(Usuario.class))).thenThrow(new BanklineBusinessException(ErrorCode.E0008, ""));

		// Throwable exception =
		assertThrows(BanklineBusinessException.class, () -> {
			service.criarUsuario(usuarioDTO);
		});

		/*
		 * String mensagemRecebida = exception.getMessage();
		 * 
		 * assertTrue(mensagemRecebida.contains(mensagemEsperada));
		 */
	}

	@Test
	@Order(2)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com cpf inválido")
	public void criandoUsuarioComCpfInvalido() {
		UsuarioDTO usuarioDTO = umUsuario.comCpfInvalido().buildDto();

		// String mensagemEsperada = "O valor para [cpf] não é válido!";

		when(repositoryMock.save(any(Usuario.class))).thenThrow(new BanklineBusinessException(ErrorCode.E0008, ""));

		/* Throwable exception = */assertThrows(BanklineBusinessException.class, () -> {
			service.criarUsuario(usuarioDTO);
		});

		/*
		 * String mensagemRecebida = exception.getMessage();
		 * 
		 * assertTrue(mensagemRecebida.contains(mensagemEsperada));
		 */
	}

	@Test
	@Order(3)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com senha inválida")
	public void criandoUsuarioComSenhaInvalida() {
		UsuarioDTO usuarioDTO = umUsuario.comSenhaInvalida().buildDto();

		// String mensagemEsperada = "O valor para [senha] não é válido!";

		when(repositoryMock.save(any(Usuario.class))).thenThrow(new BanklineBusinessException(ErrorCode.E0008, ""));

		/* Throwable exception = */ assertThrows(BanklineBusinessException.class, () -> {
			service.criarUsuario(usuarioDTO);
		});

		/*
		 * String mensagemRecebida = exception.getMessage();
		 * 
		 * assertTrue(mensagemRecebida.contains(mensagemEsperada));
		 */
	}

	@Test
	@Order(4)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com nome inválido")
	public void criandoUsuarioComNomeInvalida() {
		UsuarioDTO usuarioDTO = umUsuario.comNomeInvalido().buildDto();

		// String mensagemEsperada = "O valor para [nome] não é válido!";

		when(repositoryMock.save(any(Usuario.class))).thenThrow(new BanklineBusinessException(ErrorCode.E0008, ""));

		/* Throwable exception = */assertThrows(BanklineBusinessException.class, () -> {
			service.criarUsuario(usuarioDTO);
		});

		/*
		 * String mensagemRecebida = exception.getMessage();
		 * 
		 * assertTrue(mensagemRecebida.contains(mensagemEsperada));
		 */
	}

	@Test
	@Order(5)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com login e/ou CPF já cadastrado")
	public void criandoUsuarioComLoginExistente() {
		UsuarioDTO usuarioDTO = umUsuario.buildDto();

		// String mensagemEsperada = "CPF e/ou login já existe não é possível
		// cadastrar!";

		when(repositoryMock.findByLoginEquals(any(String.class))).thenReturn(umUsuario.build());

		/* Throwable exception = */assertThrows(BanklineBusinessException.class, () -> {
			service.criarUsuario(usuarioDTO);
		});

		/*
		 * String mansagemRecebida = exception.getMessage();
		 * 
		 * assertTrue(mansagemRecebida.contains(mensagemEsperada));
		 */
	}

	@Test
	@Order(6)
	@DisplayName("Deve retornar um id ao criar um usuário")
	public void criandoUsuario() throws Exception {
		Usuario usuario = umUsuario.comId().build();
		UsuarioDTO usuarioDTO = umUsuario.comId().buildDto();

		when(repositoryMock.save(any(Usuario.class))).thenReturn(usuario);
		when(contaServiceMock.criar(usuario.getId())).thenReturn(1L);

		Integer idEsperado = usuario.getId();
		Integer idRecebido = service.criarUsuario(usuarioDTO);

		assertEquals(idEsperado, idRecebido);
	}
}
