package com.example.demo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Option {
    @Field(name="order")
    private int order;
    @Field(name="option")
    private String option;
    @Field(name="isCorrect")
    private boolean isCorrect;
}
