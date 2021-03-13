package com.app.gamaacademy.cabrasdoagrest.bankline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@SpringBootApplication
public class BankLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankLineApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(UsuarioRepository ur){
		return args -> {
			Usuario u = new Usuario();
			u.setLogin("joaodasneves");
			u.setCpf("12345678901");
			
			ur.save(u);
			
			System.out.println("DEU BOM!");
		};
	}

}
