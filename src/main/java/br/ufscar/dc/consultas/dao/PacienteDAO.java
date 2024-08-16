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
	@Query("SELECT p FROM Paciente p WHERE p.Email = :Email")
	Paciente findByEmail(String Email);
	
//    @SuppressWarnings("unchecked")
//	Paciente save(Paciente paciente);
	
    @Modifying
    @Transactional
	@Query("DELETE FROM Paciente m WHERE m.CPF = :CPF")
   	void deleteByCpf(@Param("CPF") String cpf);
	
}
