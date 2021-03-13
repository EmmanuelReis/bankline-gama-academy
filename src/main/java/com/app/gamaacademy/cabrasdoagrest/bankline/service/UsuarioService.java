package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public interface UsuarioService {
    public abstract Integer criarUsuario(Usuario usuario) throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;
    public abstract void atualizarUsuario(Usuario usuario) throws InvalidDataAccessApiUsageException, DataIntegrityViolationException, DataRetrievalFailureException;
    public abstract void deletarUsuario(Integer id);
    public abstract Usuario encontrarUsuario(Integer id);
}
