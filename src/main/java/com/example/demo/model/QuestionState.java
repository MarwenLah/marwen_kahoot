package com.example.demo.model;

public enum QuestionState {
    NOT_ASKED_YET,
    ONGOING,
    ANSWERED;

    public boolean isNotAskedYet() {
        return this == NOT_ASKED_YET;
    }

    public boolean isOngoing() {
        return this == ONGOING;
    }

    public boolean isAnswered() {
        return this == ANSWERED;
    }

}
