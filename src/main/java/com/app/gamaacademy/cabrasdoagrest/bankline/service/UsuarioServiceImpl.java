package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.PlanoContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PlanoContaRepository pcRepository;

	@Autowired
	private ContaService contaService;

	@Override
	public Integer criarUsuario(UsuarioDTO usuario) {
		if(!validaLoginCpfUnicos(usuario.getLogin(), usuario.getCpf()))
			throw new DataIntegrityViolationException("CPF e/ou login já existe não é possível cadastrar!");

		Usuario entity = Mapper.convertUsuarioDtoToEntity(usuario);

		Integer id = repository.save(entity).getId();
		contaService.criar(id);

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
		UsuarioDTO ret = Mapper.convertUsuarioToDto(repository.findById(id).orElse(null));
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

		list.forEach(p -> ret.add(Mapper.convertPlanoContaToDto(p)));

		return ret;
	}

	@Override
	public boolean validaLoginCpfUnicos(String login, String cpf) {
		return repository.findByLoginOrCpfEquals(login, cpf) == null;
	}

	public void setRepository(UsuarioRepository usuarioRepository) {
		this.repository = usuarioRepository;
	}

	public void setMapper(MapperFacade mapperMock) {
		this.mapper = mapperMock;
	}

}
