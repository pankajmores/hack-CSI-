package com.hackathon.platform.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hackathon.platform.POJOS.Expert;
import com.hackathon.platform.Repositry.ExpertRepo;

@Service
public class ExpertService implements  UserDetailsService {

    @Autowired
    private  ExpertRepo expertRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Expert> expert =  expertRepo.findByEmail(username);
        if(!expert.isPresent()) {
            throw new  UsernameNotFoundException("Hey  Expert Not  Exist");
        }
        return  expert.get();
        
    }

}
