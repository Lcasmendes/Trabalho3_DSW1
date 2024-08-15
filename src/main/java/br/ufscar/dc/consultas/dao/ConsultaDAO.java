package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaDAO extends JpaRepository<Consulta, Long> {
	
}
