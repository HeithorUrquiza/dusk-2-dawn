package me.hecth.domain.repositories;

import me.hecth.domain.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, String> {
    boolean existsByCpf(String cpf);
}
