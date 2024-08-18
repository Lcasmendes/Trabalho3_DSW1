package br.ufscar.dc.consultas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.dao.AdminDAO;
import br.ufscar.dc.consultas.domain.Paciente;
import br.ufscar.dc.consultas.domain.Admin;
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
	public CommandLineRunner demo(PacienteDAO pacienteDAO, MedicoDAO medicoDAO, AdminDAO adminDao) {
		return (args) -> {
			
			Paciente u1 = new Paciente();
			u1.setCPF("12345678998");
			u1.setData_nascimento("11/11/2001");
			u1.setEmail("genivaldo@gmail.com");
			u1.setNome("Genivaldo");
			u1.setRole("ROLE_PACIENTE");
			u1.setSenha("genivaldo");
			u1.setSexo("M");
			u1.setTelefone("11934228462");
			pacienteDAO.save(u1);
			
			
			Paciente u2 = new Paciente();
			u2.setCPF("14725836952");
			u2.setData_nascimento("28/09/1984");
			u2.setEmail("cristina@outlook.com");
			u2.setNome("Cristina");
			u2.setRole("ROLE_PACIENTE");
			u2.setSenha("cristina");
			u2.setSexo("F");
			u2.setTelefone("32984956235");
			pacienteDAO.save(u2);
			
			Medico u3 = new Medico();
			u3.setCRM("111111");
			u3.setEmail("mario@gmail.com");
			u3.setNome("Mario");
			u3.setRole("ROLE_MEDICO");
			u3.setSenha("mario");
			u3.setEspecialidade("Cardiologista");
			medicoDAO.save(u3);
			
			
			Medico u4 = new Medico();
			u4.setCRM("222222");
			u4.setEmail("astolfo@gmail.com");
			u4.setNome("Astolfo");
			u4.setRole("ROLE_MEDICO");
			u4.setSenha("astolfo");
			u4.setEspecialidade("Oftamologista");
			medicoDAO.save(u4);
			
			Admin u5 = new Admin();
			u5.setEmail("admin");
			u5.setSenha("admin");
			u5.setRole("ROLE_ADMIN");
			adminDao.save(u5);
			
			
		};
	}
}
