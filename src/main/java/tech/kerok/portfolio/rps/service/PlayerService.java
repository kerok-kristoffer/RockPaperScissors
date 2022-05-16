package tech.kerok.portfolio.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.dto.PlayerDTO;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.repository.PlayerRepository;
import tech.kerok.portfolio.rps.service.exceptions.InvalidPlayerFormatException;
import tech.kerok.portfolio.rps.service.exceptions.WrongPasswordException;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Player> getByName(String name) {
        return playerRepository.findByName(name);
    }

    public Player getOrAdd(PlayerDTO playerDTO) {

        if (playerDTO.getName() == null) {
            throw new InvalidPlayerFormatException();
        }

        Optional<Player> player = getByName(playerDTO.getName());
        String hash = HashService.createMD5Hash(playerDTO.getPass());

        Player player1 = player.orElseGet(() -> {
            Player newPlayer = new Player(playerDTO.getName(), hash);
            playerRepository.save(newPlayer);
            return newPlayer;
        });

        if(!HashService.verify(player1.getHash(), hash)) {
            throw new WrongPasswordException();
        }

        return player.get();
    }
}
