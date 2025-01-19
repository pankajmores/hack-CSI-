package com.hackathon.platform.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hackathon.platform.Service.ExpertService;
import com.hackathon.platform.Service.InternService;

@Configuration
public class SecurityCongiguration {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private InternService internService;

    @Bean
    public  AuthenticationManager  authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider expertAuthenticationProvider() {
        DaoAuthenticationProvider  daoAuthenticationProvider =  new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(expertService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;

    }
    @Bean
    public AuthenticationProvider internAuthenticationProvider(){
        DaoAuthenticationProvider  daoAuthenticationProvider =  new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(internService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public  PasswordEncoder passwordEncoder() {
        return  new  BCryptPasswordEncoder();
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
            csrf(csrf->{
                csrf.disable();
            })
            .authorizeHttpRequests(authorizeHttpRequests-> {
                //when we   scale  use form the  App  Constans
               // authorizeHttpRequests.requestMatchers("/expert/**").permitAll();
                authorizeHttpRequests.anyRequest().permitAll();
            })
            
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            return  httpSecurity.build();

    }
}
