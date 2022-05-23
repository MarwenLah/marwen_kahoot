package com.example.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("question")
public class QuestionProperty {
    private double expirationInSeconds;
}
