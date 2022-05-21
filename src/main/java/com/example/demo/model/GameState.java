package com.example.demo.model;

public enum GameState {
    NOT_STARTED_YET,
    ONGOING,
    ENDED;

    public static boolean hasStarted(Game game) {
        return game.getState() != NOT_STARTED_YET;
    }
    public static boolean hasNotStartedYet(Game game) {
        return game.getState() == NOT_STARTED_YET;
    }

    public static boolean isOngoing(Game game) {
        return game.getState() == ONGOING;
    }

}
