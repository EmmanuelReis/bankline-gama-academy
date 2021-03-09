-- create database if not exists bankline;

drop table if exists transacoes;
drop table if exists conta;
drop table if exists usuario;
drop table if exists plano_conta;

create table usuario(
	id bigint not null auto_increment primary key,
	login varchar(20) not null unique,
	senha varchar(20),
	nome varchar(50),
	cpf varchar(11) not null unique
)auto_increment = 1;

create table plano_conta(
	id integer not null,
	sigla varchar(10) not null unique,
	nome varchar(50) not null,
	primary key(id)
);

create table conta(
	id bigint not null auto_increment primary key,
	numero bigint not null unique,
	saldo double,
	id_usuario bigint,
	foreign key (id_usuario) references usuario(id)	
)auto_increment = 1;


create table transacoes(
	id bigint not null auto_increment primary key,
	`data` date,
	valor double,
	id_conta bigint,
	id_plano_conta integer
	,foreign key (id_conta) references conta(id)
	,foreign key (id_plano_conta) references plano_conta(id)
)auto_increment = 1;

-- Adicionando DomÃ­nios

insert into plano_conta values(1,'R','RECEITA');
insert into plano_conta values(2,'D','DESPESA');
insert into plano_conta values(3,'T','TRANSFERENCIA');