package tech.kerok.portfolio.rps.service.exceptions;

public class PlayerAlreadyInGameException extends RuntimeException {

    public PlayerAlreadyInGameException(String name) {
        super(name + " already joined this game");
    }
}
