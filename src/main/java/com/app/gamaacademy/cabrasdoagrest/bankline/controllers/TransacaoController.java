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

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/transacao")
public class TransacaoController {

	@Autowired
	private TransacaoService service;

	@ApiOperation(value = "Cria uma nova transação para uma conta")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Transação criada com sucesso"),
			@ApiResponse(code = 400, message = "erros de validação") })
	@PostMapping(produces = "application/json")
	public ResponseEntity<Void> criar(@RequestBody TransacaoDTO transacao) throws Exception {

		Integer id = service.salvar(transacao);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(uri).build();

	}

	@ApiOperation(value = "Obtem detalhes de uma transação")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Obtem detalhes de uma transação"),
			@ApiResponse(code = 404, message = "transação não encontrada") })
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<TransacaoDTO> obter(@PathVariable(value = "id") Integer id) throws Exception {
		TransacaoDTO ret = service.obter(id);
		if (ret == null) {
			ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ret);
	}

}
