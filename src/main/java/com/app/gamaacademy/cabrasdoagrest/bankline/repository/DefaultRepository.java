package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

public interface DefaultRepository<T, K> {

	public K salvar(T entity);

	public void alterar(K id, T entity) throws Exception;

	public void excluir(K id);

	public T buscaPorId(K id);

	public List<T> obterTodos();
	
}
