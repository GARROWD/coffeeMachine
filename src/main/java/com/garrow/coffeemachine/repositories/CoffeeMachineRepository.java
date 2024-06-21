package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, UUID> {
}
