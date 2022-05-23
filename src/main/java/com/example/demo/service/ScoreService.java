package com.example.demo.service;

import com.example.demo.model.Answer;

public interface ScoreService {

    void compute(String gameId, String participantId, int streak, Answer answer);
}
