package com.example.demo.model;

public enum GameState {
    NOT_STARTED_YET,
    ONGOING,
    ENDED;

    public boolean hasStarted() {
        return this != NOT_STARTED_YET;
    }
    public boolean hasNotStartedYet() {
        return this == NOT_STARTED_YET;
    }

    public boolean isOngoing() {
        return this == ONGOING;
    }

}
