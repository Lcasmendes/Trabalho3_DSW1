package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Medico;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoDAO extends JpaRepository<Medico, Long> {
    @Query("SELECT m FROM Medico m WHERE LOWER(m.Especialidade) LIKE LOWER(CONCAT('%', :Especialidade, '%'))")
    List<Medico> buscarPorEspecialidade(@Param("Especialidade") String especialidade);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Medico m WHERE m.crm = :crm")
    void deleteByCrm(@Param("crm") String crm);
    
    Medico findByCrm(String crm);
    
	@Query("SELECT p FROM Medico p WHERE p.Email = :Email")
	Medico findByEmail(String Email);
}
