package com.example.demo.rest;

import com.example.demo.dto.CreateGameRequestDto;
import com.example.demo.dto.EndGameRequestDto;
import com.example.demo.dto.StartGameRequestDto;
import com.example.demo.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/create")
    public void createGame(@RequestBody CreateGameRequestDto requestDto) {
        gameService.createGame(requestDto);
    }

    @PostMapping("/start")
    public void startGame(@RequestBody StartGameRequestDto requestDto) { // todo add authentication
        gameService.startGame(requestDto);
    }

    @PostMapping("/question/next")
    public void startGame(@RequestParam String id) {
        gameService.triggerNextQuestion(id);
    }

    @PostMapping("/end")
    public void endGame(@RequestBody EndGameRequestDto requestDto) {
        gameService.endGame(requestDto);
    }

}
