package tech.kerok.portfolio.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.dto.PlayerMoveDTO;
import tech.kerok.portfolio.rps.model.*;
import tech.kerok.portfolio.rps.repository.GameRepository;
import tech.kerok.portfolio.rps.repository.PlayerMoveRepository;
import tech.kerok.portfolio.rps.service.exceptions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerMoveRepository playerMoveRepository;

    @Autowired
    public GameService(
            GameRepository gameRepository, PlayerMoveRepository playerMoveRepository) {
        this.gameRepository = gameRepository;
        this.playerMoveRepository = playerMoveRepository;
    }

    public Game addGame(Player host) {
        Game game = new Game(host.getId());
        game.setGameStatus(GameStatus.HOSTED);

        gameRepository.save(game);
        return game;
    }

    public List getAll() {
        return gameRepository.findAll();
    }

    public Game save(Game game) {

        gameRepository.save(game);
        return game;
    }

    public Optional<Game> findById(UUID id) {
        return gameRepository.findById(id);
    }

    public Game executeMove(Game game, Player player, Move move) {

        if (!validatePlayerJoined(game, player.getId())) {
            throw new PlayerNotInGameException(player.getName(), game.getId());
        }

        Stream<PlayerMove> playerMovesInGame = playerMoveRepository.findByGameId(game.getId()).stream();
        if (playerMovesInGame.anyMatch((pm) -> pm.getPlayerId().equals(player.getId()))) {
            throw new PlayerAlreadyMovedException(player.getName());
        }

        PlayerMove playerMove = new PlayerMove(game.getId(), player, move);
        playerMoveRepository.save(playerMove);

        int playerMoveCountForCurrentGame = playerMoveRepository.findByGameId(game.getId()).size();
        if (playerMoveCountForCurrentGame < game.getMaxPlayerCount()) {
            game.setGameStatus(GameStatus.AWAITING_MOVE);
        } else if (playerMoveCountForCurrentGame == game.getMaxPlayerCount()){
            game.setGameStatus(GameStatus.RESOLVED);
        } else {
            throw new RuntimeException("Something went wrong with player count");
        }

        gameRepository.save(game);
        return game;
    }

    public boolean validatePlayerJoined(Game game, UUID playerId) {

        Optional<UUID> guestId = game.getGuestId();

        return ( playerId.equals(game.getHostId()) ||
                ( guestId.isPresent() && playerId.equals(guestId.get()) ));
    }

    public Game joinGame(Game game, Player guest) {

        if (guest.getId().equals(game.getHostId())) {
            throw new PlayerAlreadyInGameException(guest.getName());
        }

        if (game.getGuestId().isPresent()) {
            throw new GameFullException(game.getId());
        }

        game.setGuest(guest.getId());
        gameRepository.save(game);
        return game;
    }

    public Game addMoveToGame(UUID gameId, Player player, PlayerMoveDTO newMove) {
        Game game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        return executeMove(game, player, newMove.getMove());
    }
}
