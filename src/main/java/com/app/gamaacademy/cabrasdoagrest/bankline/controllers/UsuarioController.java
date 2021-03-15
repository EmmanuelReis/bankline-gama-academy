package com.app.gamaacademy.cabrasdoagrest.bankline.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioService;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@PostMapping
	public ResponseEntity<Void> criar(@RequestBody Usuario usuario) {

		Integer id = service.criarUsuario(usuario);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(uri).build();

	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obter(@PathVariable(value = "id") Integer id) {
		Usuario ret = service.encontrarUsuario(id);

		return ResponseEntity.ok().body(ret);
	}

}
