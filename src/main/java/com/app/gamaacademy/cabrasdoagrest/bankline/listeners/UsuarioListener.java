package com.app.gamaacademy.cabrasdoagrest.bankline.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@Component
public class UsuarioListener {
	static private UsuarioRepository repository;
	private static Integer[] MAX_LENGTH = { Usuario.CPF_MAX_LENGTH, Usuario.LOGIN_MAX_LENGTH, Usuario.NOME_MAX_LENGTH,
			Usuario.SENHA_MAX_LENGTH };
	private static Integer[] MIN_LENGTH = { 11, 4, 3, 6 };

	@Autowired
	public void init(UsuarioRepository usuarioRepository) {
		UsuarioListener.repository = usuarioRepository;
	}

	@PrePersist
	public void validaInsercaoEAtulizacao(Usuario usuario)
			throws InvalidDataAccessApiUsageException, DataRetrievalFailureException {
		if (usuario.getId() == null) {
			List<String> parametros = validaUsuario(usuario);

			if (!parametros.isEmpty()) {
				String[] flexao = flexao(parametros.size());

				throw new IllegalArgumentException("O" + flexao[0] + " valor" + flexao[1] + " para "
						+ parametros.toString() + " não " + flexao[2] + " válido" + flexao[0] + "!");
			}

			return;
		}

		if (!repository.findById(usuario.getId()).isPresent())
			throw new DataRetrievalFailureException("Usuario não existe não é possível atualizar!");
	}

	private List<String> validaUsuario(Usuario usuario) {
		List<String[]> lista = new ArrayList<String[]>();
		List<String> camposInvalidos = new ArrayList<String>();

		lista.add(new String[] { "cpf", usuario.getCpf() });
		lista.add(new String[] { "login", usuario.getLogin() });
		lista.add(new String[] { "nome", usuario.getNome() });
		lista.add(new String[] { "senha", usuario.getSenha() });

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i)[1].length() > MAX_LENGTH[i] || lista.get(i)[1].length() < MIN_LENGTH[i])
				camposInvalidos.add(lista.get(i)[0]);
		}

		return camposInvalidos;
	}

	private String[] flexao(Integer qtd) {
		String[] singular = { "", "", "é" };
		String[] plural = { "s", "es", "são" };

		return qtd > 1 ? plural : singular;
	}
}
