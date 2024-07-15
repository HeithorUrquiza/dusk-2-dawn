package me.hecth.services;

import me.hecth.domain.models.Developer;
import me.hecth.domain.repositories.DeveloperRepository;
import me.hecth.services.exceptions.BusinessException;
import me.hecth.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class DeveloperServices {
    @Autowired
    private DeveloperRepository developerRepository;

    @Transactional(readOnly = true)
    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Developer findByCPF(String cpf) {
        return developerRepository.findById(cpf).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Developer create(Developer devToCreate) {
        ofNullable(devToCreate).orElseThrow(() -> new BusinessException("Developer must not be null"));

        if (developerRepository.existsByCpf(devToCreate.getCpf())) {
            throw new BusinessException("This developer already exists");
        }
        return developerRepository.save(devToCreate);
    }

    @Transactional
    public Developer update(String cpf, Developer devToUpdate) {
        Developer dbDev = this.findByCPF(cpf);
        if (!dbDev.getCpf().equals(devToUpdate.getCpf())) {
            throw new BusinessException("Update CPFs must be the same");
        }
        dbDev.setCpf(cpf);
        dbDev.setName(devToUpdate.getName());
        dbDev.setCompany(devToUpdate.getCompany());
        return developerRepository.save(dbDev);
    }

    public void delete(String cpf) {
        Developer dbDev = this.findByCPF(cpf);
        developerRepository.delete(dbDev);
    }
}
