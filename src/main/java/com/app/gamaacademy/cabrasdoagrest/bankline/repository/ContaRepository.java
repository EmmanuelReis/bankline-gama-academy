package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;

public class ContaRepository implements DefaultRepository<Conta, Long> {

	protected static EntityManager em = Persistence.createEntityManagerFactory("PU_BANKLINE").createEntityManager();

	@Override
	public List<Conta> obterTodos() {
		return em.createQuery("SELECT c FROM Conta c", Conta.class).getResultList();
	}

	@Override
	public Long salvar(Conta entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Long id, Conta entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Conta buscaPorId(Long id) {
		return em.find(Conta.class, id);
	}

}
