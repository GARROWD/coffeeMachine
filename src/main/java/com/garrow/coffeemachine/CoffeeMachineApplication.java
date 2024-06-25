package com.garrow.coffeemachine;

import com.garrow.coffeemachine.services.ProcedureService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
@RequiredArgsConstructor
public class CoffeeMachineApplication {

	private final ProcedureService procedureService;

	public static void main(String[] args) {
		SpringApplication.run(CoffeeMachineApplication.class, args);
	}

	@PostConstruct
	public void startOrderProcessing() {
		procedureService.processOrders();
	}
}