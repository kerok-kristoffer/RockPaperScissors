package tech.kerok.portfolio.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kerok.portfolio.rps.model.Player;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByName(String name);
}
