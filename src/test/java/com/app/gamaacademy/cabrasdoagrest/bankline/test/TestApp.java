package com.app.gamaacademy.cabrasdoagrest.bankline.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoContaEnum;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoContaEnum;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

public class TestApp {

	private UsuarioRepository userRepo = new UsuarioRepository();

	@Test
	@DisplayName("Testando criação e interação das classes do modelo")
	public void testaEstruturaModelo() {
		Usuario u = new Usuario();
		u.setId(1);
		u.setCpf("12345678901");
		u.setLogin("gabriel");
		u.setNome("gabriel albuquerque");
		u.setSenha("123");

		Conta c = new Conta();
		c.setId(1);
		c.setNumero(1101);
		c.setSaldo(1500.32);
		c.setTipo(TipoContaEnum.BANCO.toObj());
		c.setUsuario(u);

		Transacao t = new Transacao();
		t.setData(LocalDate.now());
		t.setContaOrigem(c);
		t.setId(1);
		t.setPlano(PlanoContaEnum.RECEITA.toObj());
		t.setValor(200.72);

		System.out.println(t);
	}

	@Test
	@DisplayName("Testando salvar novo usuário e verificar se o id está sendo retornado")
	public void criarNovoUsuario() {
		Usuario u = new Usuario();
		u.setCpf("12345678901");
		u.setLogin("gabriel");
		u.setNome("gabriel albuquerque");
		u.setSenha("123");

		int id = userRepo.salvar(u);
		assertTrue(id > 0);
	}

}
