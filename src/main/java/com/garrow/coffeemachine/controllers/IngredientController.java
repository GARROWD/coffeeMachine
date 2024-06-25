package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.IngredientDto;
import com.garrow.coffeemachine.models.Ingredient;
import com.garrow.coffeemachine.services.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<IngredientDto> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return ingredientService.findAll(PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, IngredientDto.class));
    }

    @GetMapping("/{id}")
    public IngredientDto findById(@PathVariable(name = "id") UUID id) {
        return modelMapper.map(ingredientService.findById(id), IngredientDto.class);
    }

    @PostMapping("/create")
    public IngredientDto create(@Valid @RequestBody IngredientDto.Create ingredientDto) {
        return modelMapper.map(ingredientService.create(modelMapper.map(ingredientDto, Ingredient.class)), IngredientDto.class);
    }

    @PutMapping("/{id}/edit")
    public IngredientDto update(
            @PathVariable(name = "id") UUID beverageId,
            @RequestBody IngredientDto.Update ingredientDto) {
        return modelMapper.map(ingredientService.update(beverageId, modelMapper.map(ingredientDto, Ingredient.class)), IngredientDto.class);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable(name = "id") UUID id) {
        ingredientService.deleteById(id);
    }
}