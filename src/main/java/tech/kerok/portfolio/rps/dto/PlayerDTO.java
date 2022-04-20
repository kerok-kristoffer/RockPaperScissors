package tech.kerok.portfolio.rps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerDTO {
    String name;
    private String pass;

    public PlayerDTO(@JsonProperty("name") String name, @JsonProperty("pass") String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
}
