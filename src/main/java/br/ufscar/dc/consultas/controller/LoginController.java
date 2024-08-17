package br.ufscar.dc.consultas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class LoginController {

    @RequestMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ADMIN")) {
            return "redirect:/medicos/CRUD";
        } else if (roles.contains("MEDICO")) {
            return "redirect:/consultas/consultas-medico";
        } else if (roles.contains("PACIENTE")) {
            return "redirect:/consultas/consultas-paciente";
        }
        return "redirect:/login";
    }
}
