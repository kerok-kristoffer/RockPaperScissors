package tech.kerok.portfolio.rps.dto.gameReport;

import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.Player;

public class GameReportJoined extends GameReport {

    public GameReportJoined(Game game, Player host) {
        super(game, host);
    }

    public String getGuest() {
        return guest;
    }

}
