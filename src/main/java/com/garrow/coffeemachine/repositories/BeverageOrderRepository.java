package com.garrow.coffeemachine.repositories;

import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BeverageOrderRepository extends JpaRepository<BeverageOrder, UUID> {
    Page<BeverageOrder> findAllByStatus(BeverageOrderStatus status, Pageable pageable);

    Optional<BeverageOrder> findByStatus(BeverageOrderStatus status);

    Optional<BeverageOrder> findFirstByStatusOrderByCreatedDesc(BeverageOrderStatus status);
}
