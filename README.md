# API Bankline

**Grupo Cabras do agREST**

Este é o repositório projeto final dos membros do grupo Cabras do agREST, para a turma  PA/21001 - Turma 7 - Desenvolvedor Backend - Java/Node.

|Membros|
|-------|
|Emmanuel de França Antunes Reis|
|Gabriel Loureiro de Albuquerque|
|Wedson Pereira de Souza|


Esse projeto foi feito utilizando todos os conhecimento que aprendemos durante o decorrer do curso que incluem
 - _Java_, como linguagem principal do projeto
 - _JPA_, para acesso, consulta e persistência
 - _SpringBoot_, centralizar arquivos de configurações e bootstrap
 - _Spring Data JPA_, facilidade na camada de banco de dados
 - _Spring Web_, RestController, RequestMapping e estrutura para projetos Web
 - _Swagger_, documentação da API
 - _JUnit & Mockito_, para testes unitários
 - _Spring Security_, para implementar segurança de rotas através de tokens JWT
  
 O projeto foi feito seguindo os requisitos estabelecidos pelos instrutores do curso, e pode ser encontrado [aqui](https://drive.google.com/file/d/1nliANoY0r4OilaWhz2-_iq7InxS355WR/view?usp=sharing).

A Api foi disponibilizada no serviço de cloud Heroku e está acessível através do link abaixo:

https://api-bankline-cabras-agrest.herokuapp.com/swagger-ui.html

Implementamos toda a jornada do usuário baseada nos requisitos exigidos, e no nosso sistema é possível:

- Cadastrar um novo usuário
- Cria um conta automaticamente
- Cria as categorias com base nos tipos de operações suportadas(receita, despesa, transferência)
- Gestão de Saldos de Contas: Saldo da conta do(s) usuário(s) são impactadas pelas operações de receita, despesa e transferência
- Extrato do perído: Podendo ser completo ou restrito ao período, no caso do período, há o cálculo do subtotal.
