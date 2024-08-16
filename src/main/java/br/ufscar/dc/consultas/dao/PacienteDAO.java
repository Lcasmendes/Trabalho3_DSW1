package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteDAO extends JpaRepository<Paciente, Long> {

	Paciente findByCPF(String CPF);
	
}
