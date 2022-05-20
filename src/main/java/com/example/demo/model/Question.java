package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Question {
    @Field(name = "order")
    private int order;
    @Field(name = "question")
    private String question;
    @Field(name = "expirationDate")
    private LocalDateTime expirationDate;
    @Field(name = "state")
    private QuestionState state = QuestionState.NOT_ASKED_YET;
    @Field(name = "answerList")
    private List<Answer> answerList;
}
