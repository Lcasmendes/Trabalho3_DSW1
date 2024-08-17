package br.ufscar.dc.consultas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.domain.Paciente;
import br.ufscar.dc.consultas.domain.Medico;

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
	public CommandLineRunner demo(PacienteDAO pacienteDAO, MedicoDAO medicoDAO) {
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
			
			
			Paciente u2 = new Paciente();
			u2.setCPF("22222222222");
			u2.setData_nascimento("11/11/1111");
			u2.setEmail("222");
			u2.setNome("222");
			u2.setRole("ROLE_PACIENTE");
			u2.setSenha("1111");
			u2.setSexo("1");
			u2.setTelefone("1111");
			pacienteDAO.save(u2);
			
			Medico u3 = new Medico();
			u3.setCRM("111111");
			u3.setEmail("mario@gmail.com");
			u3.setNome("Mario");
			//u1.setRole("ROLE_PACIENTE");
			u3.setSenha("mario");
			u3.setEspecialidade("Cardiologista");
			medicoDAO.save(u3);
			
			
			Medico u4 = new Medico();
			u4.setCRM("111112");
			u4.setEmail("astolfo@gmail.com");
			u4.setNome("Astolfo");
			//u1.setRole("ROLE_PACIENTE");
			u4.setSenha("astolfo");
			u4.setEspecialidade("Oftamologista");
			medicoDAO.save(u4);
			
			
			
		};
	}
}
