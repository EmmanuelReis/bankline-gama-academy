package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

	public List<Transacao> findByContaOrigemNumeroEquals(Long numero);

	// Essa query não funciona no heroku, usuário não tem acesso a essa tabela,
	// limitação de conta free
	@Query(value = "select * from bankline.transacao where numero_conta_origem = ?1 and data between ?2 and ?3", nativeQuery = true)
	public List<Transacao> obterExtrato(Long numero, String dtInicio, String dtFim);

	public List<Transacao> findByContaOrigemNumeroEqualsAndDataBetween(Long numero, LocalDateTime startDate,
			LocalDateTime endDate);

}
