package com.example.demo.service;

import com.example.demo.model.Answer;
import com.example.demo.model.Participant;
import com.example.demo.property.FlatScoreProperty;
import com.example.demo.property.QuestionProperty;
import com.example.demo.property.ReactionTimeProperty;
import com.example.demo.property.StreakProperty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final StreakProperty streakProperty;
    private final ReactionTimeProperty reactionTimeProperty;
    private final QuestionProperty questionProperty;
    private final FlatScoreProperty flatScoreProperty;

    public void compute(String gameId, String participantId, int streak, Answer answer) {
        if (!answer.isCorrect()){
            return;
        }
        double lastScore = 0; //todo add
        double flatScore = flatScoreProperty.getValue();
        double streakScore = streakProperty.getValues()[streak - 1];
        //todo add the reaction time logic
    }

    private double calculateFlatScore(Participant participant) {
        return participant.getAnswerList().stream().filter(Answer::isCorrect).count()
                * flatScoreProperty.getValue();
    }

    ;
}
