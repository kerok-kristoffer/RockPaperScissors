package tech.kerok.portfolio.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.kerok.portfolio.rps.dto.PlayerMoveDTO;
import tech.kerok.portfolio.rps.dto.gameReport.GameReport;
import tech.kerok.portfolio.rps.dto.gameReport.GameReportFactory;
import tech.kerok.portfolio.rps.dto.PlayerDTO;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.model.Game;
import tech.kerok.portfolio.rps.service.GameService;
import tech.kerok.portfolio.rps.service.PlayerService;
import tech.kerok.portfolio.rps.service.exceptions.GameNotFoundException;
import tech.kerok.portfolio.rps.service.exceptions.PlayerNotInGameException;

import java.util.UUID;

@RequestMapping("api/games")
@RestController
public class GameController {
    private final GameService gameService;
    private final PlayerService playerService;
    private final GameReportFactory gameReportFactory;

    @Autowired
    GameController(GameService gameService, PlayerService playerService, GameReportFactory gameReportFactory) {

        this.gameService = gameService;
        this.playerService = playerService;
        this.gameReportFactory = gameReportFactory;
    }

    @PostMapping()
    GameReport newGame(@RequestBody PlayerDTO hostDTO) {

        Game game = gameService.addGame(hostDTO);
        return gameReportFactory.createGameReport(game);
    }

    @GetMapping("{id}")
    GameReport getGameById(@PathVariable UUID id) {

        Game game = gameService.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        return gameReportFactory.createGameReport(game); // TODO kerok - seems to be public? enable private games requiring a PlayerDTO with pass to see?
    }

    @PostMapping("{id}")
    GameReport getGameReportByPlayer(@PathVariable UUID id, @RequestBody PlayerDTO playerDTO) { // TODO kerok - is this needed? - if we want privacy, yes!
        Game game = gameService.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        Player player = playerService.getByName(playerDTO.getName()).orElseThrow(() -> new PlayerNotInGameException(playerDTO.getName()));
        return gameReportFactory.createGameReport(game, player);
    }

    @PostMapping("{gameId}/join")
    GameReport joinGame(@PathVariable UUID gameId, @RequestBody PlayerDTO guestDTO) {

        Player guest = playerService.getOrAdd(guestDTO);
        Game joinedGame = gameService.joinGame(gameId, guest);

        return gameReportFactory.createGameReport(joinedGame, guest);
    }

    @PostMapping("{gameId}/move")
    GameReport addMove(@PathVariable UUID gameId, @RequestBody PlayerMoveDTO newMove) {

        // todo kerok - refactor this to take a move on first join? would streamline to just one request sent per player,
        //      returning gameReport to second player. First player would still need to request the result.
        //      Could use gameId, or just userName to find last open game as well?
                // TODO add this use case when done refactoring standard.
        Player player = playerService.getByName(newMove.getName()).orElseThrow(() -> new PlayerNotInGameException(newMove.getName()));
        Game gameMoved = gameService.addMoveToGame(gameId, newMove);

        return gameReportFactory.createGameReport(gameMoved, player);
    }
}
