package com.hackathon.platform.Repositry;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hackathon.platform.POJOS.Course;

public interface CourseRepo extends MongoRepository<Course,String> {

}
