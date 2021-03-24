package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.ErrorCode;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.PlanoContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.TransacaoRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository contaRepo;

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private PlanoContaRepository planoRepo;

	@Autowired
	private TransacaoRepository transRepo;

	@Override
	public Long criar(Integer idUsuario) {
		Conta conta = new Conta();
		Usuario user = userRepo.findById(idUsuario).orElse(null);
		List<PlanoConta> planoList = new ArrayList<>();

		conta.setUsuario(user);

		Arrays.asList(TipoOperacao.values()).forEach(t -> planoList.add(new PlanoConta(0, t.name(), t, user)));

		planoRepo.saveAll(planoList);

		return contaRepo.save(conta).getNumero();
	}

	@Override
	public ExtratoDTO extrato(Long numero, LocalDate dtInicio, LocalDate dtFim) throws BanklineBusinessException {
		ExtratoDTO ret = new ExtratoDTO();

		Conta conta = contaRepo.findById(numero).orElse(null);

		if (conta == null)
			throw new BanklineBusinessException(ErrorCode.E0009, "Conta", "numero", numero.toString());

		List<Transacao> transacoes = null;

		String dtInicioFormated = dtInicio != null
				? dtInicio.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				: LocalDate.MIN.atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		String dtFimFormated = dtFim != null ? dtFim.atTime(23, 59, 59).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				: LocalDate.now().atTime(23, 59, 59).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		transacoes = transRepo.obterExtrato(numero, dtInicioFormated, dtFimFormated);

		transacoes.forEach(t -> ret.getTransacoes().add(Mapper.convertTransacaoEntityToDto(t)));

		transacoes.sort((d1, d2) -> d1.getData().compareTo(d2.getData()));
		ret.setDtInicio(
				dtInicio != null ? dtInicio : !transacoes.isEmpty() ? transacoes.get(0).getData().toLocalDate() : null);
		ret.setDtFim(dtFim != null ? dtFim
				: !transacoes.isEmpty() ? transacoes.get(transacoes.size() - 1).getData().toLocalDate() : null);
		ret.setSaldoAtual(conta.getSaldo());
		ret.setSaldoPeriodo(transacoes.stream().mapToDouble(p -> p.getValor()).reduce(0, (s, e) -> s + e));

		return ret;
	}

	@Override
	public ContaDTO obter(Long numero) {
		Conta entity = contaRepo.findById(numero).orElse(null);
		if (entity == null)
			return null;
		ContaDTO ret = Mapper.convertContaEntityToDto(entity);
		ret.setUsuario(Mapper.convertUsuarioEntityToUsuarioSimplesDto(entity.getUsuario()));
		return ret;
	}

}
