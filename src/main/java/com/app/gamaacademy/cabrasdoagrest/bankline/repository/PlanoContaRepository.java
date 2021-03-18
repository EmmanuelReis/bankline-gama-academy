package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;

public interface PlanoContaRepository extends JpaRepository<PlanoConta, Integer> {

	@Query(value = "SELECT id, nome, tipo, id_usuario FROM bankline.plano_conta WHERE id_usuario = ?1", nativeQuery = true)
	public List<PlanoConta> obterPlanoContasUsuario(int idUsuario);

}
