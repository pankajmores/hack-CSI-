package com.hackathon.platform.POJOS;

import lombok.Data;

@Data
public class WorkCodeResponse {

    /**
     * The response output of the processing.
     */
    private String content;
    /**
     * A unique identifier for the Message. You can use this conversation id later when building
     * multiturn chat applications. This conversation id is what keeps a track of your past
     * conversation. You can use this to keep continuing your requests one after the other
     * without the hassle of maintaining the conversation history on your own.
     */
    private String conversationid;
    /**
     * The model used for the process.
     */
    private String model;
    /**
     * Usage statistics for the request. This is what is used for the billing.
     */
    private long processingCount;
    /**
     * A unique identifier for the server process. This helps us track support requests and
     * complaints.
     */
    private String processingId;
    /**
     * The amount of time in miliseconds it took to complete the request.
     */
    private double processingTime;
}


