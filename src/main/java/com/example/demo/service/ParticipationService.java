package com.example.demo.service;

import com.example.demo.dto.AnswerQuestionRequestDto;
import com.example.demo.dto.JoinGameRequestDto;

public interface ParticipationService {
    void answerQuestion(AnswerQuestionRequestDto requestDto);

    void joinGame(JoinGameRequestDto requestDto);
}
