package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.DefaultRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

public class UsuarioServiceImpl implements DefaultService<Usuario> {

	private DefaultRepository<Usuario> repository;

	private ContaServiceOperations contaOperations;

	public UsuarioServiceImpl() {
		repository = new UsuarioRepository();
		contaOperations = new ContaServiceOperationsImpl();
	}

	@Override
	public int salvar(Usuario usuario) {
		// validar objeto
		validar(usuario);
		
		// persistir objeto
		int id = repository.salvar(usuario);
		
		// ações adjacentes a criação
		contaOperations.criar(id);
		
		return id;
	}

	private boolean validar(Usuario usuario) {

		return true;
	}

	@Override
	public void alterar(int id, Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Usuario buscaPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
