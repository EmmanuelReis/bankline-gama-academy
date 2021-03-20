package com.app.gamaacademy.cabrasdoagrest.bankline.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class AppConfig {

	private static MapperFactory mapperFactory;

	static {
		mapperFactory = new DefaultMapperFactory.Builder().build();

		mapperFactory.classMap(Usuario.class, UsuarioDTO.class).exclude("planos").byDefault().register();
		mapperFactory.classMap(UsuarioDTO.class, Usuario.class).exclude("conta").byDefault().register();

		mapperFactory.classMap(PlanoConta.class, PlanoContaDTO.class).exclude("usuario").byDefault().register();
		mapperFactory.classMap(PlanoContaDTO.class, PlanoConta.class).byDefault().register();
	}

	@Bean
	public MapperFacade mapperFacade() {
		return mapperFactory.getMapperFacade();
	}
}