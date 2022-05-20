package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Answer {
    @Field(name="answerOrder")
    private int answerOrder;
    @Field(name="answer")
    private String answer;
    @Field(name="isCorrect")
    private boolean isCorrect;
}
