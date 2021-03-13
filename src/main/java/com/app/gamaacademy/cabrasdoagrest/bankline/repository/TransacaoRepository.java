package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public class TransacaoRepository implements DefaultRepository<Transacao, Integer> {

	protected static EntityManager em = Persistence.createEntityManagerFactory("PU_BANKLINE").createEntityManager();

	@Override
	public Integer salvar(Transacao transacao) {
		em.getTransaction().begin();

		transacao.setContaOrigem(em.find(Conta.class, transacao.getContaOrigem().getNumero()));

		double valor = transacao.getValor();
		if (!transacao.getPlanoConta().getTipo().equals(TipoPlanoConta.TRANSFERENCIA)) {
			transacao.getContaOrigem().setSaldo(valor);
		} else {
			transacao.setContaDestino(em.find(Conta.class, transacao.getContaDestino().getNumero()));
			transacao.getContaOrigem().setSaldo(valor * -1);
			transacao.getContaDestino().setSaldo(valor);
		}

		em.persist(transacao);
		em.getTransaction().commit();

		return transacao.getId();
	}

	@Override
	public void alterar(Integer id, Transacao entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Transacao buscaPorId(Integer id) {
		return em.find(Transacao.class, id);
	}

	@Override
	public List<Transacao> obterTodos() {
		return em.createQuery("SELECT t FROM Transacao t", Transacao.class).getResultList();
	}

}
