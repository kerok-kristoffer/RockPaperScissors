package tech.kerok.portfolio.rps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerDTO {
    String name;

    public PlayerDTO(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
