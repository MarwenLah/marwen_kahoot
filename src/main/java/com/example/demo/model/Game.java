package com.example.demo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Game {
    @Id
    private String id;
    @Field(name="admin")
    private String adminName;
    @Field(name="gameState")
    private GameState state = GameState.NOT_STARTED_YET;
    @Field(name="creationDate")
    private LocalDateTime creationDate;
    @Field(name="questionList")
    private List<Question> questionList;
}
