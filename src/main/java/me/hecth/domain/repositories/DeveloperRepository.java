package me.hecth.domain.repositories;

import me.hecth.domain.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, String> {
    boolean existsByCpf(String cpf);
    Optional<Developer> findByCpf(String cpf);
}
