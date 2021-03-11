package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
	@Id
	private int id;
	
	private long numero;
	private double saldo;

	@OneToOne(targetEntity = Usuario.class)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private int idUsuario;
}
