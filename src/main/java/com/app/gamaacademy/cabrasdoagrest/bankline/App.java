package com.app.gamaacademy.cabrasdoagrest.bankline;

import java.util.stream.Stream;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.TipoPlanoConta;

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Stream.of(TipoPlanoConta.values()).forEach(p -> System.out.println(p));
	}
}
