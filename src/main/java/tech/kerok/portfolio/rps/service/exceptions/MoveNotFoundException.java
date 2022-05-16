package tech.kerok.portfolio.rps.service.exceptions;

import tech.kerok.portfolio.rps.model.Move;

public class MoveNotFoundException extends RuntimeException {

    public MoveNotFoundException(Move move) {
        super("Could not find move " + move.name());
    }
}

