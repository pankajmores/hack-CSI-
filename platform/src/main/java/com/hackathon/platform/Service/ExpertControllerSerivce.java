package com.hackathon.platform.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hackathon.platform.DTO.LoginDto;
import com.hackathon.platform.DTO.SignupDto;
import com.hackathon.platform.POJOS.Expert;
import com.hackathon.platform.POJOS.Roles;
import com.hackathon.platform.Repositry.ExpertRepo;

import io.jsonwebtoken.lang.Collections;

@Service
public class ExpertControllerSerivce {

    @Autowired
    private  ExpertRepo  expertRepo;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;


    public  void SignUp(SignupDto signupDto) {
            Optional<Expert>  optional   =  expertRepo.findByEmail(signupDto.getEmail());
            if(optional.isPresent()) {
                throw new  RuntimeException("User  EXists  use  anoter  Email ");
            }
            Expert  expert   =  new Expert();
            expert.setExpertId(UUID.randomUUID().toString());
            expert.setEmail(signupDto.getEmail());
            expert.setName(signupDto.getName());
            expert.setPassword(passwordEncoder.encode(signupDto.getPassword()));
             expert.setRoles(new  HashSet<>(Collections.setOf(Roles.Expert)));
            expertRepo.save(expert);

    }
    public boolean Login(LoginDto dto) {
        Optional<Expert> optional = expertRepo.findByEmail(dto.getEmail());
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
