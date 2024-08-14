package br.ufscar.dc.consultas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String CPF;

    @NotBlank
    @Size(min = 3, max = 60)
    @Column(nullable = false, length = 60)
    private String Nome;

    @NotBlank
    @Column(nullable = false, length = 60, unique = true)
    private String Email;

    @NotBlank
    @Size(min = 4, max = 60)
    @Column(nullable = false, length = 60)
    private String Senha;

    @NotBlank
    @Size(min = 3, max = 60)
    @Column(nullable = false, length = 15)
    private String Telefone;

    @NotBlank
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String Sexo;

    @NotBlank
    @Size(min = 10, max = 10)
    @Column(nullable = false, length = 10)
    private String Data_nascimento;

    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas;

    // Construtor padrão
    public Paciente() {
    }

    // Getters e Setters
    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getData_nascimento() {
        return Data_nascimento;
    }

    public void setData_nascimento(String datanascimento) {
        Data_nascimento = datanascimento;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
