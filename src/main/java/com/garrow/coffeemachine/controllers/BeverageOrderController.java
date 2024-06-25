package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.BeverageOrderDto;
import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.services.BeverageOrderService;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/beverage-orders")
@RequiredArgsConstructor
@Slf4j
public class BeverageOrderController {

    private final BeverageOrderService beverageOrderService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<BeverageOrderDto> findAllByStatus(
            @RequestParam(name = "status", defaultValue = "PENDING") BeverageOrderStatus status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageOrderService.findAllByStatus(status, PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, BeverageOrderDto.class));
    }

    @GetMapping("/statuses")
    public List<BeverageOrderStatus> findAllStatuses() {
        return beverageOrderService.findAllStatuses();
    }

    @GetMapping("/{id}")
    public BeverageOrderDto findById(@PathVariable(name = "id") UUID id) {
        return modelMapper.map(beverageOrderService.findById(id), BeverageOrderDto.class);
    }

    @GetMapping("/current")
    public BeverageOrderDto findCurrent() {
        return modelMapper.map(beverageOrderService.findCurrent(), BeverageOrderDto.class);
    }

    @PostMapping("/create")
    public BeverageOrderDto create(@Valid @RequestBody BeverageOrderDto.Create beverageDto) {
        return modelMapper.map(beverageOrderService.create(modelMapper.map(beverageDto, BeverageOrder.class)), BeverageOrderDto.class);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable(name = "id") UUID id) {
        beverageOrderService.deleteById(id);
    }
}