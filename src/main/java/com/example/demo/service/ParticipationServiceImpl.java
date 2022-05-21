package com.example.demo.service;

import com.example.demo.dto.AnswerQuestionRequestDto;
import com.example.demo.dto.JoinGameRequestDto;
import com.example.demo.model.*;
import com.example.demo.repository.ParticiptionRepository;
import com.example.demo.rest.exception.BadInputException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import static com.example.demo.model.GameState.hasStarted;

@Service
@AllArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final GameService gameService;
    private final ParticiptionRepository participationRepository;

    @Override
    public void answerQuestion(AnswerQuestionRequestDto requestDto) {
        String gameId = requestDto.getGameId();
        Participation participation = getParticipation(gameId);
        Participant participant = participation.getParticipantMap().get(requestDto.getParticipantId());
        if (participant == null) {
            throw new BadInputException("Participant does not exist");
        }
        boolean isQuestionAlreadyAnswered = participant.getAnswerList().stream()
                .anyMatch(e -> e.getQuestionOrder() == requestDto.getQuestionOrder());
        if (isQuestionAlreadyAnswered) {
            throw new BadInputException("Question already answered");
        }
        //todo take into account expiration date
        Answer answer = constructAnswer(requestDto);
        participant.getAnswerList().add(answer);
        participationRepository.save(participation);
    }

    @Override
    public void joinGame(JoinGameRequestDto requestDto) {
        String gameId = requestDto.getGameId();
        Game game = gameService.getGame(gameId);
        if (hasStarted(game)) {
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

    private Answer constructAnswer(AnswerQuestionRequestDto requestDto) {
        String gameId = requestDto.getGameId();
        int questionOrder = requestDto.getQuestionOrder();
        int chosenOption = requestDto.getChosenOption();
        boolean isCorrect = isAnswerCorrect(gameId, questionOrder, chosenOption);

        Answer answer = new Answer();
        answer.setAnswerDate(LocalDateTime.now());
        answer.setQuestionOrder(questionOrder);
        answer.setChosenOption(chosenOption);
        answer.setCorrect(isCorrect);
        return answer;
    }

    private boolean isAnswerCorrect(String gameId, int questionOrder, int chosenOption) {
        Game game = gameService.getGame(gameId);
        Question question = game.getQuestionList().get(questionOrder - 1);
        Option option = question.getOptionList().get(chosenOption - 1);
        return option.isCorrect();
    }

}
