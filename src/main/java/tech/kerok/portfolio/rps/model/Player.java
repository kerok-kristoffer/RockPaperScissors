package tech.kerok.portfolio.rps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Player {
    @Id
    UUID id;
    private String name;
    private String hash;

    public Player(@JsonProperty("name")String name, @JsonProperty String hash) {
        this.name = name;
        this.hash = hash;
        this.id = UUID.randomUUID();
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }
}
