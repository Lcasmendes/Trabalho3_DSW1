package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoDAO extends JpaRepository<Medico, Long> {
    
}
