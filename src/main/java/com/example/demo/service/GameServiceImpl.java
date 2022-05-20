package com.example.demo.service;

import com.example.demo.dto.CreateGameRequestDto;
import com.example.demo.dto.EndGameRequestDto;
import com.example.demo.dto.StartGameRequestDto;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.Game;
import com.example.demo.model.GameState;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionState;
import com.example.demo.repository.GameRepository;
import com.example.demo.rest.exception.BadInputException;
import com.example.demo.rest.exception.NotFoundException;
import com.google.common.collect.Iterables;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final Mapper mapper;

    @Override
    public void createGame(CreateGameRequestDto createGameData) {
        Game game = mapper.toGameData(createGameData);
        gameRepository.save(game);
    }

    @Override
    public void startGame(StartGameRequestDto requestDto) {
        Game game = getGame(requestDto.getId());
        triggerNextQuestion(requestDto.getId());
        game.setState(GameState.ONGOING);
        gameRepository.save(game);
    }

    @Override
    public void triggerNextQuestion(String id) {
        Game game = getGame(id);
        triggerNextQuestion(game);
        gameRepository.save(game);
    }

    @Override
    public void endGame(EndGameRequestDto requestDto) {
        Game game = getGame(requestDto.getId());
        setLastQuestionToAnswered(game);
        game.setState(GameState.ENDED);
        gameRepository.save(game);
    }

    private void setLastQuestionToAnswered(Game game) {
        Question lastQuestion = Iterables.getLast(game.getQuestionList());
        boolean isLastQuestionOngoing = lastQuestion.getState() == QuestionState.ONGOING;
        if (isLastQuestionOngoing) {
            setAnswered(lastQuestion);
        }
        throw new BadInputException("Last question is not ongoing on this game : " + game.getId());
    }

    private void triggerNextQuestion(Game game) {
        List<Question> questionList = game.getQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            Question currentQuestion = questionList.get(i);
            boolean currentQuestionNotAskedYet = currentQuestion.getState() == QuestionState.NOT_ASKED_YET;
            if (i == 0) {
                if (currentQuestionNotAskedYet) {
                    setOngoing(currentQuestion);
                    break;
                }
            } else {
                Question beforeQuestion = questionList.get(i - 1);
                boolean lastQuestionOngoing = beforeQuestion.getState() == QuestionState.ONGOING;
                if (lastQuestionOngoing && currentQuestionNotAskedYet) {
                    setAnswered(beforeQuestion);
                    setOngoing(currentQuestion);
                    break;
                }
            }
        }
        throw new BadInputException("Cannot trigger next question on this game : " + game.getId());
    }

    private void setAnswered(Question question) {
        question.setState(QuestionState.ANSWERED);
    }

    private void setOngoing(Question question) {
        question.setState(QuestionState.ONGOING);
        question.setExpirationDate(LocalDateTime.now());
    }

    private Game getGame(String requestDto) {
        return gameRepository.findById(requestDto)
                .orElseThrow(() -> new NotFoundException("Game not found for id " + requestDto));
    }

}
