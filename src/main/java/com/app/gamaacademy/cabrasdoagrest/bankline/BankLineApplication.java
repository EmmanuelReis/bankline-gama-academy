package com.app.gamaacademy.cabrasdoagrest.bankline;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Conta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.PlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Transacao;
import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.ContaRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.repository.UsuarioRepository;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.TransacaoService;
import com.app.gamaacademy.cabrasdoagrest.bankline.service.UsuarioService;

@SpringBootApplication
public class BankLineApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BankLineApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UsuarioRepository ur, UsuarioService userRepo, TransacaoService tService,
			ContaRepository contaRepository) {
		return args -> {
			Usuario u = new Usuario();
			u.setLogin("joaodasneves");
			u.setCpf("12345678901");

			ur.save(u);

			System.out.println("DEU BOM!");

			u = new Usuario();
			u.setCpf("12345678985");
			u.setLogin("gabriel");
			u.setNome("gabriel");
			u.setSenha("123");

			int id = userRepo.criarUsuario(u);
			System.out.println(id);

			List<Conta> lt = contaRepository.findAll();
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

			long c1Num = c1.getNumero();
			Conta result = contaRepository.findAll().stream().filter(x -> x.getNumero() == c1Num).findFirst().get();

			c1 = result;

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
		};
	}

}
