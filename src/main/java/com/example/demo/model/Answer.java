package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public class Answer {

    @Field(name = "questionOrder")
    private int questionOrder;
    @Field(name = "chosenOption")
    private int chosenOption; // todo limit to 4
    @Field(name = "answerDate")
    private LocalDateTime answerDate;
    @Field(name = "isCorrect")
    private boolean isCorrect;

}
