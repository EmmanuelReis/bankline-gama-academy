package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.PlanoContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

import ma.glasnost.orika.MapperFacade;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PlanoContaRepository pcRepository;

	@Autowired
	private MapperFacade mapper;

	@Override
	public Integer criarUsuario(UsuarioDTO usuario) {
		Integer id = 0;

		Usuario entity = mapper.map(usuario, Usuario.class);

		try {
			Conta novaConta = new Conta();
			entity.setConta(novaConta);
			novaConta.setUsuario(entity);
			id = repository.save(entity).getId();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
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
	public UsuarioDTO encontrarUsuario(Integer id) {
		UsuarioDTO ret = mapper.map(repository.findById(id).orElse(null), UsuarioDTO.class);
		ret.setPlanos(obterPlanoContas(id));
		return ret;
	}

	@Override
	public Usuario encontrarUsuarioDB(Integer id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<PlanoContaDTO> obterPlanoContas(int id) {
		List<PlanoConta> list = pcRepository.findByUsuarioIdEquals((int) id);
		List<PlanoContaDTO> ret = new ArrayList<>();

		list.forEach(p -> ret.add(mapper.map(p, PlanoContaDTO.class)));

		return ret;
	}

	@Override
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return repository.findByLoginOrCpfEquals(login, cpf) == null;
	}

}
