package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public Integer criarUsuario(Usuario usuario) {
		return repository.save(usuario).getId();
	}

	@Override
	public void atualizarUsuario(Usuario usuario) {
		if(usuario.getId() == null)
			return;

		repository.save(usuario);
	}

	@Override
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Usuario encontrarUsuario(Integer id) {
		return repository.findById(id).get();
	}

}
