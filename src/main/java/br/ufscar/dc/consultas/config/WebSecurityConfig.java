package br.ufscar.dc.consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.ufscar.dc.consultas.security.AdminDetailsServiceImpl;
import br.ufscar.dc.consultas.security.MedicoDetails;
import br.ufscar.dc.consultas.security.MedicoDetailsServiceImpl;
import br.ufscar.dc.consultas.security.PacienteDetails;
import br.ufscar.dc.consultas.security.PacienteDetailsServiceImpl;

import java.io.IOException;
import java.util.Arrays;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService adminDetailsService() {
        return new AdminDetailsServiceImpl();
    }

    @Bean
    public UserDetailsService medicoDetailsService() {
        return new MedicoDetailsServiceImpl();
    }

    @Bean
    public UserDetailsService pacienteDetailsService() {
        return new PacienteDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider medicoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(medicoDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider pacienteAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(pacienteDetailsService());
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/medicos/home", "/login").permitAll() // Rotas públicas
                .requestMatchers("/medicos/**", "/pacientes/**", "/pacientes").hasRole("ADMIN")
                .requestMatchers("/consultas/consultas-medico").hasRole("MEDICO")
                .requestMatchers("/consultas/consultas-paciente", "/consultas/novaConsulta").hasRole("PACIENTE")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("Email")
                .passwordParameter("Senha")
                .successHandler((request, response, authentication) -> {
                    String role = authentication.getAuthorities().iterator().next().getAuthority();
                    String crm = "";
                    String cpf = ""; 
                    
                    if (role.equals("ROLE_MEDICO")) {
                        crm = ((MedicoDetails) authentication.getPrincipal()).getCrm();
                        response.sendRedirect("/consultas/consultas-medico?crm=" + crm);
                    } else if (role.equals("ROLE_ADMIN")) {
                        response.sendRedirect("/medicos/CRUD");
                    } else if (role.equals("ROLE_PACIENTE")) {
                    	cpf = ((PacienteDetails) authentication.getPrincipal()).getCpf();
                        response.sendRedirect("/consultas/consultas-paciente?cpf=" + cpf);
                    } else {
                        response.sendRedirect("/login?error");
                    }
                })
                .loginProcessingUrl("/login")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(
            adminAuthenticationProvider(),
            medicoAuthenticationProvider(),
            pacienteAuthenticationProvider()
        ));
    }
}
