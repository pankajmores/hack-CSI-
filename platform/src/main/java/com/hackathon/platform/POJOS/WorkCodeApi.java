package com.hackathon.platform.POJOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkCodeApi {
    private String question;
    private String model;
    private double randomness;
    private boolean stream_data;
    private String training_data;
    private String response_type;
}
