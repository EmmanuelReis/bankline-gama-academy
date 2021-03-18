package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;

@Service
public class TransacaoServiceImpl implements TransacaoService {

	@Autowired
	private TransacaoRepository transRepo;

	@Autowired
	private ContaRepository contaRepo;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Integer salvar(Transacao entity) throws Exception {

		Transacao transDest = null;

		try {
			validar(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro na validação" + e.getMessage());
		}

		entity.setContaOrigem(contaRepo.findById(entity.getContaOrigem().getNumero()).get());

		int idUsuario = entity.getContaOrigem().getUsuario().getId();
		int idPC = entity.getPlanoConta().getId() == null ? 0 : entity.getPlanoConta().getId();

		entity.setPlanoConta(
				preencherPC(idUsuario, idPC, entity.getPlanoConta().getNome(), entity.getPlanoConta().getTipo()));

		double valor = entity.getValor() * entity.getPlanoConta().getTipo().getMultiplicador();
		if (!entity.getPlanoConta().getTipo().equals(TipoOperacao.TRANSFERENCIA)) {
			entity.getContaOrigem().setSaldo(valor);
		} else {
			entity.setContaDestino(contaRepo.findById(entity.getContaDestino().getNumero()).get());
			entity.getContaOrigem().setSaldo(valor);
			entity.getContaDestino().setSaldo(valor * -1);

			/* Criando Transação de Receita na conta destino, para fins de extrato */
			PlanoConta pc = new PlanoConta();
			pc.setNome("TRANSFERENCIA");
			pc.setTipo(TipoOperacao.RECEITA);

			transDest = new Transacao();
			transDest.setContaOrigem(entity.getContaDestino());
			transDest.setValor(entity.getValor());
			transDest.setPlanoConta(pc);
			transDest.setData(entity.getData());
		}

		try {
			entity = transRepo.save(entity);
			if (transDest != null)
				transRepo.save(transDest);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return entity.getId();
	}

	private void validar(Transacao entity) throws Exception {
		if (entity == null)
			throw new NullPointerException("transacao não pode ser nulo.");

		if (entity.getContaOrigem() == null || entity.getContaOrigem().getNumero() <= 0)
			throw new Exception("Conta origem não pode ser nula ou sem informar numero.");

		Conta contaOrigem = contaRepo.findById(entity.getContaOrigem().getNumero()).orElse(null);
		if (contaOrigem == null)
			throw new Exception("Conta origem informada não existe");

		if (entity.getValor() <= 0)
			throw new Exception("Valor precisa ser maior que zero");

		Usuario usuario = contaOrigem.getUsuario();
		PlanoConta plano = entity.getPlanoConta();
		if (plano == null)
			throw new Exception("Plano de conta dever ser informado.");
//		else if (!Stream.of(TipoOperacao.values()).anyMatch(p -> p.getCodigo().equals(plano.getTipo().getCodigo())))
		else if (plano.getTipo() == null || plano.getTipo().getCodigo() == null)
			throw new Exception("Valor do Tipo de Operação não é válido.");
		else if (plano.getId() <= 0 && StringUtils.isBlank(plano.getNome()))
			throw new Exception("Plano de conta deve ter informação de Id ou número");
		else if (plano.getId() > 0 && usuarioService.obterPlanoContas(usuario.getId()).stream()
				.filter(x -> x.getId() == plano.getId()).findFirst().orElse(null) == null)
			throw new Exception("Id informado não se refere a nenhum plano de conta do usuário da conta.");
		else if (StringUtils.isNotBlank(plano.getNome()) && obterPC(usuario.getId(), 0, plano.getNome()) == null
				&& plano.getTipo() == null) {
			throw new Exception(
					"Nome do Plano de conta não está cadastrado para o usuário e precisa de TipoPlanoConta válido.");
		}

		if (entity.getPlanoConta().getTipo().equals(TipoOperacao.TRANSFERENCIA))

		{
			if (entity.getContaDestino() == null || entity.getContaDestino().getNumero() <= 0)
				throw new Exception(
						"Para transação de TRANSFERENCIA. Conta destino não pode ser nula ou sem informar numero");

			if (entity.getContaDestino().getNumero().equals(entity.getContaOrigem().getNumero()))
				throw new Exception("Para transferência Conta destino tem de ser diferente da conta de origem");

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

	private PlanoConta preencherPC(Integer idUsuario, Integer idPC, String nomePC, TipoOperacao tipo) {
		List<PlanoConta> listPC = usuarioService.obterPlanoContas(idUsuario);

		PlanoConta defaultPC = new PlanoConta();
		defaultPC.setTipo(tipo);
		defaultPC.setNome(StringUtils.isBlank(nomePC) ? tipo.name() : nomePC);

		defaultPC.setUsuario(usuarioService.encontrarUsuario(idPC));

		if (idPC > 0)
			return listPC.stream().filter(x -> x.getId().equals(idPC)).findFirst().orElse(defaultPC);

		return listPC.stream().filter(x -> x.getNome().equals(nomePC)).findFirst().orElse(defaultPC);
	}

	private PlanoConta obterPC(Integer idUsuario, Integer idPC, String nomePC) {
		List<PlanoConta> listPC = usuarioService.obterPlanoContas(idUsuario);

		if (idPC > 0)
			return listPC.stream().filter(x -> x.getId().equals(idPC)).findFirst().orElse(null);

		return listPC.stream().filter(x -> x.getNome().equals(nomePC)).findFirst().orElse(null);
	}

}
