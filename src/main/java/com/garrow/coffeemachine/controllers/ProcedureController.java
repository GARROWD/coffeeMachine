package com.garrow.coffeemachine.controllers;

import com.garrow.coffeemachine.services.ProcedureService;
import com.garrow.coffeemachine.utils.enums.ProcedureType;
import com.garrow.coffeemachine.utils.enums.ProcessingStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedures")
@RequiredArgsConstructor
@Slf4j
public class ProcedureController {

    private final ProcedureService procedureService;

    @GetMapping("/types")
    public List<ProcedureType> findAllTypes() {
        return procedureService.findAllTypes();
    }

    @GetMapping("/processing/status")
    public ProcessingStatus getProcessingStatus(){
        return procedureService.getProcessingStatus();
    }

    @PostMapping("/processing")
    public void startProcessing() {
        procedureService.startProcessing();
    }
}
