package me.hecth.domain.repositories;

import me.hecth.domain.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByTitle(String title);
    Optional<Game> findByTitle(String title);
}
