package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	public Usuario findByLoginEquals(String login);
	
	@Query("SELECT 1 FROM Usuario u WHERE login = :login AND cpf = :cpf")
	public boolean validaLoginCpfUnicos(String login, String cpf);

}
