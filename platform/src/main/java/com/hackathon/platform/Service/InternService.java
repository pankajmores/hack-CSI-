package com.hackathon.platform.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hackathon.platform.POJOS.Intern;
import com.hackathon.platform.Repositry.InterRepo;

@Service
public class InternService implements  UserDetailsService {

    @Autowired
    private InterRepo interRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Intern>  optional   =  interRepo.findByEmail(username);
        if(!optional.isPresent()) {
            throw new  UsernameNotFoundException("Hey  Expert Not  Exist");
        }
        return  optional.get();
        
        
    }

}
