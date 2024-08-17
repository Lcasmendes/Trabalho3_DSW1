package br.ufscar.dc.consultas.security;

import java.util.Arrays;
import java.util.Collection;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.consultas.domain.Medico;


 
@SuppressWarnings("serial")
public class MedicoDetails implements UserDetails {
 
    private Medico medico;
     
    public MedicoDetails(Medico medico) {
        this.medico = medico;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(medico.getRole());
        return Arrays.asList(authority);
    }
 
    @Override
    public String getPassword() {
        return medico.getSenha();
    }
 
    @Override
    public String getUsername() {
        return medico.getEmail();
    }
    
    public String getCrm() {
        return medico.getCRM();
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

	public Medico getUsuario() {
		return medico;
	}
    
}