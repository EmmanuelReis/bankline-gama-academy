package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;

@Service
public class TransacaoServiceImpl implements TransacaoService {

	@Autowired
	protected TransacaoRepository transRepo;

	@Autowired
	protected ContaRepository contaRepo;

	@Override
	public Integer salvar(Transacao entity) throws Exception {

		try {
			validar(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro na validação" + e.getMessage());
		}

		entity.setContaOrigem(contaRepo.findById(entity.getContaOrigem().getNumero()).get());

		double valor = entity.getValor();
		if (!entity.getPlanoConta().getTipo().equals(TipoPlanoConta.TRANSFERENCIA)) {
			entity.getContaOrigem().setSaldo(valor);
		} else {
			entity.setContaDestino(contaRepo.findById(entity.getContaDestino().getNumero()).get());
			entity.getContaOrigem().setSaldo(valor * -1);
			entity.getContaDestino().setSaldo(valor);
		}

		transRepo.save(entity);

		return entity.getId();
	}

	private void validar(Transacao entity) throws Exception {
		if (entity == null)
			throw new NullPointerException("transacao não pode ser nulo.");

		if (entity.getContaOrigem() == null || entity.getContaOrigem().getNumero() <= 0)
			throw new Exception("Conta origem não pode ser nula ou sem informar numero.");

		if (contaRepo.findById(entity.getContaOrigem().getNumero()).orElse(null) == null)
			throw new Exception("Conta origem informada não existe");

		if (entity.getPlanoConta() == null)
			throw new Exception("Plano de Conta inexiste ou inválido.");

		if ((entity.getPlanoConta().getTipo().equals(TipoPlanoConta.TRANSFERENCIA)
				|| entity.getPlanoConta().getTipo().equals(TipoPlanoConta.RECEITA)) && entity.getValor() <= 0)
			throw new Exception("Transações do plano de conta RECEITA ou TRANSFERENCIA precisam ter valor maior que 0");

		if (entity.getPlanoConta().getTipo().equals(TipoPlanoConta.DESPESA) && entity.getValor() >= 0)
			throw new Exception("Transações do plano de conta DESPESA precisam ter valor menor que 0");

		if (entity.getPlanoConta().getTipo().equals(TipoPlanoConta.TRANSFERENCIA)) {
			if (entity.getContaDestino() == null || entity.getContaDestino().getNumero() <= 0)
				throw new Exception(
						"Para transação de TRANSFERENCIA. Conta destino não pode ser nula ou sem informar numero.");

			if (contaRepo.findById(entity.getContaDestino().getNumero()).orElse(null) == null)
				throw new Exception("Conta destino informada não existe");
		}
	}

	@Override
	public Transacao obter(Integer id) {
		return transRepo.findById(id).orElse(null);
	}

	@Override
	public List<Transacao> obterTodos(Long numConta) {
		return null;
	}

}
