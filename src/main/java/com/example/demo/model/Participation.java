package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
public class Participation {

    @Id
    private String id;
    @Field(name = "participantMap")
    private Map<String, Participant> participantMap;

}
