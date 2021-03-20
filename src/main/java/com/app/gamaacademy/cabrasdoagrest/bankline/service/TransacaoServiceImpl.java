package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.ArrayList;
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
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

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

		entity.getContaOrigem().setSaldo(entity.getValor());

		if (entity.getPlanoConta().getTipo().equals(TipoOperacao.TRANSFERENCIA)) {
			entity.setContaDestino(contaRepo.findById(entity.getContaDestino().getNumero()).get());

			/* Criando Transação de Receita na conta destino, para fins de extrato */
			PlanoConta pc = preencherPC(entity.getContaDestino().getUsuario().getId(), 0, "TRANSFERENCIA",
					TipoOperacao.RECEITA);

			transDest = new Transacao();
			transDest.setContaOrigem(entity.getContaDestino());
			transDest.setPlanoConta(pc);
			transDest.setValor(entity.getValor());
			transDest.setData(entity.getData());

			entity.getContaDestino().setSaldo(transDest.getValor());
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

		if (entity.getValor() == 0)
			throw new Exception("Valor precisa ser diferente de zero");

		Usuario usuario = contaOrigem.getUsuario();
		PlanoConta plano = entity.getPlanoConta();
		if (plano == null)
			throw new Exception("Plano de conta dever ser informado.");
		else if (plano.getTipo() == null || plano.getTipo().getCodigo() == null)
			throw new Exception("Valor do Tipo de Operação não é válido.");
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
		PlanoConta result = null;
		List<PlanoConta> listPC = new ArrayList<>();
		Usuario u = usuarioService.encontrarUsuarioDB(idUsuario);

		usuarioService.obterPlanoContas(idUsuario).forEach(p -> listPC.add(Mapper.convertPlanoContaDtoToEntity(p)));

		PlanoConta defaultPC = new PlanoConta();
		defaultPC.setTipo(tipo);
		defaultPC.setNome(idPC <= 0 && StringUtils.isBlank(nomePC) ? tipo.name() : nomePC);

		if (idPC > 0)
			result = listPC.stream().filter(x -> x.getId().equals(idPC)).findFirst().orElse(defaultPC);
		else
			result = listPC.stream().filter(x -> x.getNome().equals(defaultPC.getNome())).findFirst().orElse(defaultPC);

		result.setUsuario(u);

		return result;
	}

	private PlanoConta obterPC(Integer idUsuario, Integer idPC, String nomePC) {
		List<PlanoConta> listPC = new ArrayList<>();

		usuarioService.obterPlanoContas(idUsuario).forEach(p -> listPC.add(Mapper.convertPlanoContaDtoToEntity(p)));

		if (idPC > 0)
			return listPC.stream().filter(x -> x.getId().equals(idPC)).findFirst().orElse(null);

		return listPC.stream().filter(x -> x.getNome().equals(nomePC)).findFirst().orElse(null);
	}

}
