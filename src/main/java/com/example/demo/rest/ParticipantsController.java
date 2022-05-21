package com.example.demo.rest;

import com.example.demo.dto.JoinGameRequestDto;
import com.example.demo.service.ParticipationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ParticipantsController {

    private final ParticipationService participationService;

    @PostMapping("join")
    public void joinGame(@RequestBody JoinGameRequestDto requestDto) {
        participationService.joinGame(requestDto);
    }
}
