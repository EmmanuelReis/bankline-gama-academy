package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

import java.util.List;

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
	
	public boolean validaLoginCpfUnicos(String login, String cpf);

	public List<PlanoConta> obterPlanoContas(int id);
}
