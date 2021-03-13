package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;

public class TransacaoServiceImpl implements DefaultService<Transacao> {

	protected TransacaoRepository transRepo = new TransacaoRepository();
	protected ContaRepository contaRepo = new ContaRepository();

	@Override
	public int salvar(Transacao entity) throws Exception {

		try {
			validar(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro na validação" + e.getMessage());
		}

		transRepo.salvar(entity);

		return entity.getId();
	}

	private void validar(Transacao entity) throws Exception {
		if (entity == null)
			throw new NullPointerException("transacao não pode ser nulo.");

		if (entity.getContaOrigem() == null || entity.getContaOrigem().getNumero() <= 0)
			throw new Exception("Conta origem não pode ser nula ou sem informar numero.");

		if (contaRepo.buscaPorId(entity.getContaOrigem().getNumero()) == null)
			throw new Exception("Conta origem informada não existe");

		if (entity.getPlano() == null)
			throw new Exception("Plano de Conta inexiste ou inválido.");

		if ((entity.getPlano().equals(PlanoConta.TRANSFERENCIA) || entity.getPlano().equals(PlanoConta.RECEITA))
				&& entity.getValor() <= 0)
			throw new Exception("Transações do plano de conta RECEITA ou TRANSFERENCIA precisam ter valor maior que 0");

		if (entity.getPlano().equals(PlanoConta.DESPESA) && entity.getValor() >= 0)
			throw new Exception("Transações do plano de conta DESPESA precisam ter valor menor que 0");

		if (entity.getPlano().equals(PlanoConta.TRANSFERENCIA)) {
			if (entity.getContaDestino() == null || entity.getContaDestino().getNumero() <= 0)
				throw new Exception(
						"Para transação de TRANSFERENCIA. Conta destino não pode ser nula ou sem informar numero.");

			if (contaRepo.buscaPorId(entity.getContaDestino().getNumero()) == null)
				throw new Exception("Conta destino informada não existe");
		}
	}

	/*
	 * private void fillMissingInfo(Transacao entity) { if() }
	 */

	@Override
	public void alterar(int id, Transacao usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Transacao buscaPorId(int id) {
		return transRepo.buscaPorId(id);
	}

	@Override
	public List<Transacao> obterTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
