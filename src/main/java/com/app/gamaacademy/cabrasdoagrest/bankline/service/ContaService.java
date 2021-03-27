package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;

public interface ContaService {

	public Long criar(Integer idUsuario);

	public ContaDTO obter(Long numero);

	public ExtratoDTO extrato(Long numero, LocalDate initialDate, LocalDate endData) throws BanklineBusinessException;

}
