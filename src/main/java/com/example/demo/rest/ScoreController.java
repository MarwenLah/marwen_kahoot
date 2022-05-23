package com.example.demo.rest;

import com.example.demo.dto.AnswerQuestionRequestDto;
import com.example.demo.dto.JoinGameRequestDto;
import com.example.demo.service.ParticipationService;
import com.example.demo.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
}
