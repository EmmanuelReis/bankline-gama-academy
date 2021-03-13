package com.app.gamaacademy.cabrasdoagrest.bankline;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.DefaultService;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoServiceImpl;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioServiceImpl;

public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		Stream.of(TipoPlanoConta.values()).forEach(p -> System.out.println(p));

		DefaultService<Usuario> userRepo = new UsuarioServiceImpl();

		// TransacaoRepository tRepo = new TransacaoRepository();
		DefaultService<Transacao> tService = new TransacaoServiceImpl();
		ContaRepository contaRepository = new ContaRepository();

		
		Usuario u = new Usuario(); u.setCpf("12345678985"); u.setLogin("gabriel");
		u.setNome("gabriel"); u.setSenha("123");
		 
		int id = userRepo.salvar(u); System.out.println(id);
		 

		List<Conta> lt = contaRepository.obterTodos();
		Conta c1 = lt.get(0);
		Conta c2 = lt.get(1);
		lt = null;

		PlanoConta pc = new PlanoConta();

		pc.setNome("Luz");
		pc.setTipo(TipoPlanoConta.RECEITA);
		pc.setUsuario(u);

		Transacao t = new Transacao();

		t.setContaOrigem(c1);
		t.setPlanoConta(pc);
		t.setValor(100.25);

		tService.salvar(t);

		// Ver se o Gleyson consegue dar uma ajuda pq essa linha abaixo não está
		// funcionando.
		// Aparentemente no banco está alterando o saldo da conta, mas a busca do JPA
		// não está
		// retornando o objeto atualizado
		long c1Num = c1.getNumero();
		Conta result = contaRepository.obterTodos().stream().filter(x -> x.getNumero() == c1Num).findFirst().get();

		c1 = result;
		// c1 = contaRepository.buscaPorId(c1.getNumero());

		PlanoConta pcd = new PlanoConta();

		pcd.setNome("Luz");
		pcd.setTipo(TipoPlanoConta.DESPESA);
		pcd.setUsuario(u);

		Transacao t1 = new Transacao();

		t1.setContaOrigem(c1);
		t1.setPlanoConta(pcd);
		t1.setValor(-50.10);

		tService.salvar(t1);

		PlanoConta pct = new PlanoConta();

		pct.setNome("Luz");
		pct.setTipo(TipoPlanoConta.TRANSFERENCIA);
		pct.setUsuario(u);

		Transacao t2 = new Transacao();

		t2.setContaOrigem(c1);
		t2.setContaDestino(c2);
		t2.setPlanoConta(pct);
		t2.setValor(20.0);

		tService.salvar(t2);

	}
}
