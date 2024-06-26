package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.IngredientDto;
import com.garrow.coffeemachine.models.Ingredient;
import com.garrow.coffeemachine.services.IngredientService;
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
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientService ingredientService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get all ingredients", description = "Returns a paginated list of all ingredients")
    @GetMapping
    public Page<IngredientDto> findAll(
            @Parameter(description = "Page number to retrieve", required = false)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", required = false)
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return ingredientService.findAll(PageRequest.of(page, size)).map(entity -> modelMapper.map(entity, IngredientDto.class));
    }

    @Operation(summary = "Get all ingredients with low quantity")
    @GetMapping("/low-quantity")
    public List<IngredientDto> findAllWithLowQuantity() {
        return ingredientService.findAllWithLowQuantity().stream().map(entity -> modelMapper.map(entity, IngredientDto.class)).toList();
    }

    @Operation(summary = "Get an ingredient by Id")
    @GetMapping("/{id}")
    public IngredientDto findById(
            @Parameter(description = "ID of the ingredient to be obtained", required = true)
            @PathVariable(name = "id") UUID id) {
        return modelMapper.map(ingredientService.findById(id), IngredientDto.class);
    }

    @Operation(summary = "Create a new ingredient")
    @PostMapping("/create")
    public IngredientDto create(
            @Parameter(description = "Details of the ingredient to be created", required = true)
            @Valid @RequestBody IngredientDto.Create ingredientDto) {
        return modelMapper.map(ingredientService.create(modelMapper.map(ingredientDto, Ingredient.class)), IngredientDto.class);
    }

    @Operation(summary = "Update an existing ingredient")
    @PutMapping("/{id}/edit")
    public IngredientDto update(
            @Parameter(description = "ID of the ingredient to be updated", required = true)
            @PathVariable(name = "id") UUID id,
            @Parameter(description = "Updated details of the ingredient", required = true)
            @RequestBody IngredientDto.Update ingredientDto) {
        return modelMapper.map(ingredientService.update(id, modelMapper.map(ingredientDto, Ingredient.class)), IngredientDto.class);
    }

    @Operation(summary = "Delete an ingredient by Id")
    @DeleteMapping("/{id}/delete")
    public void delete(
            @Parameter(description = "ID of the ingredient to be deleted", required = true)
            @PathVariable(name = "id") UUID id) {
        ingredientService.deleteById(id);
    }
}