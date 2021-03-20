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

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ExtratoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.ContaService;

@RestController
@RequestMapping("api/conta")
public class ContaController {

	@Autowired
	private ContaService service;

	@GetMapping("/{numero}")
	public ResponseEntity<ExtratoDTO> obter(@PathVariable(value = "numero") Long numero) throws Exception {
		ExtratoDTO ret = service.extrato(numero);

		return ResponseEntity.ok().body(ret);
	}

	@GetMapping("/{numero}/detalhado")
	public ResponseEntity<ExtratoDTO> obterPorData(@PathVariable(value = "numero") Long numero,
			@RequestParam("dtInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtInicio,
			@RequestParam(value = "dtFim", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dtFim)
			throws Exception {
		ExtratoDTO ret = service.extrato(numero, dtInicio, dtFim);

		return ResponseEntity.ok().body(ret);
	}

}
