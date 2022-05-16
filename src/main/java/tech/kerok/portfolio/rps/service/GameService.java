package tech.kerok.portfolio.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.dto.PlayerDTO;
import tech.kerok.portfolio.rps.dto.PlayerMoveDTO;
import tech.kerok.portfolio.rps.model.*;
import tech.kerok.portfolio.rps.repository.GameRepository;
import tech.kerok.portfolio.rps.repository.PlayerMoveRepository;
import tech.kerok.portfolio.rps.service.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerMoveRepository playerMoveRepository;
    private final PlayerService playerService;

    @Autowired
    public GameService(
            GameRepository gameRepository,
            PlayerMoveRepository playerMoveRepository,
            PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerMoveRepository = playerMoveRepository;
        this.playerService = playerService;
    }

    public Game addGame(PlayerDTO hostDTO) {

        Player hostPlayer = playerService.getOrAdd(hostDTO);
        Game game = new Game(hostPlayer.getId());

        game.setGameStatus(GameStatus.HOSTED);

        gameRepository.save(game);
        return game;
    }

    public List<Game> getAll() {
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

        long playerMoveCountForCurrentGame = playerMoveRepository.findByGameId(game.getId()).size();
        if (playerMoveCountForCurrentGame < game.getMaxPlayerCount()) {
            game.setGameStatus(GameStatus.AWAITING_MOVE);
        } else if (playerMoveCountForCurrentGame == game.getMaxPlayerCount()) {
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

    public Game joinGame(UUID gameId, Player guest) {

        Game game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

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

    public Game addMoveToGame(UUID gameId, PlayerMoveDTO newMove) {

        Game game = findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        Player player = playerService.getByName(newMove.getName()).orElseThrow(() -> new PlayerNotInGameException(newMove.getName()));

        String playerMoveHash = HashService.createMD5Hash(newMove.getPass());
        if (!HashService.verify(player.getHash(), playerMoveHash)) {
            throw new WrongPasswordException();
        }

        return executeMove(game, player, newMove.getMove());
    }

    public static GameResult calculateWinner(PlayerMove hostMove, PlayerMove guestMove) {

        List<Move> moveSet = new ArrayList<>(); // TODO kerok - make part of game parameters on create! - make enum MoveSet with predefined movesets?
        moveSet.add(Move.Rock);
        moveSet.add(Move.Paper);
        moveSet.add(Move.Scissors);
        MoveChain moveChain = new MoveChain(moveSet);

        return moveChain.evaluate(hostMove.getMove(), guestMove.getMove());
    }
}
