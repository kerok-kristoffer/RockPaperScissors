package tech.kerok.portfolio.rps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PlayerMove {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID playerId;
    private Move move;
    private UUID gameId;
    private String playerName;

    public PlayerMove(UUID gameId, Player player, Move move) {
        this.playerId = player.getId();
        this.playerName = player.getName();
        this.move = move;
        this.gameId = gameId;
    }

    public PlayerMove() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayer(UUID player) {
        this.playerId = player;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameID) {
        this.gameId = gameID;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}
