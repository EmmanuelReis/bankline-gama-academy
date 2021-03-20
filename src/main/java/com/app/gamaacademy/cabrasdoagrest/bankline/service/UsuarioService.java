package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public interface UsuarioService {
	public Integer criarUsuario(UsuarioDTO usuario)
			throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;

	public void atualizarUsuario(Usuario usuario)
			throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;

	public void deletarUsuario(Integer id);

	public UsuarioDTO encontrarUsuario(Integer id);

	public Usuario encontrarUsuarioDB(Integer id);

	public boolean validaLoginCpfUnicos(String login, String cpf);

	public List<PlanoContaDTO> obterPlanoContas(int id);
}
