package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Answer {
    @Field(name="order")
    private int order;
    @Field(name="answer")
    private String answer;
    @Field(name="isCorrect")
    private boolean isCorrect;
}
