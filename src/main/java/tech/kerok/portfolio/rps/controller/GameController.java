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

        Player player = playerService.getOrAdd(hostDTO);
        return gameReportFactory.createGameReport(gameService.addGame(player));
    }

    @GetMapping("{id}")
    GameReport getGameById(@PathVariable UUID id) {

        Game game = gameService.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        return gameReportFactory.createGameReport(game);
    }

    @PostMapping("{id}")
    GameReport getGameReportByPlayer(@PathVariable UUID id, @RequestBody PlayerDTO playerDTO) {
        Game game = gameService.findById(id).orElseThrow(() -> new GameNotFoundException(id));
        Player player = playerService.getByName(playerDTO.getName()).orElseThrow(() -> new PlayerNotInGameException(playerDTO.getName()));
        return gameReportFactory.createGameReport(game, player);
    }

    @PostMapping("{id}/join")
    GameReport joinGame(@PathVariable UUID id, @RequestBody PlayerDTO guestDTO) {

        Player guest = playerService.getOrAdd(guestDTO);
        Game game = gameService.findById(id).orElseThrow(() -> new GameNotFoundException(id));

        Game joinedGame = gameService.joinGame(game, guest);

        return gameReportFactory.createGameReport(joinedGame, guest);
    }

    @PostMapping("{id}/move")
    GameReport addMove(@PathVariable UUID id, @RequestBody PlayerMoveDTO newMove) {

        Player player = playerService.getByName(newMove.getName()).orElseThrow(() -> new PlayerNotInGameException(newMove.getName()));
        Game gameMoved = gameService.addMoveToGame(id, player, newMove);

        return gameReportFactory.createGameReport(gameMoved, player);
    }
}
