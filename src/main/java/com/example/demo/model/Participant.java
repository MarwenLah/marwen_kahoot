package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Participant {

    @Field(name = "id")
    private String id;
    @Field(name = "answerList")
    private List<Answer> answerList; //todo lock answers only when save
    @Field(name = "streak")
    private int streak;

}
