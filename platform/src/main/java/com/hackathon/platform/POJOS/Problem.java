package com.hackathon.platform.POJOS;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document("Problem_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    private  String  id;

    private  String  problem_statement;

    private  String  title;

    private  String  test_cases;

    //private  LocalDate date;
}
