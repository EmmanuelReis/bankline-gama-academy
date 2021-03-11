package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public interface UsuarioQuery {

	public Usuario buscaPorLogin(String login);

	public Usuario buscaPorCpf(String cpf);
	
	public boolean validaLoginCpfUnicos(int id, String login, String cpf);

	public List<Usuario> buscaPorNome(String nome);

	public List<Usuario> obterTodos();

}
