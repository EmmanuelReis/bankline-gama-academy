package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioDAOImpl extends UsuarioDAO {

	private static EntityManager em;

	public UsuarioDAOImpl() {
		if (em == null) {
			em = Persistence.createEntityManagerFactory("PU_BANKLINE").createEntityManager();
		}
	}

	@Override
	public Integer salvar(Usuario entity) {
		em.getTransaction().begin();

		if (entity.getConta() == null) {
			Conta conta = new Conta();
			conta.setSaldo(0);
			conta.setUsuario(entity);

			entity.setConta(conta);
			em.persist(conta);
		}

		em.persist(entity);
		em.getTransaction().commit();
		return entity.getId();
	}

	@Override
	public void alterar(Integer id, Usuario entity) throws Exception {
		Usuario u = buscaPorId(id);

		if (u == null)
			throw new Exception("Usuario nao encontrado.");

		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();

	}

	@Override
	public void excluir(Integer id) {
		Usuario entity = buscaPorId(id);

		if (entity != null) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}

	}

	@Override
	public Usuario buscaPorId(Integer id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public Usuario buscaPorLogin(String login) {
		List<Usuario> result = em.createQuery("SELECT u FROM Usuario u WHERE u.login = '" + login + "'", Usuario.class)
				.getResultList();
		if (!result.isEmpty())
			return result.get(0);
		return null;
	}

	@Override
	public List<Usuario> buscaPorNome(String nome) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.nome like '%" + nome + "%'", Usuario.class)
				.getResultList();
	}

	@Override
	public List<Usuario> obterTodos() {
		return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
	}

	@Override
	public Usuario buscaPorCpf(String cpf) {
		List<Usuario> result = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf = '" + cpf + "'", Usuario.class)
				.getResultList();
		if (!result.isEmpty())
			return result.get(0);
		return null;
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na INSERÇÃO.
	 */
	@Override
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return validaLoginCpfUnicos(0, login, cpf);
	}

	/**
	 * Retorna true se o login e cpf informados forem válidos para criar um novo
	 * usuário. Utilizado na ATUALIZAÇÃO.
	 */
	@Override
	public boolean validaLoginCpfUnicos(int id, String login, String cpf) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("SELECT 1 FROM Usuario u WHERE u.cpf = '%s' OR u.login = '%s'", cpf, login));
			if (id > 0)
				sb.append(String.format("AND u.id <> %s", id));
			em.createNativeQuery(sb.toString()).getSingleResult();
		} catch (NoResultException nre) {
			return true;
		}
		return false;
	}

}
