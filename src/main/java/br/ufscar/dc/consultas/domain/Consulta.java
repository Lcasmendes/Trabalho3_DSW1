package br.ufscar.dc.consultas.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CPF", foreignKey = @ForeignKey(name = "FK_Paciente"), nullable = false)
    private Paciente paciente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CRM", foreignKey = @ForeignKey(name = "FK_Medico"), nullable = false)
    private Medico medico;


    @NotNull
    @Column(name = "Horario", nullable = false, length = 15)
    private String horario;
    
    @NotNull
    @Column(name = "DataConsulta", nullable = false, length = 15)
    private String dataConsulta;

    // Construtor padrão
    public Consulta() {
    }

    // Getters e Setters
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    // hashCode e equals para garantir a unicidade
    @Override
    public int hashCode() {
        return Objects.hash(paciente.getCPF(), medico.getCRM(), horario, dataConsulta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Consulta that = (Consulta) obj;
        return Objects.equals(paciente.getCPF(), that.paciente.getCPF()) &&
               Objects.equals(medico.getCRM(), that.medico.getCRM()) &&
               Objects.equals(horario, that.horario) &&
               Objects.equals(dataConsulta, that.dataConsulta);
    }
}
