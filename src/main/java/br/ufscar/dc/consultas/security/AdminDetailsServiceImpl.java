package br.ufscar.dc.consultas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.consultas.dao.AdminDAO;
import br.ufscar.dc.consultas.domain.Admin;
 
public class AdminDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AdminDAO dao;
     
    @Override
    public UserDetails loadUserByUsername(String Email)
            throws UsernameNotFoundException {
    	Admin admin = dao.findByEmail(Email);
         
        if (admin == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new AdminDetails(admin);
    }
}