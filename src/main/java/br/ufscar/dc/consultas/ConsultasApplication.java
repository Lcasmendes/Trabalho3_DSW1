package br.ufscar.dc.consultas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultasApplication.class, args);
	}
	
	@Override
	public void run (String... args) throws Exception {
		System.out.println("Se lascou");
	}

}
