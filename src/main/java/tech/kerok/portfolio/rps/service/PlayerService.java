package tech.kerok.portfolio.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kerok.portfolio.rps.dto.PlayerDTO;
import tech.kerok.portfolio.rps.model.Player;
import tech.kerok.portfolio.rps.repository.PlayerRepository;
import tech.kerok.portfolio.rps.service.exceptions.InvalidPlayerFormatException;
import tech.kerok.portfolio.rps.service.exceptions.WrongPasswordException;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
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
        String hash = null;
        try {
            hash = HashService.createMD5Hash(hostDTO.getPass());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // TODO add proper handling of Exception?
        }

        String finalHash = hash;
        Player player1 = player.orElseGet(() -> {
            Player newPlayer = new Player(hostDTO.getName(), finalHash);
            playerRepository.save(newPlayer);
            return newPlayer;
        });

        if(!HashService.verify(player1.getHash(), finalHash)) {
            throw new WrongPasswordException();
        }

        return player.get();
    }
}
