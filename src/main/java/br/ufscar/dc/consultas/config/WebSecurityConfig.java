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
                .anyRequest().permitAll() // Permitir todas as rotas sem autenticação
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("Email")
                .passwordParameter("Senha")
                .loginProcessingUrl("/login")
                .permitAll() // Permitir acesso à página de login sem autenticação
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll() // Permitir logout sem autenticação
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
