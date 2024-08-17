package br.ufscar.dc.consultas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.domain.Paciente;

@SpringBootApplication
public class ConsultasApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultasApplication.class, args);
	}
	
	@Override
	public void run (String... args) throws Exception {
		System.out.println("Aplicacao iniciada");
	}
	
	@Bean
	public CommandLineRunner demo(PacienteDAO pacienteDAO) {
		return (args) -> {
			
			Paciente u1 = new Paciente();
			u1.setCPF("11111111111");
			u1.setData_nascimento("11/11/1111");
			u1.setEmail("111");
			u1.setNome("111");
			u1.setRole("ROLE_PACIENTE");
			u1.setSenha("1111");
			u1.setSexo("1");
			u1.setTelefone("1111");
			pacienteDAO.save(u1);
		};
	}
}
