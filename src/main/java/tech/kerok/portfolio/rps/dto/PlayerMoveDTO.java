package tech.kerok.portfolio.rps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.kerok.portfolio.rps.model.Move;

public class PlayerMoveDTO {
    String name;
    Move move;

    public PlayerMoveDTO(@JsonProperty("name") String name,@JsonProperty("move") Move move) {
        this.name = name;
        this.move = move;
    }

    public String getName() {
        return this.name;
    }

    public Move getMove() {
        return move;
    }
}
