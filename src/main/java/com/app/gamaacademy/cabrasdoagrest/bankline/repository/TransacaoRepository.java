package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query(value = "SELECT * FROM bankline.transacao WHERE conta_origem = ?1 OR conta_destino = ?1", nativeQuery = true)
    public List<Transacao> findByConta(Conta conta);

}
