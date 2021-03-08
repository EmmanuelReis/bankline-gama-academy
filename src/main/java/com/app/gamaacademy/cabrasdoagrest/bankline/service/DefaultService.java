package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

public interface DefaultService<T> {

	public int salvar(T usuario);

	public void alterar(int id, T usuario);

	public void excluir(int id);

	public T buscaPorId(int id);

	public List<T> obterTodos();

}
