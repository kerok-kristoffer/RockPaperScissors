package tech.kerok.portfolio.rps.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Game {

    @Id @GeneratedValue UUID id;

    private UUID hostId;
    private UUID guestId;
    private final int MAX_PLAYER_COUNT = 2;
    private String gameStatus;

    public Game() {
    }

    public Game(@JsonProperty("hostid") UUID hostId) {
        this.hostId = hostId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHostId() {
        return hostId;
    }

    public Optional<UUID> getGuestId() {
        return Optional.ofNullable(guestId);
    }

    public void setGuest(UUID guestId) {
        this.guestId = guestId;

        if (!this.gameStatus.equals(GameStatus.AWAITING_MOVE.getValue())) {
            this.gameStatus = GameStatus.JOINED.getValue();
        }
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus.getValue();
    }

    public GameStatus getGameStatus() {
        return GameStatus.valueOf(gameStatus);
    }

    public int getMaxPlayerCount() {
        return this.MAX_PLAYER_COUNT;
    }
}
