package com.hackathon.platform.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.platform.DTO.LoginDto;
import com.hackathon.platform.DTO.SignupDto;
import com.hackathon.platform.Service.ExpertControllerSerivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/expert")
public class ExpertController {
//we KNow about  that  some  of  the   Things are  nnot  done  in  the   industry  standars  but  we  are  unabble  to  implelemt  then 
    @Autowired 
    private  ExpertControllerSerivce  expertControllerSerivce;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto entity) {
        expertControllerSerivce.SignUp(entity);
        return ResponseEntity.ok("Signup successful");
    }
    
    @PostMapping("/login")
    public ResponseEntity<String>  postMethodName(@RequestBody LoginDto loginDto) {
        //TODO: process POST request
        Boolean  auth=  expertControllerSerivce.Login(loginDto);
        if(auth) return  new ResponseEntity<>(auth.toString(auth), HttpStatus.FOUND);
        else  return  new ResponseEntity<>(auth.toString(auth), HttpStatus.FORBIDDEN);
    }
    
    
}
