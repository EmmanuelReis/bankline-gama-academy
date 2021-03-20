package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoService {

	Transacao obter(Integer id);

	Integer salvar(Transacao entity) throws Exception;

	List<Transacao> obterTodos(Long numConta);

}
