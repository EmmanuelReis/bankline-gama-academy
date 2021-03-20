package com.app.gamaacademy.cabrasdoagrest.bankline.utils;

import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.ContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.PlanoContaDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.TransacaoDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.dtos.UsuarioDTO;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class Mapper {

	public static ContaDTO convertContaToDto(Conta entity) {
		if (entity == null)
			return null;

		ContaDTO dto = new ContaDTO();
		dto.setNumero(entity.getNumero());
		dto.setSaldo(entity.getSaldo());

		return dto;
	}

	public static Conta convertContaDtoToEntity(ContaDTO dto) {
		if (dto == null)
			return null;

		Conta entity = new Conta();
		entity.setNumero(dto.getNumero());
		entity.setSaldo(dto.getSaldo());

		return entity;
	}

	public static PlanoContaDTO convertPlanoContaToDto(PlanoConta entity) {
		if (entity == null)
			return null;

		PlanoContaDTO dto = new PlanoContaDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setTipo(entity.getTipo());

		return dto;
	}

	public static PlanoConta convertPlanoContaDtoToEntity(PlanoContaDTO dto) {
		if (dto == null)
			return null;

		PlanoConta entity = new PlanoConta();
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());
		entity.setTipo(dto.getTipo());

		return entity;
	}

	public static TransacaoDTO convertTransacaoToDto(Transacao entity) {
		if (entity == null)
			return null;

		TransacaoDTO dto = new TransacaoDTO();
		dto.setContaDestino(convertContaToDto(entity.getContaDestino()));
		dto.setContaOrigem(convertContaToDto(entity.getContaOrigem()));
		dto.setData(entity.getData());
		dto.setId(entity.getId());
		dto.setPlanoConta(convertPlanoContaToDto(entity.getPlanoConta()));
		dto.setValor(entity.getValor());

		return dto;
	}

	public static Transacao convertTransacaoDtoToEntity(TransacaoDTO dto) {
		if (dto == null)
			return null;

		Transacao entity = new Transacao();
		entity.setContaDestino(convertContaDtoToEntity(dto.getContaDestino()));
		entity.setContaOrigem(convertContaDtoToEntity(dto.getContaOrigem()));
		entity.setData(dto.getData());
		entity.setId(dto.getId());
		entity.setPlanoConta(convertPlanoContaDtoToEntity(dto.getPlanoConta()));
		entity.setValor(dto.getValor());

		return entity;
	}

	public static UsuarioDTO convertUsuarioToDto(Usuario entity) {
		if (entity == null)
			return null;

		UsuarioDTO dto = new UsuarioDTO();
		dto.setConta(convertContaToDto(entity.getConta()));
		dto.setCpf(entity.getCpf());
		dto.setId(entity.getId());
		dto.setLogin(entity.getLogin());
		dto.setNome(entity.getNome());
		dto.setSenha(entity.getSenha());

		return dto;
	}

	public static Usuario convertUsuarioDtoToEntity(UsuarioDTO dto) {
		if (dto == null)
			return null;

		Usuario entity = new Usuario();
		entity.setConta(convertContaDtoToEntity(dto.getConta()));
		entity.setCpf(dto.getCpf());
		entity.setId(dto.getId());
		entity.setLogin(dto.getLogin());
		entity.setNome(dto.getNome());
		entity.setSenha(dto.getSenha());

		return entity;
	}

}
