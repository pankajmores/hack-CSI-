package com.hackathon.platform.Repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hackathon.platform.POJOS.Problem;
import java.util.List;


public interface Problem_Repo extends MongoRepository<Problem,String>{

    Optional<Problem>  findByTitle(String title);

}
