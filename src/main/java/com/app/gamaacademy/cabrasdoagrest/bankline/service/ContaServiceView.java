package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;
import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface ContaServiceView {

	public List<PlanoConta> obterPlanoContas();
	
	public Conta obterContaDeUsuario(int idUsuario);

	public List<Transacao> extrato();

	public List<Transacao> extrato(LocalDate initialDate);

	public List<Transacao> extrato(LocalDate initialDate, LocalDate endData);

}
