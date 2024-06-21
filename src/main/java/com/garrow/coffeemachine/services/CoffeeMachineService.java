package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.models.CoffeeMachine;
import com.garrow.coffeemachine.repositories.CoffeeMachineRepository;
import com.garrow.coffeemachine.services.interfaces.ActionExecutorService;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CoffeeMachineService implements ActionExecutorService, JpaService<CoffeeMachine, UUID> {
    private final CoffeeMachineRepository coffeeMachineRepository;

    @Override
    public void perform(Action action) {
        action.execute();
    }

    @Override
    public void perform(List<Action> actions) {
        actions.forEach(Action::execute);
    }

    @Override
    public CoffeeMachine findById(UUID id) throws NotFoundException {
        Optional<CoffeeMachine> foundEntity = coffeeMachineRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        coffeeMachineRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public CoffeeMachine create(CoffeeMachine entity) {
        coffeeMachineRepository.save(entity);

        log.info("CoffeeMachine with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public CoffeeMachine update(UUID id, CoffeeMachine entity) throws NotFoundException {
        existsById(id);
        coffeeMachineRepository.save(entity);

        log.info("CoffeeMachine with ID {} is updated", id);

        return null;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        coffeeMachineRepository.deleteById(id);

        log.info("CoffeeMachine with ID {} is deleted", id);
    }
}
