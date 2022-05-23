package com.example.demo.mapper;

import com.example.demo.dto.OptionDto;
import com.example.demo.dto.CreateGameRequestDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.model.Option;
import com.example.demo.model.Game;
import com.example.demo.model.Question;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public Game toGameData(CreateGameRequestDto createGameData) {
        Game game = new Game();
        game.setId(createGameData.getId());
        game.setAdminName(createGameData.getAdminName());
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
        question.setOptionList(
                questionDto.getOptionList()
                        .stream()
                        .map(this::toOption)
                        .collect(Collectors.toList())
        );
        return question;
    }

    private Option toOption(OptionDto optionDto) {
        Option option = new Option();
        option.setOrder(optionDto.getOrder());
        option.setOption(optionDto.getOption());
        option.setCorrect(Boolean.TRUE.equals(optionDto.getIsCorrect()));
        return option;
    }
}
