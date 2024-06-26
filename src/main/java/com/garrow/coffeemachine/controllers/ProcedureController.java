package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.services.ProcedureService;
import com.garrow.coffeemachine.utils.enums.ProcedureType;
import com.garrow.coffeemachine.utils.enums.ProcessingStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/procedures")
@RequiredArgsConstructor
@Slf4j
public class ProcedureController {

    private final ProcedureService procedureService;

    @Operation(summary = "Get all procedure types", description = "Returns a list of all possible procedure types")
    @GetMapping("/types")
    public List<ProcedureType> findAllTypes() {
        return procedureService.findAllTypes();
    }

    @Operation(summary = "Get processing status", description = "Returns the current processing status")
    @GetMapping("/processing/status")
    public ProcessingStatus getProcessingStatus() {
        return procedureService.getProcessingStatus();
    }

    @Operation(summary = "Start processing", description = "Starts the processing procedure")
    @PostMapping("/processing")
    public void startProcessing() {
        procedureService.startProcessing();
    }
}
