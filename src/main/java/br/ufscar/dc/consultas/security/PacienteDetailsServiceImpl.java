package br.ufscar.dc.consultas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.domain.Paciente;
 
public class PacienteDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private PacienteDAO dao;
     
    @Override
    public UserDetails loadUserByUsername(String Email)
            throws UsernameNotFoundException {
        Paciente paciente = dao.findByEmail(Email);
         
        if (paciente == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new PacienteDetails(paciente);
    }
}