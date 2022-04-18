package tech.kerok.portfolio.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.dto.PlayerDTO;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.repository.PlayerRepository;
import tech.kerok.portfolio.rps.service.exceptions.InvalidPlayerFormatException;

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

    public Player getOrAdd(PlayerDTO hostDTO) {

        if (hostDTO.getName() == null) {
            throw new InvalidPlayerFormatException();
        }

        Optional<Player> player = getByName(hostDTO.getName());

        return player.orElseGet(() -> {
            Player newPlayer = new Player(hostDTO.getName());
            playerRepository.save(newPlayer);
            return newPlayer;
        });
    }
}
