package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public interface UsuarioService {
	public Integer criarUsuario(Usuario usuario)
			throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;

	public void atualizarUsuario(Usuario usuario)
			throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;

	public void deletarUsuario(Integer id);

	public Usuario encontrarUsuario(Integer id);
}
