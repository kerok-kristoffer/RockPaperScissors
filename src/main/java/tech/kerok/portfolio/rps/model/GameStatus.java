package tech.kerok.portfolio.rps.model;

import java.util.Locale;

public enum GameStatus {
    HOSTED("HOSTED"),
    JOINED("JOINED"),
    AWAITING_MOVE("AWAITING_MOVE"),
    RESOLVED("RESOLVED");

    private final String value;

    GameStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
