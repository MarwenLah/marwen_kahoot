package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerQuestionRequestDto {
    private String gameId;
    private String participantId;
    private int questionOrder;
    private int chosenOption;
}
