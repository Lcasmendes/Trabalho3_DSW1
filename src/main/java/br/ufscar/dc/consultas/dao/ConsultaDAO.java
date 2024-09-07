package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaDAO extends JpaRepository<Consulta, Long> {

    @Modifying
    @Transactional
    void deleteByMedico_Crm(String crm);
    
    @Modifying
    @Transactional
    void deleteByPaciente_CPF(String cpf);
    
    @Query("SELECT c FROM Consulta c WHERE c.paciente.CPF = :cpf")
    List<Consulta> findConsultasByPacienteCPF(@Param("cpf") String cpf);
    
    @Query("SELECT c FROM Consulta c WHERE c.medico.crm = :crm")
    List<Consulta> findConsultasByMedicoCRM(@Param("crm") String crm);

    @Query("SELECT c FROM Consulta c WHERE c.paciente = :paciente")
    List<Consulta> findByPaciente(@Param("paciente") Paciente paciente);

    @Query("SELECT c FROM Consulta c WHERE c.medico = :medico")
    List<Consulta> findByMedico(@Param("medico") Medico medico);

    @Query("SELECT c FROM Consulta c WHERE c.medico.crm = :crm AND c.horario = :horario AND c.dataConsulta = :dataConsulta")
    Optional<Consulta> findConsultaByMedicoAndHorarioAndDataConsulta(@Param("crm") String crm, @Param("horario") String horario, @Param("dataConsulta") String dataConsulta);
    
    @Query("SELECT c FROM Consulta c WHERE c.paciente.CPF = :cpf AND c.horario = :horario AND c.dataConsulta = :dataConsulta")
    Optional<Consulta> findConsultaByPacienteAndHorarioAndDataConsulta(@Param("cpf") String cpf, @Param("horario") String horario, @Param("dataConsulta") String dataConsulta);


}
