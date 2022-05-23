package com.example.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("reactionTime")
public class ReactionTimeProperty {
    private double slope;
    private double initialCondition;
}
