package br.ufscar.dc.consultas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.domain.Medico;
 
public class MedicoDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private MedicoDAO dao;
     
    @Override
    public UserDetails loadUserByUsername(String Email)
            throws UsernameNotFoundException {
    	Medico medico = dao.findByEmail(Email);
         
        if (medico == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new MedicoDetails(medico);
    }
}