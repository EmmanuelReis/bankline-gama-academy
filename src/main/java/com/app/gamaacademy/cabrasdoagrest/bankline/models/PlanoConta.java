package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import javax.persistence.CascadeType;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlanoConta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private TipoPlanoConta tipo;

    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_plano_usuario"))
	private Usuario usuario;
}
