package com.hackathon.platform.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.hackathon.platform.POJOS.Problem;
import com.hackathon.platform.Service.Problem_Service;

import java.util.Map;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/problem")
public class Problem_Controller {

    @Autowired
    private  Problem_Service  problem_Service;
    @GetMapping("/title")
    public ResponseEntity<Object> problem(@RequestParam String param) {
        Object  get_Problem =  problem_Service.findbyTitle(param);
        if(get_Problem == null) {
            return  new  ResponseEntity<>("Not  Found ",HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(get_Problem,HttpStatus.OK);
    }
    
    @PostMapping("/")
    public String postMethodName(@RequestBody Problem entity) {
        //TODO: process POST request
        problem_Service.save(entity);
        return "added";
    }

    @PostMapping(value = "/title/{title}/submit")
public String submit(@PathVariable("title") String title, 
                     @RequestBody String  code) {
    // Find the problem by title
    Problem problem = problem_Service.findbyTitle(title);
    
    // Check if the problem exists
    if (problem != null) {
        // Extract 'code' from the map
      
        
        // Check if 'code' is valid
        if (code != null && !code.isEmpty()) {
            try {
                // Process the code (AI evaluation, etc.)
                return problem_Service.evalute_code(code,problem);
            } catch (Exception e) {
                // Handle exception and return error message
                return "Error processing the code: " + e.getMessage();
            }
        } else {
            return "Code is missing or empty.";
        }
    }
    
    // Return a response if the problem is not found
    return "Problem not found.";
}
    
    
}
