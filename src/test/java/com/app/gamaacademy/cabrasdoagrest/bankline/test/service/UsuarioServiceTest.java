package com.app.gamaacademy.cabrasdoagrest.bankline.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.test.builders.UsuarioBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import ma.glasnost.orika.MapperFacade;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTest {

    @Autowired
	private UsuarioServiceImpl service;
    
    @Autowired
    private UsuarioBuilder umUsuario;

    @Autowired
	private MapperFacade mapper;

    @BeforeEach
    public void reset() {
        umUsuario.valido();
    }

    @Test
	@Order(1)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com login inválido")
	public void criandoUsuarioComLoginInvalido() {
		Usuario usuario = umUsuario.comLoginInvalido().build();

        Throwable exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            service.criarUsuario(mapper.map(usuario, UsuarioDTO.class));
        });

        String mensagemEsperada = "O valor para [login] não é válido!";
        String mansagemRecebida = exception.getMessage();

        assertTrue(mansagemRecebida.contains(mensagemEsperada));
	}

    @Test
	@Order(2)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com cpf inválido")
	public void criandoUsuarioComCpfInvalido() {
		Usuario usuario = umUsuario.comCpfInvalido().build();

        Throwable exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            service.criarUsuario(mapper.map(usuario, UsuarioDTO.class));
        });

        String mensagemEsperada = "O valor para [cpf] não é válido!";
        String mansagemRecebida = exception.getMessage();

        assertTrue(mansagemRecebida.contains(mensagemEsperada));
	}

    @Test
	@Order(3)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com senha inválida")
	public void criandoUsuarioComSenhaInvalida() {
		Usuario usuario = umUsuario.comSenhaInvalida().build();

        Throwable exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            service.criarUsuario(mapper.map(usuario, UsuarioDTO.class));
        });

        String mensagemEsperada = "O valor para [senha] não é válido!";
        String mansagemRecebida = exception.getMessage();

        assertTrue(mansagemRecebida.contains(mensagemEsperada));
	}

    @Test
	@Order(4)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com nome inválido")
	public void criandoUsuarioComNomeInvalida() {
		Usuario usuario = umUsuario.comNomeInvalido().build();

        Throwable exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            service.criarUsuario(mapper.map(usuario, UsuarioDTO.class));
        });

        String mensagemEsperada = "O valor para [nome] não é válido!";
        String mansagemRecebida = exception.getMessage();

        assertTrue(mansagemRecebida.contains(mensagemEsperada));
	}

    @Test
	@Order(5)
	@DisplayName("Deve lançar uma exceção ao tentar criar um usuário com login e/ou CPF já cadastrado")
	public void criandoUsuarioComLoginExistente() {
		Usuario usuario = umUsuario.build();
        UsuarioDTO usuarioDTO = mapper.map(usuario, UsuarioDTO.class);
        UsuarioServiceImpl usuarioService = mock(UsuarioServiceImpl.class);

        when(usuarioService.criarUsuario(usuarioDTO))
        .thenThrow(new DataIntegrityViolationException("CPF e/ou login já existe não é possível cadastrar!"));

        Throwable exception = assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.criarUsuario(usuarioDTO);
        });

        String mensagemEsperada = "CPF e/ou login já existe não é possível cadastrar!";
        String mansagemRecebida = exception.getMessage();

        assertTrue(mansagemRecebida.contains(mensagemEsperada));
	}

    @Test
	@Order(6)
	@DisplayName("Deve retornar um id ao criar um usuário")
	public void criandoUsuario() {
		Usuario usuario = umUsuario.comId().build();
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
			
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        service.setRepository(usuarioRepository);

        Integer idEsperado = usuario.getId();
        Integer idRecebido = service.criarUsuario(mapper.map(usuario, UsuarioDTO.class));

        assertEquals(idEsperado, idRecebido);
	}
}
