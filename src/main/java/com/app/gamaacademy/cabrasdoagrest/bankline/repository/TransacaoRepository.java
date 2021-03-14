package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

}
