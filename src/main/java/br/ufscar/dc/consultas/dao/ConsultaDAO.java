package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Consulta;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaDAO extends JpaRepository<Consulta, Long> {
	@Modifying
	@Transactional
    void deleteByMedico_Crm(String crm);
}
