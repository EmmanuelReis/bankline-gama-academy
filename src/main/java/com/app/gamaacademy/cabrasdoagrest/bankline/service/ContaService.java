package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.time.LocalDate;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;

public interface ContaService {

	public Long criar(Integer idUsuario);

	public ExtratoDTO extrato(Long numero, LocalDate initialDate, LocalDate endData) throws Exception;

}
