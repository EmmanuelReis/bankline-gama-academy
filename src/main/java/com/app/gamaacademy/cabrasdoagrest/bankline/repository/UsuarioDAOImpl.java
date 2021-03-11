package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioDAOImpl extends UsuarioDAO {

	protected static EntityManager em = Persistence.createEntityManagerFactory("PU_BANKLINE").createEntityManager();

	@Override
	public int salvar(Usuario entity) {
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		return entity.getId();
	}

	@Override
	public void alterar(int id, Usuario entity) throws Exception {
		Usuario u = buscaPorId(id);

		if (u == null)
			throw new Exception("Usuario nao encontrado.");

		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();

	}

	@Override
	public void excluir(int id) {
		Usuario entity = buscaPorId(id);

		if (entity != null) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}

	}

	@Override
	public Usuario buscaPorId(int id) {
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
