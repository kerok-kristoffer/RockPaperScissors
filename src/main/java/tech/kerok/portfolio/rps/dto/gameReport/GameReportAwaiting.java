package tech.kerok.portfolio.rps.dto.gameReport;

import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.Player;

public class GameReportAwaiting extends GameReport {

    public GameReportAwaiting(Game game, Player host) {
        super(game, host);
    }

    public String getGuest() {
        return guest;
    }

    @Override
    public void generateReportMessage() {

        StringBuilder sb = new StringBuilder(gameReportMessage);
        currentPlayerMove.ifPresent(playerMove -> sb.append(", " + playerMove.getPlayerName() + " made move: " + playerMove.getMove()));

        gameReportMessage = sb.toString();
    }
}
