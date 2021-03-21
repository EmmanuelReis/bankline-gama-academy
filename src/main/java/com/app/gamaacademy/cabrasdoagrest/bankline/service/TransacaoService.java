package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public interface TransacaoService {

	Transacao obter(Integer id) throws InvalidDataAccessApiUsageException, DataRetrievalFailureException, NullPointerException, Exception;

	Integer salvar(Transacao entity) throws Exception;

	List<Transacao> obterTodos(Long numConta);

}
