package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoOperacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.PlanoContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository contaRepo;

	@Autowired
	private UsuarioRepository userRepo;

	@Autowired
	private PlanoContaRepository planoRepo;

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
	public Long salvar(Conta conta) {
		return contaRepo.save(conta).getNumero();
	}

	@Override
	public Conta obter(Long numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Conta obterContaDeUsuario(Integer idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transacao> extrato() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transacao> extrato(LocalDate initialDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transacao> extrato(LocalDate initialDate, LocalDate endData) {
		// TODO Auto-generated method stub
		return null;
	}

}
