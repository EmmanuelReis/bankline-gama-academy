package com.app.gamaacademy.cabrasdoagrest.bankline.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.LoginDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.Sessao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.security.jwt.JWTConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@PostMapping
	public Sessao logar(@RequestBody LoginDTO usuario) throws Exception {

		Usuario user = repository.findByLoginEquals(usuario.getLogin());
		System.out.println(repository.findByLoginEquals(usuario.getLogin()));

		boolean senhaOk = encoder.matches(usuario.getSenha(), user.getSenha());

		if (!senhaOk) {
			throw new RuntimeException("Senha inválida para o login: " + usuario.getLogin());
		}

		long tempoToken = 1 * 60 * 60 * 1000;
		Sessao sessao = new Sessao();
		sessao.setDataInicio(new Date(System.currentTimeMillis()));
		sessao.setDataFim(new Date(System.currentTimeMillis() + tempoToken));

		sessao.setLogin(user.getLogin());

		sessao.setToken(JWTConstants.PREFIX + getJWTToken(sessao));

		return sessao;
	}

	// como vc gerenciaria a nivel de banco o role de um usuario
	private String getJWTToken(Sessao sessao) {
		String role = "ROLE_ADMIN";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);

		String token = Jwts.builder().setSubject(sessao.getLogin())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(sessao.getDataInicio()).setExpiration(sessao.getDataFim())
				.signWith(SignatureAlgorithm.HS512, JWTConstants.KEY.getBytes()).compact();

		return token;
	}

}