package com.app.gamaacademy.cabrasdoagrest.bankline.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.ContaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/conta")
public class ContaController {

	@Autowired
	private ContaService service;

	@ApiOperation(value = "Retorna as informações cadastrais da conta")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna informações cadastrais da conta"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Número de conta não encontrado"),
			@ApiResponse(code = 500, message = "Erro durante o processamento da requisição") })
	@GetMapping(value = "/{numero}", produces = "application/json")
	public ResponseEntity<ContaDTO> obter(@PathVariable(value = "numero") Long numero) throws Exception {
		ContaDTO ret = service.obter(numero);

		if (ret == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(ret);
	}

	@ApiOperation(value = "Retorna o extrato detalhado de todas as transações do cliente, ou por um período informado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o extrato detalhado de todas as transações do cliente, ou por um período informado"),
			@ApiResponse(code = 401, message = "Acesso não autorizado"),
			@ApiResponse(code = 404, message = "Número de conta não encontrado"),
			@ApiResponse(code = 500, message = "Erro durante o processamento da requisição") })
	@GetMapping(value = "/{numero}/extrato", produces = "application/json")
	public ResponseEntity<ExtratoDTO> extrato(@PathVariable(value = "numero") Long numero,
			@RequestParam(value = "dtInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtInicio,
			@RequestParam(value = "dtFim", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtFim)
			throws Exception {
		ExtratoDTO ret = service.extrato(numero, dtInicio, dtFim);

		return ResponseEntity.ok().body(ret);
	}

}
