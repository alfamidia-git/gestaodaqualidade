package com.gestaodaqualidade;

import com.gestaodaqualidade.model.Cliente;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestaoDaQualidadeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoDaQualidadeApplication.class, args);

		Cliente cliente = new Cliente();
	}

}
