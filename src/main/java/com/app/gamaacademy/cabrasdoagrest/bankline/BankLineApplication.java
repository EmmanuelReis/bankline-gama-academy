package com.app.gamaacademy.cabrasdoagrest.bankline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@SpringBootApplication
public class BankLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankLineApplication.class, args);
	}

	@Autowired
	private PasswordEncoder encoder;

	@Bean
	public CommandLineRunner run(UsuarioRepository usuarioRepository) {
		return args -> {
			if (usuarioRepository.findByLoginEquals("admin") == null) {
				Usuario u = new Usuario();
				u.setCpf("11111111111");
				u.setLogin("admin");
				u.setNome("administrador");
				u.setSenha(encoder.encode("admin"));
				usuarioRepository.save(u);
			}
		};
	}

}
