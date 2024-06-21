package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {
}
