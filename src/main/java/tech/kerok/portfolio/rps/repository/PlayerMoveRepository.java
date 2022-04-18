package tech.kerok.portfolio.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.kerok.portfolio.rps.model.PlayerMove;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerMoveRepository extends JpaRepository<PlayerMove, UUID> {
    List<PlayerMove> findByGameId(UUID id);
}
