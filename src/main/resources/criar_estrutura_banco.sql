-- create database if not exists bankline;
use bankline;
drop table if exists transacao;
drop table if exists conta;
drop table if exists usuario;

create table usuario(
	id bigint not null auto_increment primary key,
	login varchar(20) not null unique,
	senha varchar(20),
	nome varchar(50),
	cpf varchar(11) not null unique
)auto_increment = 1;

create table conta(
	id bigint not null auto_increment primary key,
	numero bigint not null unique,
	saldo double,
	id_usuario bigint,
	foreign key (id_usuario) references usuario(id)	
)auto_increment = 1;


create table transacao(
	id bigint not null auto_increment primary key,
	`data` date,
	valor double,
	id_conta_origem bigint,
	id_conta_destino bigint,
	plano_conta varchar(4)
	,foreign key (id_conta_origem) references conta(id)
	,foreign key (id_conta_destino) references conta(id)
)auto_increment = 1;
