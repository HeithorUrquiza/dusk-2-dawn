package me.hecth.services;

import me.hecth.domain.models.Game;
import me.hecth.domain.repositories.DeveloperRepository;
import me.hecth.domain.repositories.GameRepository;
import me.hecth.services.exceptions.BusinessException;
import me.hecth.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class GameServices {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DeveloperRepository developerRepository;

    @Transactional(readOnly = true)
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Game findByTitle(String title) {
        return gameRepository.findByTitle(title).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Game create(Game gameToCreate) {
        ofNullable(gameToCreate).orElseThrow(() -> new BusinessException("Developer must not be null"));

        if (gameRepository.existsByTitle(gameToCreate.getTitle())) {
            throw new BusinessException("This game already exists");
        }
        return gameRepository.save(gameToCreate);
    }

    @Transactional
    public Game update(String title, Game gameToUpdate) {
        Game dbGame = this.findByTitle(title);
        String devCPF = gameToUpdate.getDeveloper().getCpf();
        if (!dbGame.getTitle().equalsIgnoreCase(gameToUpdate.getTitle())) {
            throw new BusinessException("Update titles must be the same");
        }
        if (!developerRepository.existsByCpf(devCPF)) {
            throw new BusinessException("Developer must exist in Database");
        }
        dbGame.setDescription(gameToUpdate.getDescription());
        dbGame.setGenre(gameToUpdate.getGenre());
        dbGame.setPrice(gameToUpdate.getPrice());
        dbGame.setDeveloper(developerRepository.findByCpf(devCPF).orElseThrow(NotFoundException::new));
        return gameRepository.save(dbGame);
    }

    @Transactional
    public void delete(String title) {
        Game dbGame = this.findByTitle(title);
        gameRepository.delete(dbGame);
    }
}
