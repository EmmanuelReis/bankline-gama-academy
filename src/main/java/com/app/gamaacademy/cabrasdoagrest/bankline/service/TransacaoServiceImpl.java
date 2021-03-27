package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.ErrorCode;
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
	public Integer salvar(TransacaoDTO dto) throws BanklineBusinessException {

		Transacao transDest = null;

		validar(dto);

		Transacao entity = Mapper.convertTransacaoDtoToEntity(dto);

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

	private void validar(TransacaoDTO entity) throws BanklineBusinessException {
		if (entity == null)
			throw new BanklineBusinessException(ErrorCode.E0001, "transacao", null, null);

		if (entity.getValor() <= 0)
			throw new BanklineBusinessException(ErrorCode.E0004, "transacao", "valor", entity.getValor().toString());

		if (entity.getData() == null)
			throw new BanklineBusinessException(ErrorCode.E0002, "transacao", "data", null);

		if (entity.getContaOrigem() == null)
			throw new BanklineBusinessException(ErrorCode.E0002, "transacao", "contaOrigem", null);
		if (entity.getContaOrigem().getNumero() <= 0)
			throw new BanklineBusinessException(ErrorCode.E0004, "transacao", "contaOrigem",
					entity.getContaOrigem().getNumero().toString());

		Conta contaOrigem = contaRepo.findById(entity.getContaOrigem().getNumero()).orElse(null);

		if (contaOrigem == null)
			throw new BanklineBusinessException(ErrorCode.E0003, "transacao", "contaOrigem",
					entity.getContaOrigem().getNumero().toString());

		Usuario usuario = contaOrigem.getUsuario();
		PlanoContaDTO plano = entity.getPlanoConta();

		if (plano == null)
			throw new BanklineBusinessException(ErrorCode.E0002, "transacao", "plano", null);
		else if (plano.getTipo() == null)
			throw new BanklineBusinessException(ErrorCode.E0002, "plano", "tipo", null);
		else if (plano.getTipo().getCodigo() == null)
			throw new BanklineBusinessException(ErrorCode.E0004, "transacao", "tipo", plano.getTipo().name());
		else if (plano.getId() > 0 && usuarioService.obterPlanoContas(usuario.getId()).stream()
				.filter(x -> x.getId() == plano.getId()).findFirst().orElse(null) == null)
			throw new BanklineBusinessException(ErrorCode.E0003, "plano", "tipo",
					entity.getPlanoConta().getId().toString());
		/*
		 * else if (StringUtils.isNotBlank(plano.getNome()) && obterPC(usuario.getId(),
		 * 0, plano.getNome()) == null) { throw new
		 * DataRetrievalFailureException("Nome do Plano de conta não está cadastrado para o usuário"
		 * ); }
		 */

		if (entity.getPlanoConta().getTipo().equals(TipoOperacao.TRANSFERENCIA)) {
			if (entity.getContaDestino() == null)
				throw new BanklineBusinessException(ErrorCode.E0002, "transacao", "contaDestino", null);
			if (entity.getContaDestino().getNumero() <= 0)
				throw new BanklineBusinessException(ErrorCode.E0004, "transacao", "contaOrigem",
						entity.getContaDestino().getNumero().toString());

			if (entity.getContaDestino().getNumero().equals(entity.getContaOrigem().getNumero()))
				throw new BanklineBusinessException(ErrorCode.E0005);

			if (contaRepo.findById(entity.getContaDestino().getNumero()).orElse(null) == null)
				throw new BanklineBusinessException(ErrorCode.E0003, "transacao", "contaDestino",
						entity.getContaDestino().getNumero().toString());
		}
	}

	@Override
	public TransacaoDTO obter(Integer id) {
		return Mapper.convertTransacaoEntityToDto(transRepo.findById(id).orElse(null));
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

	/*
	 * private PlanoConta obterPC(Integer idUsuario, Integer idPC, String nomePC) {
	 * List<PlanoConta> listPC = new ArrayList<>();
	 * 
	 * usuarioService.obterPlanoContas(idUsuario).forEach(p ->
	 * listPC.add(Mapper.convertPlanoContaDtoToEntity(p)));
	 * 
	 * if (idPC > 0) return listPC.stream().filter(x ->
	 * x.getId().equals(idPC)).findFirst().orElse(null);
	 * 
	 * return listPC.stream().filter(x ->
	 * x.getNome().equals(nomePC)).findFirst().orElse(null); }
	 */

}
