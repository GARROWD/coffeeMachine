package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, String> {
}
