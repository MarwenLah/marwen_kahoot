package com.example.demo.mapper;

import com.example.demo.dto.AnswerDto;
import com.example.demo.dto.CreateGameRequestDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.model.Answer;
import com.example.demo.model.Game;
import com.example.demo.model.Question;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public Game toGameData(CreateGameRequestDto createGameData) {
        Game game = new Game();
        game.setId(createGameData.getId());
        game.setAdminName(createGameData.getAdminName());
        LocalDateTime creationDate = LocalDateTime.parse(createGameData.getCreationDate(), FORMATTER);
        game.setCreationDate(creationDate);
        game.setQuestionList(
                createGameData.getQuestionList()
                        .stream()
                        .map(this::toQuestion)
                        .collect(Collectors.toList())
        );
        return game;
    }

    private Question toQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setOrder(questionDto.getOrder());
        question.setQuestion(questionDto.getQuestion());
        question.setAnswerList(
                questionDto.getAnswerList()
                        .stream()
                        .map(this::toAnswer)
                        .collect(Collectors.toList())
        );
        return question;
    }

    private Answer toAnswer(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setOrder(answerDto.getOrder());
        answer.setAnswer(answerDto.getAnswer());
        answer.setCorrect(Boolean.TRUE.equals(answerDto.getIsCorrect()));
        return answer;
    }
}
