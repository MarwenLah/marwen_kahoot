package com.example.demo.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("flatScore")
public class FlatScoreProperty {
    private double value;
}
