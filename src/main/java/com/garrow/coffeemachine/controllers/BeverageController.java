package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.ActionDto;
import com.garrow.coffeemachine.dto.BeverageDto;
import com.garrow.coffeemachine.models.Beverage;
import com.garrow.coffeemachine.services.BeverageActionService;
import com.garrow.coffeemachine.services.BeverageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/beverages")
@RequiredArgsConstructor
@Slf4j
public class BeverageController {

    private final BeverageService beverageService;
    private final BeverageActionService beverageActionService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get all beverages", description = "Returns a paginated list of all beverages")
    @GetMapping
    public Page<BeverageDto> findAllBeverages(
            @Parameter(description = "Page number to retrieve", required = false)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", required = false)
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageService.findAll(PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, BeverageDto.class));
    }

    @Operation(summary = "Get a beverage by Id")
    @GetMapping("/{beverageId}")
    public BeverageDto findBeverageById(
            @Parameter(description = "ID of the beverage to be obtained", required = true)
            @PathVariable(name = "beverageId") UUID beverageId) {
        return modelMapper.map(beverageService.findById(beverageId), BeverageDto.class);
    }

    @Operation(summary = "Create a new beverage")
    @PostMapping("/create")
    public BeverageDto createBeverage(
            @Parameter(description = "Details of the beverage to be created", required = true)
            @Valid @RequestBody BeverageDto.Create beverageDto) {
        return modelMapper.map(beverageService.create(modelMapper.map(beverageDto, Beverage.class)), BeverageDto.class);
    }

    @Operation(summary = "Update an existing beverage")
    @PutMapping("/{beverageId}/edit")
    public BeverageDto updateBeverage(
            @Parameter(description = "ID of the beverage to be updated", required = true)
            @PathVariable(name = "beverageId") UUID beverageId,
            @Parameter(description = "Updated details of the beverage", required = true)
            @Valid @RequestBody BeverageDto.Update beverageDto) {
        return modelMapper.map(beverageService.update(beverageId, modelMapper.map(beverageDto, Beverage.class)), BeverageDto.class);
    }

    @Operation(summary = "Delete a beverage by Id")
    @DeleteMapping("/{beverageId}/delete")
    public void deleteBeverageById(
            @Parameter(description = "ID of the beverage to be deleted", required = true)
            @PathVariable(name = "beverageId") UUID beverageId) {
        beverageService.deleteById(beverageId);
    }

    @Operation(summary = "Get all actions for a beverage", description = "Returns a paginated list of all actions for a specific beverage")
    @GetMapping("/{beverageId}/actions")
    public Page<ActionDto> findAllBeverageActions(
            @Parameter(description = "ID of the beverage for which actions are to be retrieved", required = true)
            @PathVariable(name = "beverageId") UUID beverageId,
            @Parameter(description = "Page number to retrieve", required = false)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", required = false)
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageActionService.findAllByBeverage(beverageId, PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, ActionDto.class));
    }
}
