package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public interface UsuarioService {
	public Integer criarUsuario(UsuarioDTO usuario) throws Exception;

	public void atualizarUsuario(Usuario usuario) throws Exception;

	public void deletarUsuario(Integer id);

	public UsuarioDTO encontrarUsuario(Integer id);

	public Usuario encontrarUsuarioDB(Integer id);
	
	public UsuarioDTO encontrarPorLogin(String login);

	public List<PlanoContaDTO> obterPlanoContas(int id);
}
