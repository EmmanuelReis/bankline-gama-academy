package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;
import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface ContaService {

	public Long criar(Integer idUsuario);
	
	public Long salvar(Conta conta);

	public Conta obter(Long numero);

	public Conta obterContaDeUsuario(Integer idUsuario);

	public List<Transacao> extrato();

	public List<Transacao> extrato(LocalDate initialDate);

	public List<Transacao> extrato(LocalDate initialDate, LocalDate endData);

}
