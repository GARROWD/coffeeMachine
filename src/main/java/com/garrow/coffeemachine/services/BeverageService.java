package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Beverage;
import com.garrow.coffeemachine.repositories.BeverageRepository;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BeverageService implements JpaService<Beverage, UUID> {
    private final BeverageRepository beverageRepository;

    @Override
    public Beverage findById(UUID id) throws NotFoundException {
        Optional<Beverage> foundEntity = beverageRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        beverageRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Beverage create(Beverage entity) {
        beverageRepository.save(entity);

        log.info("Beverage with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public Beverage update(UUID id, Beverage entity) throws NotFoundException {
        existsById(id);
        beverageRepository.save(entity);

        log.info("Beverage with ID {} is updated", id);

        return null;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        beverageRepository.deleteById(id);

        log.info("Beverage with ID {} is deleted", id);
    }
}
