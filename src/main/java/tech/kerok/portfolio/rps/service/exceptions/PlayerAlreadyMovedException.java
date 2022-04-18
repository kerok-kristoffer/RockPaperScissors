package tech.kerok.portfolio.rps.service.exceptions;

public class PlayerAlreadyMovedException extends RuntimeException {

    public PlayerAlreadyMovedException(String name) {
        super(name + " already moved this game");
    }
}
