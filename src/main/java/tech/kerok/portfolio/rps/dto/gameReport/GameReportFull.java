package tech.kerok.portfolio.rps.dto.gameReport;

import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.service.GameResult;
import tech.kerok.portfolio.rps.service.GameService;

public class GameReportFull extends GameReport {

    public GameReportFull(Game game, Player host) {
        super(game, host);
    }

    public String getGuest() {
        return guest;
    }

    @Override
    public void generateReportMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.getGameReportMessage());
        stringBuilder.append(String.format(", %s: %s", hostMove.getPlayerName(), hostMove.getMove()));
        stringBuilder.append(String.format(", %s: %s", guestMove.getPlayerName(), guestMove.getMove()));

        GameResult result = GameService.calculateWinner(hostMove, guestMove);
        switch (result) {
            case HOST_WINNER:
                stringBuilder.append(String.format("%s is the Winner", hostMove.getPlayerName()));
            case GUEST_WINNER:
                stringBuilder.append(String.format("%s is the Winner.", guestMove.getPlayerName()));
            case TIE:
                stringBuilder.append(", The game is a tie.");
        }

        this.gameReportMessage = stringBuilder.toString();
    }
}
