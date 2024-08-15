package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoDAO extends JpaRepository<Medico, Long> {

	 @Query("SELECT m FROM Medico m WHERE LOWER(m.Especialidade) LIKE LOWER(CONCAT('%', :especialidade, '%'))")
	 List<Medico> buscarPorEspecialidade(@Param("especialidade") String especialidade);
}
