package tech.kerok.portfolio.rps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.kerok.portfolio.rps.model.Move;

public class PlayerMoveDTO {
    String name;
    String pass;
    Move move;

    public PlayerMoveDTO(@JsonProperty("name") String name, @JsonProperty("pass") String pass, @JsonProperty("move") Move move) {
        this.name = name;
        this.pass = pass;
        this.move = move;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return this.name;
    }

    public Move getMove() {
        return move;
    }
}
