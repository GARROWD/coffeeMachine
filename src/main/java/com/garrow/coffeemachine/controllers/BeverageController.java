package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.ActionDto;
import com.garrow.coffeemachine.dto.BeverageDto;
import com.garrow.coffeemachine.models.Beverage;
import com.garrow.coffeemachine.services.BeverageActionService;
import com.garrow.coffeemachine.services.BeverageService;
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

    @GetMapping
    public Page<BeverageDto> findAllBeverages(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageService.findAll(PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, BeverageDto.class));
    }

    @GetMapping("/{beverageId}")
    public BeverageDto findBeverageById(@PathVariable(name = "beverageId") UUID beverageId) {
        return modelMapper.map(beverageService.findById(beverageId), BeverageDto.class);
    }

    @PostMapping("/create")
    public BeverageDto createBeverage(@Valid @RequestBody BeverageDto.Create beverageDto) {
        return modelMapper.map(beverageService.create(modelMapper.map(beverageDto, Beverage.class)), BeverageDto.class);
    }

    @PutMapping("/{beverageId}/edit")
    public BeverageDto updateBeverage(
            @PathVariable(name = "beverageId") UUID beverageId,
            @Valid @RequestBody BeverageDto.Update beverageDto) {
        return modelMapper.map(beverageService.update(beverageId, modelMapper.map(beverageDto, Beverage.class)), BeverageDto.class);
    }

    @DeleteMapping("/{beverageId}/delete")
    public void deleteBeverageById(@PathVariable(name = "beverageId") UUID beverageId) {
        beverageService.deleteById(beverageId);
    }

    @GetMapping("/beverage/{beverageId}")
    public Page<ActionDto> findAllBeverageActions(@PathVariable(name = "beverageId") UUID beverageId,
                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "20") int size) {
        return beverageActionService.findAllByBeverage(beverageId, PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, ActionDto.class));
    }
}
