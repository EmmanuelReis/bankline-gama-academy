package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

	public List<Transacao> findByContaOrigemNumeroEquals(Long numero);

}
