package com.app.gamaacademy.cabrasdoagrest.bankline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.BanklineBusinessException;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.ErrorCode;
import com.app.gamaacademy.cabrasdoagrest.bankline.exceptions.Operadores;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.PlanoContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.utils.Mapper;

import lombok.Getter;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PlanoContaRepository pcRepository;

	@Autowired
	private ContaService contaService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Integer criarUsuario(UsuarioDTO usuario) throws Exception {
		validar(usuario);

		Usuario entity = Mapper.convertUsuarioDtoToEntity(usuario);

		entity.setSenha(encoder.encode(entity.getSenha()));

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
		UsuarioDTO ret = Mapper.convertUsuarioEntityToDto(repository.findById(id).orElse(null));
		if (ret != null) {
			ret.setPlanos(obterPlanoContas(id));
		}
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

		list.forEach(p -> ret.add(Mapper.convertPlanoContaEntityToDto(p)));

		return ret;
	}

	private void validar(UsuarioDTO usuario) throws Exception {
		HashMap<String, ValidacaoUsuario> map = obterRegrasValidacao();

		ValidacaoUsuario v = null;

		if (usuario == null) {
			throw new BanklineBusinessException(ErrorCode.E0001, "usuario", null, null);
		}

		if (StringUtils.isBlank(usuario.getLogin())) {
			throw new BanklineBusinessException(ErrorCode.E0002, "usuario", "login", null);
		}

		v = map.get("login");
		if (usuario.getLogin().length() < v.getMin() || usuario.getLogin().length() > v.getMax()) {
			throw new BanklineBusinessException(ErrorCode.E0008, "usuario", "login", "" + usuario.getLogin().length(),
					Operadores.MAIOR_IGUAL.getOperador(), v.getMin().toString(), Operadores.MENOR_IGUAL.getOperador(),
					v.getMax().toString());
		}

		if (StringUtils.isBlank(usuario.getCpf())) {
			throw new BanklineBusinessException(ErrorCode.E0002, "usuario", "cpf", null);
		}

		v = map.get("cpf");
		if (usuario.getCpf().length() < v.getMin() || usuario.getCpf().length() > v.getMax()) {
			throw new BanklineBusinessException(ErrorCode.E0008, "usuario", "cpf", "" + usuario.getCpf().length(),
					Operadores.MAIOR_IGUAL.getOperador(), v.getMin().toString(), Operadores.MENOR_IGUAL.getOperador(),
					v.getMax().toString());
		}

		if (StringUtils.isBlank(usuario.getSenha())) {
			throw new BanklineBusinessException(ErrorCode.E0002, "usuario", "senha", null);
		}

		v = map.get("senha");
		if (usuario.getSenha().length() < v.getMin() || usuario.getSenha().length() > v.getMax()) {
			throw new BanklineBusinessException(ErrorCode.E0008, "usuario", "senha", "" + usuario.getSenha().length(),
					Operadores.MAIOR_IGUAL.getOperador(), v.getMin().toString(), Operadores.MENOR_IGUAL.getOperador(),
					v.getMax().toString());
		}

		if (StringUtils.isBlank(usuario.getNome())) {
			throw new BanklineBusinessException(ErrorCode.E0002, "usuario", "nome", null);
		}

		v = map.get("nome");
		if (usuario.getNome().length() < v.getMin() || usuario.getNome().length() > v.getMax()) {
			throw new BanklineBusinessException(ErrorCode.E0008, "usuario", "nome", "" + usuario.getNome().length(),
					Operadores.MAIOR_IGUAL.getOperador(), v.getMin().toString(), Operadores.MENOR_IGUAL.getOperador(),
					v.getMax().toString());
		}

		if (repository.findByLoginEquals(usuario.getLogin()) != null)
			throw new BanklineBusinessException(ErrorCode.E0006, "usuario", "login", usuario.getLogin());

		if (repository.findByCpfEquals(usuario.getCpf()) != null)
			throw new BanklineBusinessException(ErrorCode.E0006, "usuario", "cpf", usuario.getCpf());

	}

	private HashMap<String, ValidacaoUsuario> obterRegrasValidacao() {
		HashMap<String, ValidacaoUsuario> map = new HashMap<>();

		Stream.of(ValidaCamposUsuarioEnum.values()).forEach(v -> {
			map.put(v.name(), new ValidacaoUsuario(v.getMin(), v.getMax()));
		});

		return map;
	}

	@Override
	public UsuarioDTO encontrarPorLogin(String login) {
		return Mapper.convertUsuarioEntityToDto(repository.findByLoginEquals(login));
	}

}

@Getter
class ValidacaoUsuario {

	private Integer min;
	private Integer max;

	public ValidacaoUsuario(Integer min, Integer max) {
		super();
		this.min = min;
		this.max = max;
	}

}
