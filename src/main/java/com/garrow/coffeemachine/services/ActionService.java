package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.repositories.ActionRepository;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ActionService implements JpaService<Action, UUID> {

    private final ActionRepository actionRepository;

    @Override
    public Action findById(UUID id) throws NotFoundException {
        Optional<Action> foundEntity = actionRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        actionRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Page<Action> findAllByBeverage(UUID beverageId, Pageable pageable) {
        return actionRepository.findAllByBeverageId(beverageId, pageable);
    }

    @Override
    public Action create(Action entity) {
        actionRepository.save(entity);

        log.info("Action with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public Action update(UUID id, Action entity) throws NotFoundException {
        existsById(id);
        entity.setId(id);
        actionRepository.save(entity);

        log.info("Action with ID {} is updated", id);

        return entity;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        actionRepository.deleteById(id);

        log.info("Action with ID {} is deleted", id);
    }
}
