package com.hackathon.platform.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hackathon.platform.DTO.LoginDto;
import com.hackathon.platform.DTO.SignupDto;

import com.hackathon.platform.POJOS.Intern;
import com.hackathon.platform.POJOS.Roles;

import com.hackathon.platform.Repositry.InterRepo;

import io.jsonwebtoken.lang.Collections;

@Service
public class InterControllerService {

        @Autowired
    private  InterRepo  interRepo;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    public  void SignUp(SignupDto signupDto) {
            Optional<Intern>  optional   =  interRepo.findByEmail(signupDto.getEmail());
            if(optional.isPresent()) {
                throw new  RuntimeException("User  EXists  use  anoter  Email ");
            }
            Intern  intern   =  new Intern();
            intern.setInternId(UUID.randomUUID().toString());
            intern.setEmail(signupDto.getEmail());
            intern.setName(signupDto.getName());
            intern.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            intern.setRoles(new  HashSet<>(Collections.setOf(Roles.Intern)));

            interRepo.save(intern);

    }
    public boolean Login(LoginDto dto) {
        Optional<Intern> optional = interRepo.findByEmail(dto.getEmail());
        if (!optional.isPresent()) return false; // User not found
    
        // Use passwordEncoder to match the raw password with the encoded one
        boolean  login =  passwordEncoder.matches(dto.getPassword(), optional.get().getPassword());
        
        if (login) {
            // Create an authentication object
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(
                    dto.getEmail(), // Principal
                    dto.getPassword()// Authorities can be added if roles exist
                );
    
            // Set the authentication object in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            return true; // Login successful
        }
    
        return false;
    }
}
