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
        game.setCreationDate(LocalDateTime.now());
        gameRepository.save(game);
    }

    @Override
    public void startGame(StartGameRequestDto requestDto) {
        Game game = getGame(requestDto.getId());
        triggerNextQuestion(game);
        game.setState(GameState.ONGOING);
        gameRepository.save(game);
    }

    @Override
    public void triggerNextQuestion(String id) {
        Game game = getGame(id);
        validateGameIsOngoing(game);
        triggerNextQuestion(game);
        gameRepository.save(game);
    }

    private void validateGameIsOngoing(Game game) {
        if (game.getState() != GameState.ONGOING) {
            throw new BadInputException("Cannot trigger next question if game is not ongoing : " + game.getId());
        }
    }

    @Override
    public void endGame(EndGameRequestDto requestDto) {
        Game game = getGame(requestDto.getId());
        setLastQuestionToAnswered(game);
        game.setState(GameState.ENDED);
        gameRepository.save(game);
    }

    @Override
    public Game getGame(String gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException("Game not found for id " + gameId));
    }

    private void setLastQuestionToAnswered(Game game) {
        Question lastQuestion = Iterables.getLast(game.getQuestionList());
        boolean isLastQuestionOngoing = lastQuestion.getState() == QuestionState.ONGOING;
        if (isLastQuestionOngoing) {
            setAnswered(lastQuestion);
            return;
        }
        throw new BadInputException("Last question is not ongoing on this game : " + game.getId());
    }

    private void triggerNextQuestion(Game game) {
        List<Question> questionList = game.getQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            Question currentQuestion = questionList.get(i);
            boolean currentQuestionNotAskedYet = currentQuestion.getState() == QuestionState.NOT_ASKED_YET;
            if (i == 0 && currentQuestionNotAskedYet) {
                setOngoing(currentQuestion);
                return;
            } else if (i != 0) {
                Question beforeQuestion = questionList.get(i - 1);
                boolean beforeQuestionOngoing = beforeQuestion.getState() == QuestionState.ONGOING;
                if (beforeQuestionOngoing && currentQuestionNotAskedYet) {
                    setAnswered(beforeQuestion);
                    setOngoing(currentQuestion);
                    return;
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
        question.setExpirationDate(LocalDateTime.now().plusSeconds(300)); // todo make a config
    }

}
