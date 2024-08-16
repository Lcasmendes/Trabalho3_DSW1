package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Paciente;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteDAO extends JpaRepository<Paciente, Long> {

	Paciente findByCPF(String CPF);
	
    @Modifying
    @Transactional
	@Query("DELETE FROM Paciente m WHERE m.CPF = :CPF")
   	void deleteByCpf(@Param("CPF") String cpf);
	
}
