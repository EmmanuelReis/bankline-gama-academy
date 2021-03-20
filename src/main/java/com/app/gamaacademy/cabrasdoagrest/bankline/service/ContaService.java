package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;

public interface ContaService {

	public Long criar(Integer idUsuario);

	public Long salvar(Conta conta);

	public Conta obter(Long numero);

	public Conta obterContaDeUsuario(Integer idUsuario);

	public ExtratoDTO extrato(Long numero) throws Exception;

	public ExtratoDTO extrato(LocalDate initialDate);

	public ExtratoDTO extrato(LocalDate initialDate, LocalDate endData);

}
