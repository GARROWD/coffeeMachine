package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.repositories.BeverageOrderRepository;
import com.garrow.coffeemachine.services.interfaces.JpaService;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BeverageOrderService implements JpaService<BeverageOrder, UUID> {

    private final BeverageOrderRepository beverageOrderRepository;

    @Override
    public BeverageOrder findById(UUID id) throws NotFoundException {
        Optional<BeverageOrder> foundEntity = beverageOrderRepository.findById(id);

        return foundEntity.orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public void existsById(UUID id) throws NotFoundException {
        beverageOrderRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void setStatus(UUID id, BeverageOrderStatus status) {
        BeverageOrder beverageOrder = findById(id);
        beverageOrder.setStatus(status);
        beverageOrderRepository.save(beverageOrder);
    }

    public BeverageOrder findCurrent() {
        Optional<BeverageOrder> foundEntity = beverageOrderRepository.findByStatus(BeverageOrderStatus.PROCESSING);

        return foundEntity.orElseThrow(() -> new NotFoundException("Current")); // TODO
    }

    public BeverageOrder findNext() {
        Optional<BeverageOrder> foundEntity = beverageOrderRepository.findFirstByStatusOrderByCreatedDesc(BeverageOrderStatus.PENDING);

        return foundEntity.orElseThrow(() -> new NotFoundException("Next")); // TODO
    }

    public Page<BeverageOrder> findAllByStatus(BeverageOrderStatus status, Pageable pageable) {
        return beverageOrderRepository.findAllByStatus(status, pageable);
    }

    @Cacheable("beverageOrderStatuses")
    public Page<BeverageOrderStatus> findAllStatuses(Pageable pageable) {
        List<BeverageOrderStatus> statuses = Arrays.asList(BeverageOrderStatus.values());
        int start = Math.min((int) pageable.getOffset(), statuses.size());
        int end = Math.min((start + pageable.getPageSize()), statuses.size());

        List<BeverageOrderStatus> pagedStatuses = statuses.subList(start, end);
        return new PageImpl<>(pagedStatuses, pageable, statuses.size());
    }

    @Override
    public BeverageOrder create(BeverageOrder entity) {
        entity.setStatus(BeverageOrderStatus.PENDING);
        entity.setCreated(LocalDateTime.now());
        beverageOrderRepository.save(entity);

        log.info("BeverageOrder with ID {} is created", entity.getId());

        return entity;
    }

    @Override
    public BeverageOrder update(UUID id, BeverageOrder entity) throws NotFoundException {
        existsById(id);
        entity.setId(id);
        beverageOrderRepository.save(entity);

        log.info("BeverageOrder with ID {} is updated", id);

        return entity;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        existsById(id);
        beverageOrderRepository.deleteById(id);

        log.info("BeverageOrder with ID {} is deleted", id);
    }
}
