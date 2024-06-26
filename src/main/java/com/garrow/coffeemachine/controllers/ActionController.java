package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.ActionDto;
import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.services.ActionService;
import com.garrow.coffeemachine.services.BeverageActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/actions")
@RequiredArgsConstructor
@Slf4j
public class ActionController {

    private final ActionService actionService;
    private final BeverageActionService beverageActionService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Get an action by Id")
    @GetMapping("/{actionId}")
    public ActionDto findActionById(
            @Parameter(description = "ID of the action to be obtained", required = true)
            @PathVariable(name = "actionId") UUID actionId) {
        return modelMapper.map(actionService.findById(actionId), ActionDto.class);
    }

    @Operation(summary = "Create a new beverage action")
    @PostMapping("/create")
    public ActionDto createBeverageAction(
            @Parameter(description = "Details of the action to be created, including the ID of the beverage", required = true)
            @Valid @RequestBody ActionDto.Create actionDto) {
        return modelMapper.map(beverageActionService.create(modelMapper.map(actionDto, Action.class)), ActionDto.class);
    }

    @Operation(summary = "Update an existing action")
    @PutMapping("/{actionId}/edit")
    public ActionDto updateAction(
            @Parameter(description = "ID of the action to be updated", required = true)
            @PathVariable(name = "actionId") UUID actionId,
            @Parameter(description = "Updated details of the action", required = true)
            @Valid @RequestBody ActionDto.Update actionDto) {
        return modelMapper.map(actionService.update(actionId, modelMapper.map(actionDto, Action.class)), ActionDto.class);
    }

    @Operation(summary = "Delete an action by Id")
    @DeleteMapping("/{actionId}/delete")
    public void deleteActionById(
            @Parameter(description = "ID of the action to be deleted", required = true)
            @PathVariable(name = "actionId") UUID actionId) {
        actionService.deleteById(actionId);
    }
}
