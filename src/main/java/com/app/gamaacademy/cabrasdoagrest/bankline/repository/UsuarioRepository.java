package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioRepository implements DefaultRepository<Usuario> {

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

	public Usuario buscaPorLogin(String nome) {
		return em.createQuery("SELECT u FROM Usuario u WHERE u.login = '" + nome + "'", Usuario.class).getResultList().get(0);
	}

	@Override
	public List<Usuario> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
