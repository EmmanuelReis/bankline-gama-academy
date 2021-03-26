package com.app.gamaacademy.cabrasdoagrest.bankline.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sessao {
	private String login;
	private String token;
	private Date dataInicio;
	private Date dataFim;
}
