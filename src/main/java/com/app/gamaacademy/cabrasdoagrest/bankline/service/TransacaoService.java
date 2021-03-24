package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoService {

	TransacaoDTO obter(Integer id) throws InvalidDataAccessApiUsageException, DataRetrievalFailureException, NullPointerException, Exception;

	Integer salvar(TransacaoDTO entity) throws Exception;

	List<Transacao> obterTodos(Long numConta);

}
