package br.ufscar.dc.consultas.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "medico")
public class Medico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotBlank
	@Size(min=1, max=6)
	@Column(nullable = false, unique = true, length = 60)
    private Long CRM;

	@NotBlank
	@Size(min=3, max=60)
	@Column(nullable = false, length = 60)
    private String Nome;
	
	@NotBlank
	@Column(nullable = false, length = 60, unique = true)
    private String Email;
	
	@NotBlank
	@Size(min=4, max=60)
	@Column(nullable = false, length = 60)
    private String Senha;
	
	@NotBlank
	@Size(min=3, max=60)
	@Column(nullable = false, length = 60)
    private String Especialidade;

	@OneToMany(mappedBy = "medico")
	private List<Consulta> consultas;
	
    // Construtor padrão
    public Medico() {
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

	public String getEspecialidade() {
		return Especialidade;
	}

	public void setEspecialidade(String especialidade) {
		Especialidade = especialidade;
	}
	
	public Long getCRM() {
		return CRM;
	}

}
