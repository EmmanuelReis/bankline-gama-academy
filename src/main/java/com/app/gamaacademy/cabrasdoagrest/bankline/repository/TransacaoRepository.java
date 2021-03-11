package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public class TransacaoRepository implements DefaultRepository<Transacao> {

	protected static EntityManager em = Persistence.createEntityManagerFactory("PU_BANKLINE").createEntityManager();

	@Override
	public int salvar(Transacao transacao) {
		em.getTransaction().begin();
		em.persist(transacao);
		em.getTransaction().commit();

		return transacao.getId();
	}

	@Override
	public void alterar(int id, Transacao entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Transacao buscaPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transacao> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
