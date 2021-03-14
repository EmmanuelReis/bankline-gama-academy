package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository repository;

	@Override
	public Long salvar(Conta conta) {
		return repository.save(conta).getNumero();
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
