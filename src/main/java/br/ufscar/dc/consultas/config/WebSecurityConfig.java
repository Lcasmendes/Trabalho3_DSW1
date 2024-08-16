package br.ufscar.dc.consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.ufscar.dc.consultas.security.PacienteDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return (UserDetailsService) new PacienteDetailsServiceImpl();
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//
//		return authProvider;
//	}
//
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
//
//
////    @SuppressWarnings("deprecation")
////	protected void configure(HttpSecurity http) throws Exception {
////        http
////            .authorizeRequests()
////                .requestMatchers("/login", "/css/", "/js/", "/images/**","/medicos/home").permitAll()
////                .anyRequest().authenticated()
////                .and()
////            .formLogin()
////                .loginPage("/login")
////                .permitAll()
////                .and()
////            .logout()
////                .permitAll();
////    }
//    
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests((authz) -> authz
//                .requestMatchers("/error", "/login/**", "/js/**", "/home/**", "/medicos/**").permitAll()
//                .requestMatchers("/css/**", "/image/**", "/webjars/**").permitAll()
//                .requestMatchers("/pacientes/**").hasRole("PACIENTE")
//                //.requestMatchers("/medicos/**").hasRole("MEDICO")
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated())
//            .formLogin((form) -> form
//                .loginPage("/login")
//                .usernameParameter("Email") // Parâmetro de nome de usuário personalizado
//                .passwordParameter("Senha") // Parâmetro de senha personalizado
//                .defaultSuccessUrl("/home", true)
//                .permitAll())
//            .logout((logout) -> logout
//                .logoutSuccessUrl("/").permitAll());
//
//        return http.build();
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new PacienteDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login", "/css/", "/js/", "/images/**", "/medicos/home").permitAll()
                .requestMatchers("/consultas/**").hasRole("PACIENTE")
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();
    }
}
