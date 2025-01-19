package com.hackathon.platform.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.platform.POJOS.Problem;
import com.hackathon.platform.POJOS.WorkCodeApi;
import com.hackathon.platform.POJOS.WorkCodeResponse;
import com.hackathon.platform.Repositry.Problem_Repo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Problem_Service {

    private final WebClient webClient;
    private final Problem_Repo problem_Repo;

    public  final  RestTemplate  restTemplate  =  new RestTemplate();
    // Inject WebClient and Problem_Repo via constructor
    @Autowired
    public Problem_Service(WebClient.Builder webClientBuilder, Problem_Repo problem_Repo) {
        this.webClient = webClientBuilder.baseUrl("http://external-api-base-url").build(); // Replace with actual base URL
        this.problem_Repo = problem_Repo;
    }

    @Value("${api.token}")
    private String apiToken;

    
    public Object get_all() {
        return  problem_Repo.findAll();
    }

    public  void  save(Problem problem) {
        problem_Repo.save(problem);
    }
    public  Problem  findbyTitle(String  title) {
        Optional<Problem>  optional =  problem_Repo.findByTitle(title);
        if(optional.isPresent()) return  optional.get();
        return  null; 
    }

    public  String  evalute_code(String  code,Problem  problem) throws JsonProcessingException {

        WorkCodeApi  payload  =  new WorkCodeApi();
        // Setting up the evaluation instructions.
        StringBuilder System__msg = new StringBuilder(
            "Evaluate the given code based on the following parameters:\n" +
            "1. Correctness: Check if the algorithm works correctly and if the code meets the expected output.\n" +
            "2. Code Quality: Review the code for readability, performance, and adherence to good coding practices.\n" +
            "3. Provide a simple, actionable summary with percentages for correctness and code quality.\n" +
            "4. Suggest improvements to enhance the code.\n" +
            "Output should be straightforward, highlighting the key points.\n" +
            "Please avoid complex explanations."

        );
// Constructing the evaluation question.
    StringBuilder Question = new StringBuilder();
    Question.append("Problem Title: ").append(problem.getTitle()).append("\n");
    Question.append("Provided Test Cases: ").append(problem.getTest_cases()).append("\n");
    Question.append("User Code: \n").append(code).append("\n");

    // Configuring the payload with simple evaluation output requirements.
    payload.setQuestion(Question.toString());
    payload.setModel("aicon-v4-nano-160824");
    payload.setRandomness(0.5);
    payload.setStream_data(false);
    payload.setTraining_data(System__msg.toString());
    payload.setResponse_type("text");

        String jsonPayload = new ObjectMapper().writeValueAsString(payload);
        String  Bearer =  "Bearer "+apiToken;
        log.info(Bearer);
        log.info(jsonPayload);

         String url = "https://api.worqhat.com/api/ai/content/v4";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);
        headers.set("Content-Type", "application/json");

        // Create the request entity
        HttpEntity<WorkCodeResponse> requestEntity = new HttpEntity(jsonPayload, headers);

        // Make the POST request
        ResponseEntity<WorkCodeResponse> response = restTemplate.exchange(
                url, 
                HttpMethod.POST, 
                requestEntity, 
                WorkCodeResponse.class
        );

        // Return the response body
        log.info(response.getBody().getContent());
        return response.getBody().getContent();

       
        
    }
}
