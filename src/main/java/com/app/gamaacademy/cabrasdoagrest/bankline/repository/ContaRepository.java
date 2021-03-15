package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

}
