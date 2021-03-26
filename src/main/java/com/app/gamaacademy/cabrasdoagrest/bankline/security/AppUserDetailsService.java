package com.app.gamaacademy.cabrasdoagrest.bankline.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLoginEquals(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (usuario.getLogin().equals("admin"))
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return new User(usuario.getLogin(), usuario.getSenha(), authorities);
	}

}
