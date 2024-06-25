package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.dto.ActionDto;
import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.services.ActionService;
import com.garrow.coffeemachine.services.BeverageActionService;
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

    @GetMapping("/{actionId}")
    public ActionDto findActionById(@PathVariable(name = "actionId") UUID actionId) {
        return modelMapper.map(actionService.findById(actionId), ActionDto.class);
    }

    // Может лучше было бы иметь /beverage/{beverageId}/action/create и отсюда брать id
    @PostMapping("/create")
    public ActionDto createBeverageAction(@Valid @RequestBody ActionDto.Create actionDto) {
        return modelMapper.map(beverageActionService.create(modelMapper.map(actionDto, Action.class)), ActionDto.class);
    }

    @PutMapping("/{actionId}/edit")
    public ActionDto updateAction(
            @PathVariable(name = "actionId") UUID actionId,
            @Valid @RequestBody ActionDto.Update actionDto) {
        return modelMapper.map(actionService.update(actionId, modelMapper.map(actionDto, Action.class)), ActionDto.class);
    }

    @DeleteMapping("/{actionId}/delete")
    public void deleteActionById(@PathVariable(name = "actionId") UUID actionId) {
        actionService.deleteById(actionId);
    }
}
