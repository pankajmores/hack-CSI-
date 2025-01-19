package com.hackathon.platform.Repositry;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.platform.POJOS.Intern;
import java.util.List;


@Repository
public interface InterRepo  extends  MongoRepository<Intern,String>{
    Optional<Intern>  findByEmail(String email);
}
