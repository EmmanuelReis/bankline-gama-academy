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

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoService;

@RestController
@RequestMapping("api/transacao")
public class TransacaoController {

	@Autowired
	private TransacaoService service;

	@PostMapping
	public ResponseEntity<Void> criar(@RequestBody Transacao transacao) throws Exception {

		Integer id = service.salvar(transacao);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(uri).build();

	}

	@GetMapping("/{id}")
	public ResponseEntity<Transacao> obter(@PathVariable(value = "id") Integer id) throws Exception {
		Transacao ret = service.obter(id);

		return ResponseEntity.ok().body(ret);
	}

}
