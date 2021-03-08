package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanoConta {	
	private int id;
	private String sigla;
	private String nome;
}
