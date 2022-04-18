package tech.kerok.portfolio.rps.dto.gameReport;

import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.model.PlayerMove;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameReport {

    private final UUID gameId;
    protected String gameReportMessage;
    private final String host;
    private final List<String> moves;
    protected String guest;
    protected Optional<PlayerMove> currentPlayerMove;

    protected PlayerMove hostMove;
    protected PlayerMove guestMove;

    public GameReport(Game game, Player host) {
        this.gameId = game.getId();
        this.host = host.getName();
        this.guest = "<not connected yet>";
        this.gameReportMessage = game.getGameStatus().getValue();
        this.currentPlayerMove = Optional.empty();
        this.moves = new LinkedList<>();
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getHost() {
        return host;
    }

    public String getGameReportMessage() {
        return gameReportMessage;
    }

    public void setGuest(Player guest) {
        this.guest = guest.getName();
    }

    public void addMove(PlayerMove move, Optional<Player> currentPlayerOptional, Player movePlayer) {

        if (move.getPlayerName().equals(host)) {
            hostMove = move;
        } else if(move.getPlayerName().equals(guest)) {
            guestMove = move;
        }

        if (currentPlayerOptional.isPresent() && currentPlayerOptional.get().equals(movePlayer)) {
            currentPlayerMove = Optional.of(move);
        }
    }

    protected List<String> getMoves() {
        return moves;
    }

    public void generateReportMessage() {
        // Not necessary for base class - overridden in children.
    }


}
