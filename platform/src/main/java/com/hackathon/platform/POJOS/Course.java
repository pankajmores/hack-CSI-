package com.hackathon.platform.POJOS;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("Course")
public  class Course {

    @Id
    private  String  courseId; 
    private String  courseName;
    private String  courseTitle;
    private  String courseDescription;
    private  String  video_link;
    
    private List<Expert>  experts;
    private  List<Intern>  interns;

    private List<Problem>  problems;

}
