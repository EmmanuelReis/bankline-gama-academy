package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements DefaultService<Usuario> {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public int salvar(Usuario usuario) throws Exception {
		Usuario dadoPersistido = repository.findById(usuario.getId()).get();
		boolean isUpdate = dadoPersistido != null;
		int idUsuario = isUpdate ? dadoPersistido.getId() : 0;

		try {
			validar(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// persistir objeto
		idUsuario = repository.save(usuario).getId();

		return idUsuario;
	}

	private boolean validar(Usuario usuario) throws Exception {
		if (usuario == null)
			throw new NullPointerException("Usuario nao pode ser nulo");

		if (usuario.getLogin() == null || usuario.getLogin().isEmpty())
			throw new Exception("Login precisa ser informado");
		if (usuario.getLogin().length() > Usuario.LOGIN_MAX_LENGTH)
			throw new Exception(
					String.format("Login maior que %s caracteres: %s", Usuario.LOGIN_MAX_LENGTH, usuario.getLogin()));

		if (usuario.getCpf() == null || usuario.getCpf().isEmpty())
			throw new Exception("Cpf precisa ser informado");

		/*
		 * if (!dao.validaLoginCpfUnicos(usuario.getId(), usuario.getLogin(),
		 * usuario.getCpf())) throw new Exception( String.
		 * format("Já existe um usuário com o mesmo login ou cpf informado. cpf: %s, login: %s"
		 * , usuario.getCpf(), usuario.getLogin()));
		 */

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
		throw new NotImplementedException("buscaPorId");
	}

	public Usuario buscaPorLogin(String login) {
		throw new NotImplementedException("buscaPorLogin");
	}

	public Usuario buscaPorCpf(String cpf) {
		throw new NotImplementedException("buscaPorCpf");
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na INSERCAO
	 */
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return repository.validaLoginCpfUnicos(login, cpf);
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na ALTERACAO
	 */
	public boolean validaLoginCpfUnicos(int id, String login, String cpf) {
		throw new NotImplementedException("validaLoginCpfUnicos");
	}

	@Override
	public List<Usuario> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
