package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
	private int id;
	private long numero;
	private double saldo;
	private Usuario usuario;
}
