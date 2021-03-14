package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
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
		if (usuario.getId() == null)
			return;

		repository.save(usuario);
	}

	@Override
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Usuario encontrarUsuario(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<PlanoConta> obterPlanoContas(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return repository.findByLoginOrCpfEquals(login, cpf) == null;
	}

}
