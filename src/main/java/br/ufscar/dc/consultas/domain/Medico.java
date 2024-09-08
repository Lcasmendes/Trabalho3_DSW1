package br.ufscar.dc.consultas.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name = "medico")
public class Medico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Size(min=3, max=7)
	@Column(nullable = false, unique = true)
	private String crm; 

	@NotBlank
	@Size(min=3, max=60)
	@Column(nullable = false, length = 60)
    private String Nome;
	
	@NotBlank
    @Size(min = 4, max = 60)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com)$", message = "Email invalido")
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
	
    @NotBlank
    @Column(nullable = false, length = 30)
    private String role;
    

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
