package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto {
    private int questionOrder;
    private String question; // limit length
    private List<AnswerDto> answerList;  // force number to x in config
}
