package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoService {

	TransacaoDTO obter(Integer id);

	Integer salvar(TransacaoDTO entity) throws BanklineBusinessException;

	List<Transacao> obterTodos(Long numConta);

}
