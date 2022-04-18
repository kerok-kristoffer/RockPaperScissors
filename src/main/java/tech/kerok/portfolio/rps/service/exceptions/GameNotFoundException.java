package tech.kerok.portfolio.rps.service.exceptions;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(UUID id) {
        super("Could not find game " + id);
    }
}
