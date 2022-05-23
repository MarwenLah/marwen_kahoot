package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateGameRequestDto {
    private String id;
    private String adminName;
    private List<QuestionDto> questionList;
}
