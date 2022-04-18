package tech.kerok.portfolio.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.kerok.portfolio.rps.model.Game;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
