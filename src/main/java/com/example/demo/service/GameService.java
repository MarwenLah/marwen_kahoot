package com.example.demo.service;

import com.example.demo.dto.CreateGameRequestDto;
import com.example.demo.dto.EndGameRequestDto;
import com.example.demo.dto.StartGameRequestDto;
import com.example.demo.model.Game;

public interface GameService {

    void createGame(CreateGameRequestDto createGameData);

    void startGame(StartGameRequestDto requestDto);

    void triggerNextQuestion(String id);

    void endGame(EndGameRequestDto requestDto);

    Game getGame(String gameId);
}
