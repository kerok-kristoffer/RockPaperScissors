package tech.kerok.portfolio.rps.service.exceptions;

import java.util.UUID;

public class PlayerNotInGameException extends RuntimeException {

    public PlayerNotInGameException(String name, UUID id) {
        super("Could not find player in game " + name + ": " + id);
    }
    public PlayerNotInGameException(String name) {
        super("Could not find player in game " + name);
    }
}
