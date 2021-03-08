package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

public interface DefaultRepository<T> {

	public int salvar(T entity);

	public void alterar(int id, T entity);

	public void excluir(int id);

	public T buscaPorId(int id);

	public List<T> obterTodos();
}
