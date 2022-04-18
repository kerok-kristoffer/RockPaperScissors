package tech.kerok.portfolio.rps.service.exceptions;

import java.util.UUID;

public class GameFullException extends RuntimeException {
    public GameFullException(UUID gameId) {
        super("Guest spot already filled in game " + gameId);
    }
}
