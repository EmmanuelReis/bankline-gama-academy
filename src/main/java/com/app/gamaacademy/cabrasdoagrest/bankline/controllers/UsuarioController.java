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

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@ApiOperation(value = "Cria um novo usuário")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Usuário criado com sucesso"),
			@ApiResponse(code = 400, message = "erros na validação") })
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> criar(@RequestBody UsuarioDTO usuario) throws Exception {
		Integer id = service.criarUsuario(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Obtem detalhes de um usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Obtem detalhes de um usuário"),
			@ApiResponse(code = 404, message = "usuário não encontrado") })
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UsuarioDTO> obter(@PathVariable(value = "id") Integer id) {
		UsuarioDTO ret = service.encontrarUsuario(id);
		if (ret != null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(ret);
	}

}
