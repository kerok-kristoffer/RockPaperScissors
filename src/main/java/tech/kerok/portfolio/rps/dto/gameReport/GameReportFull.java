package tech.kerok.portfolio.rps.dto.gameReport;

import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.model.PlayerMove;

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
        stringBuilder.append(", " + hostMove.getPlayerName() + ": " + hostMove.getMove());
        stringBuilder.append(", " + guestMove.getPlayerName() + ": " + guestMove.getMove());

        String winner = calculateWinner(hostMove, guestMove);
        stringBuilder.append(", " + winner);

        this.gameReportMessage = stringBuilder.toString();
    }

    private String calculateWinner(PlayerMove hostMove, PlayerMove guestMove) {
        int hostValue = hostMove.getMove().getValue();
        int guestValue = guestMove.getMove().getValue();
        if ((hostValue + 1) % 3 == guestValue) {
            return guestMove.getPlayerName() + " is the Winner!";
        } else if( hostValue == guestValue) {
            return "The game is a draw!";
        }
        return hostMove.getPlayerName() + " is the Winner!";
    }
}
