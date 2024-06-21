package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeverageRepository extends JpaRepository<Beverage, UUID> {
}
