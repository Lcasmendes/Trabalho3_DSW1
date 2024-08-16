package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Consulta;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaDAO extends JpaRepository<Consulta, Long> {
    
    @Modifying
    @Transactional
    void deleteByMedico_Crm(String crm);

    @Query("SELECT c FROM Consulta c WHERE c.medico.crm = :crm AND c.horario = :horario AND c.dataConsulta = :dataConsulta")
    Optional<Consulta> findConsultaByMedicoAndHorarioAndDataConsulta(@Param("crm") String crm, @Param("horario") String horario, @Param("dataConsulta") String dataConsulta);
}
