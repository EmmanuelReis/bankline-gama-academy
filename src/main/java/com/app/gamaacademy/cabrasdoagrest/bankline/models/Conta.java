package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;

	@Column(precision = 2)
	private Double saldo = 0.0;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_conta_usuario"))
	private Usuario usuario;

	public void setSaldo(double valor) {
		this.saldo += valor;
	}
}
