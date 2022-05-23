package com.example.demo.service;

import com.example.demo.dto.AnswerQuestionRequestDto;
import com.example.demo.dto.JoinGameRequestDto;
import com.example.demo.model.*;
import com.example.demo.repository.ParticiptionRepository;
import com.example.demo.rest.exception.BadInputException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final GameService gameService;
    private final ParticiptionRepository participationRepository;

    @Override
    public void answerQuestion(AnswerQuestionRequestDto requestDto) {
        LocalDateTime answerDate = LocalDateTime.now();
        String gameId = requestDto.getGameId();
        Participation participation = getParticipation(gameId);

        Participant participant = getParticipant(requestDto, participation);
        validateParticipant(participant);

        int questionOrder = requestDto.getQuestionOrder();
        validateNotAlreadyAnswered(participant, questionOrder);

        Game game = gameService.getGame(gameId);
        validateGameIsOngoing(game);

        Question question = getQuestion(game, questionOrder);
        validateQuestionIsOngoing(question);

        LocalDateTime expirationDate = question.getExpirationDate();
        validateQuestionNotExpired(answerDate, expirationDate);

        Answer answer = constructAnswer(requestDto, answerDate);
        participant.getAnswerList().add(answer);

        setStreak(participant, answer.isCorrect());

        participationRepository.save(participation);
    }

    private void setStreak(Participant participant, boolean isAnswerCorrect) {
        int oldStreak = participant.getStreak();
        int newStreak = isAnswerCorrect ?  oldStreak + 1 : 0;
        participant.setStreak(newStreak);
    }

    @Override
    public void joinGame(JoinGameRequestDto requestDto) {
        String gameId = requestDto.getGameId();
        Game game = gameService.getGame(gameId);
        if (game.getState().hasStarted()) {
            throw new BadInputException("Game did already start for id : " + gameId);
        }
        Participation participation = getOrCreateParticipation(gameId);
        addNewParticipant(participation, requestDto.getParticipantId());
        participationRepository.save(participation);
    }

    private void addNewParticipant(Participation participation, String participantId) {
        boolean participantIdAlreadyJoined = participation.getParticipantMap().containsKey(participantId);
        if (participantIdAlreadyJoined) {
            throw new BadInputException("Participant already exists");
        }
        Participant newParticipant = new Participant();
        newParticipant.setId(participantId);
        newParticipant.setAnswerList(new ArrayList<>());
        participation.getParticipantMap().put(participantId, newParticipant);
    }

    private Participation getParticipation(String gameId) {
        return participationRepository.findById(gameId)
                .orElseThrow(() -> new BadInputException("No participation for game id : " + gameId));
    }

    private Participation getOrCreateParticipation(String gameId) {
        Optional<Participation> participationOpt = participationRepository.findById(gameId);
        if (participationOpt.isPresent()) {
            return participationOpt.get();
        }
        Participation newParticipation = new Participation();
        newParticipation.setId(gameId);
        newParticipation.setParticipantMap(new HashMap<>());
        return newParticipation;
    }

    private Answer constructAnswer(AnswerQuestionRequestDto requestDto, LocalDateTime answerDate) {
        String gameId = requestDto.getGameId();
        int questionOrder = requestDto.getQuestionOrder();
        int chosenOption = requestDto.getChosenOption();
        boolean isCorrect = isAnswerCorrect(gameId, questionOrder, chosenOption);

        Answer answer = new Answer();
        answer.setAnswerDate(answerDate);
        answer.setQuestionOrder(questionOrder);
        answer.setChosenOption(chosenOption);
        answer.setCorrect(isCorrect);
        return answer;
    }

    private boolean isAnswerCorrect(String gameId, int questionOrder, int chosenOption) {
        Game game = gameService.getGame(gameId);
        Question question = getQuestion(game, questionOrder);
        Option option = question.getOptionList().get(chosenOption - 1);
        return option.isCorrect();
    }
    private void validateQuestionNotExpired(LocalDateTime answerDate, LocalDateTime expirationDate) {
        if (answerDate.isAfter(expirationDate)) {
            throw new BadInputException("Question already expired");
        }
    }

    private void validateQuestionIsOngoing(Question question) {
        if (!question.getState().isOngoing()) {
            throw new BadInputException("Question must be ongoing");
        }
    }

    private void validateGameIsOngoing(Game game) {
        if(!game.getState().isOngoing()) {
            throw new BadInputException("Game must be ongoing");
        }
    }

    private void validateNotAlreadyAnswered(Participant participant, int questionOrder) {
        boolean isQuestionAlreadyAnswered = participant.getAnswerList().stream()
                .anyMatch(e -> e.getQuestionOrder() == questionOrder);
        if (isQuestionAlreadyAnswered) {
            throw new BadInputException("Question already answered");
        }
    }

    private void validateParticipant(Participant participant) {
        if (participant == null) {
            throw new BadInputException("Participant does not exist");
        }
    }

    private Participant getParticipant(AnswerQuestionRequestDto requestDto, Participation participation) {
        return participation.getParticipantMap().get(requestDto.getParticipantId());
    }

    private Question getQuestion(Game game, int questionOrder) {
        return game.getQuestionList().get(questionOrder - 1);
    }

}
