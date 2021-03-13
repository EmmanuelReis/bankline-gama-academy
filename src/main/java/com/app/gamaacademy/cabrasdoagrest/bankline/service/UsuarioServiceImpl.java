package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioDAO;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioDAOImpl;

public class UsuarioServiceImpl implements DefaultService<Usuario> {

	@Autowired
	private DefaultRepository<Usuario> repository;

	@Autowired
	private ContaServiceOperations contaOperations;

	/*
	 * public UsuarioServiceImpl() { repository = new UsuarioRepository();
	 * contaOperations = new ContaServiceOperationsImpl(); }
	 */

	@Override
	public int salvar(Usuario usuario) throws Exception {
		Usuario dadoPersistido = dao.buscaPorId(usuario.getId());
		boolean isUpdate = dadoPersistido != null;
		int idUsuario = isUpdate ? dadoPersistido.getId() : 0;
		int idConta = 0;

		/*
		 * try {
		 * 
		 * validar(usuario); } catch (Exception e) { e.printStackTrace(); }
		 */

		// persistir objeto
		try {
			if (isUpdate)
				dao.alterar(usuario.getId(), usuario);
			else
				idUsuario = dao.salvar(usuario);

			// ações adjacentes a criação
			// idConta = contaOperations.criar(idUsuario);

		} catch (Exception ex) {
			// Criou um usuário mas não criou a conta
			/*
			 * if (!isUpdate && idUsuario >= 0 && idConta <= 0) dao.excluir(idUsuario);
			 */
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

		return idUsuario;
	}

	private boolean validar(Usuario usuario) throws Exception {
		if (usuario == null)
			throw new NullPointerException("Usuario nao pode ser nulo");

		if (usuario.getLogin() == null || usuario.getLogin().isEmpty())
			throw new Exception("Login precisa ser informado");
		if (usuario.getLogin().length() > MAX_LENGTH_LOGIN)
			throw new Exception(
					String.format("Login maior que %s caracteres: %s", MAX_LENGTH_LOGIN, usuario.getLogin()));

		if (usuario.getCpf() == null || usuario.getCpf().isEmpty())
			throw new Exception("Cpf precisa ser informado");

		if (!dao.validaLoginCpfUnicos(usuario.getId(), usuario.getLogin(), usuario.getCpf()))
			throw new Exception(
					String.format("Já existe um usuário com o mesmo login ou cpf informado. cpf: %s, login: %s",
							usuario.getCpf(), usuario.getLogin()));

		/*
		 * Usuario checaLogin = dao.buscaPorLogin(usuario.getLogin()); if (checaLogin !=
		 * null && checaLogin.getId() != checaLogin.getId()) throw new Exception(
		 * String.format("Login informado já está sendo utilizado por outro usuário: %s"
		 * , usuario.getLogin()));
		 * 
		 * Usuario checaCpf = dao.buscaPorCpf(null);
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
		return dao.buscaPorId(id);
	}

	public Usuario buscaPorLogin(String login) {
		return dao.buscaPorLogin(login);
	}

	public Usuario buscaPorCpf(String cpf) {
		return dao.buscaPorCpf(cpf);
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na INSERCAO
	 */
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return dao.validaLoginCpfUnicos(login, cpf);
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na ALTERACAO
	 */
	public boolean validaLoginCpfUnicos(int id, String login, String cpf) {
		return dao.validaLoginCpfUnicos(id, login, cpf);
	}

	@Override
	public List<Usuario> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
