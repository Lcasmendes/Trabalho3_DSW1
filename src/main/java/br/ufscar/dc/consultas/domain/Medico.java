package br.ufscar.dc.consultas.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "medico")
public class Medico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private String crm; 

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
		this.Nome = nome;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getSenha() {
		return Senha;
	}

	public void setSenha(String senha) {
		this.Senha = senha;
	}

	public String getEspecialidade() {
		return Especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.Especialidade = especialidade;
	}
	
	public String getCRM() {
		return crm;
	}
	
	public void setCRM(String CRM) {
        this.crm = CRM;
    }
}
