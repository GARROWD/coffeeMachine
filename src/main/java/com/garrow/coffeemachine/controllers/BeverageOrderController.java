package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.BeverageOrderDto;
import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.services.BeverageOrderService;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get all beverage orders by status", description = "Returns a paginated list of all beverage orders with the specified status")
    @GetMapping
    public Page<BeverageOrderDto> findAllByStatus(
            @Parameter(description = "Status of the beverage orders to retrieve", required = false)
            @RequestParam(name = "status", defaultValue = "PENDING") BeverageOrderStatus status,
            @Parameter(description = "Page number to retrieve", required = false)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", required = false)
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageOrderService.findAllByStatus(status, PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, BeverageOrderDto.class));
    }

    @Operation(summary = "Get all beverage order statuses", description = "Returns a list of all possible statuses for beverage orders")
    @GetMapping("/statuses")
    public List<BeverageOrderStatus> findAllStatuses() {
        return beverageOrderService.findAllStatuses();
    }

    @Operation(summary = "Get a beverage order by Id")
    @GetMapping("/{id}")
    public BeverageOrderDto findById(
            @Parameter(description = "ID of the beverage order to be obtained", required = true)
            @PathVariable(name = "id") UUID id) {
        return modelMapper.map(beverageOrderService.findById(id), BeverageOrderDto.class);
    }

    @Operation(summary = "Get the current beverage order", description = "Returns the current active beverage order")
    @GetMapping("/current")
    public BeverageOrderDto findCurrent() {
        return modelMapper.map(beverageOrderService.findCurrent(), BeverageOrderDto.class);
    }

    @Operation(summary = "Create a new beverage order")
    @PostMapping("/create")
    public BeverageOrderDto create(
            @Parameter(description = "Details of the beverage order to be created", required = true)
            @Valid @RequestBody BeverageOrderDto.Create beverageDto) {
        return modelMapper.map(beverageOrderService.create(modelMapper.map(beverageDto, BeverageOrder.class)), BeverageOrderDto.class);
    }

    @Operation(summary = "Delete a beverage order by Id")
    @DeleteMapping("/{id}/delete")
    public void delete(
            @Parameter(description = "ID of the beverage order to be deleted", required = true)
            @PathVariable(name = "id") UUID id) {
        beverageOrderService.deleteById(id);
    }
}