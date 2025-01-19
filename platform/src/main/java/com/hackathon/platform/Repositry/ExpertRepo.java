package com.hackathon.platform.Repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.platform.POJOS.Expert;

@Repository
public interface ExpertRepo extends MongoRepository<Expert,String>{
    Optional<Expert>  findByEmail(String  email);
}
