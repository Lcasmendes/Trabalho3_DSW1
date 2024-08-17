package br.ufscar.dc.consultas.security;

import java.util.Arrays;
import java.util.Collection;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.consultas.domain.Paciente;


 
@SuppressWarnings("serial")
public class PacienteDetails implements UserDetails {
 
    private Paciente paciente;
     
    public PacienteDetails(Paciente paciente) {
        this.paciente = paciente;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(paciente.getRole());
        return Arrays.asList(authority);
    }
 
    @Override
    public String getPassword() {
        return paciente.getSenha();
    }
 
    @Override
    public String getUsername() {
        return paciente.getEmail();
    }
    
    public String getCpf() {
        return paciente.getCPF();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }

	public Paciente getUsuario() {
		return paciente;
	}
    
}