package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

public interface DefaultService<T> {

	public int salvar(T entity) throws Exception;

	public void alterar(int id, T entity);

	public void excluir(int id);

	public T buscaPorId(int id);

	public List<T> obterTodos();

}
