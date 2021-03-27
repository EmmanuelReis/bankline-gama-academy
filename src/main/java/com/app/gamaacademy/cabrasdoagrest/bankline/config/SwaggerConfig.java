package com.app.gamaacademy.cabrasdoagrest.bankline.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.app.gamaacademy.cabrasdoagrest.bankline.controllers"))
				.paths(PathSelectors.any()).build().useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, responseMessageForGet())
				.globalResponseMessage(RequestMethod.POST, responseMessageForPost()).apiInfo(apiInfo());

	}

	@SuppressWarnings("serial")
	private List<ResponseMessage> responseMessageForGet() {
		return new ArrayList<ResponseMessage>() {
			{
				add(new ResponseMessageBuilder().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase())
						.build());
				add(new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value())
						.message(HttpStatus.NOT_FOUND.getReasonPhrase()).build());
			}
		};
	}

	@SuppressWarnings("serial")
	private List<ResponseMessage> responseMessageForPost() {
		return new ArrayList<ResponseMessage>() {
			{
				add(new ResponseMessageBuilder().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase())
						.build());
				add(new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message(HttpStatus.BAD_REQUEST.getReasonPhrase()).build());
			}
		};
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Api Bankline")
				.description(
						"Projeto final desenvolvido pelo time cabras do agREST no curso oferecido pela Gama Academy.")
				.contact(new Contact("cabras do agREST",
						"https://github.com/EmmanuelReis/bankline-gama-academy/tree/main", "gloureiro100@gmail.com"))
				.build();
	}

}
