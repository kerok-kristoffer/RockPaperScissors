package tech.kerok.portfolio.rps.dto.gameReport;

import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.model.GameStatus;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.model.PlayerMove;
import tech.kerok.portfolio.rps.repository.PlayerMoveRepository;
import tech.kerok.portfolio.rps.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameReportFactory {

    private final PlayerRepository playerRepository;
    private final PlayerMoveRepository playerMoveRepository;

    public GameReportFactory(PlayerRepository playerRepository, PlayerMoveRepository playerMoveRepository) {
        this.playerRepository = playerRepository;
        this.playerMoveRepository = playerMoveRepository;
    }

    public GameReport createGameReport(Game game, Player currentPlayer) {
        return createGameReport(game, Optional.of(currentPlayer));
    }

    public GameReport createGameReport(Game game) {
        return createGameReport(game, Optional.empty());
    }

    private GameReport createGameReport(Game game, Optional<Player> currentPlayer) {
        GameStatus gameStatus = game.getGameStatus();

        Player host = playerRepository.getOne(game.getHostId());

        GameReport gameReport;

        switch (gameStatus) {
            case HOSTED:
                gameReport = new GameReport(game, host);
                break;
            case JOINED:
                gameReport = new GameReportJoined(game, host);
                break;
            case AWAITING_MOVE:
                gameReport = new GameReportAwaiting(game, host);
                break;
            case RESOLVED:
                gameReport = new GameReportFull(game, host);
                break;
            default:
                throw new RuntimeException("Something went wrong with gameStatus"); // Should never happen
        }

        if (game.getGuestId().isPresent()) {
            Player guest = playerRepository.getOne(game.getGuestId().get());
            gameReport.setGuest(guest);
        }

        List<PlayerMove> moves = playerMoveRepository.findByGameId(game.getId());
        for (PlayerMove move : moves) {
            Player movePlayer = playerRepository.getOne(move.getPlayerId());
            gameReport.addMove(move, currentPlayer, movePlayer);
        }

        gameReport.generateReportMessage();

        return gameReport;
    }
}
