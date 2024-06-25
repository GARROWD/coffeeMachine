package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {
    Page<Action> findAllByBeverageIdOrderByOrderIndex(UUID beverageId, Pageable pageable);
}
