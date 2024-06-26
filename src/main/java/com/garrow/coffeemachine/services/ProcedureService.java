package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;
import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.procedures.interfaces.Procedure;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import com.garrow.coffeemachine.utils.enums.ProcedureType;
import com.garrow.coffeemachine.utils.enums.ProcessingStatus;
import com.garrow.coffeemachine.utils.exceptions.InsufficientQuantityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcedureService {

    // Самый важный класс который получился самым спорным и ненадежным

    private final BeverageOrderService beverageOrderService;
    private final IngredientService ingredientService;

    @Getter
    private volatile ProcessingStatus processingStatus = ProcessingStatus.IDLE;

    @Cacheable("procedureTypes")
    public List<ProcedureType> findAllTypes() {
        return Arrays.asList(ProcedureType.values());
    }

    public void startProcessing() {
        if (processingStatus != ProcessingStatus.PROCESSING) {
            processOrders();
        }
    }

    @Async
    public void processOrders() {
        processingStatus = ProcessingStatus.PROCESSING;

        while (true) {
            try {
                if (beverageOrderService.hasNext()) {
                    BeverageOrder beverageOrder = beverageOrderService.findNext();
                    log.info("Processing order: {}", beverageOrder.getId());
                    processBeverageOrder(beverageOrder);
                    log.info("Processing orders completed: {}", beverageOrder.getId());
                }
                sleep();
            } catch (Exception exception) {
                log.warn("Thread interrupted, terminating processOrders");
                Thread.currentThread().interrupt();
                processingStatus = ProcessingStatus.IDLE;
                break;
            }
        }
    }

    private void processBeverageOrder(BeverageOrder beverageOrder) {
        try {
            checkIngredients(beverageOrder);
            beverageOrderService.setStatus(beverageOrder.getId(), BeverageOrderStatus.PROCESSING);
            List<Action> actions = sortActions(beverageOrder);
            executeActions(actions);
            beverageOrderService.setStatus(beverageOrder.getId(), BeverageOrderStatus.COMPLETED);
        } catch (Exception exception) {
            beverageOrderService.setStatus(beverageOrder.getId(), BeverageOrderStatus.CANCELED);
            log.error("Exception in processBeverageOrder for order: {}", beverageOrder.getId(), exception);
            throw exception;
        }
    }

    private void checkIngredients(BeverageOrder beverageOrder) {
        try {
            beverageOrder.getBeverage().getActionsIngredients().forEach(actionIngredient ->
                    ingredientService.checkQuantity(actionIngredient.getIngredient().getId(), actionIngredient.getQuantity()));
        } catch (InsufficientQuantityException exception) {
            log.error("Exception in checkIngredients for order: {}", beverageOrder.getId(), exception);
            processingStatus = ProcessingStatus.OUT_OF_STOCK;
            throw exception;
        }
    }

    private List<Action> sortActions(BeverageOrder beverageOrder) {
        try {
            return beverageOrder.getBeverage().getActions().stream()
                    .sorted(Comparator.comparingInt(Action::getOrderIndex)).toList();
        } catch (Exception exception) {
            log.error("Exception in sortActions for order: {}", beverageOrder.getId(), exception);
            throw exception;
        }
    }

    private void executeActions(List<Action> actions) {
        actions.forEach(action -> {
            List<ActionIngredient> actionIngredients = action.getActionIngredients();
            List<ActionArgument> actionArguments = action.getActionArguments();
            ProcedureType procedureType = action.getProcedureType();

            try {
                Procedure procedure = createProcedure(procedureType);
                procedure.execute(actionIngredients, actionArguments);
                decreaseIngredientQuantities(actionIngredients);
            } catch (Exception exception) {
                log.error("Exception in executeActions for action: {}", action.getId(), exception);
            }
        });
    }

    private void decreaseIngredientQuantities(List<ActionIngredient> actionIngredients) {
        try {
            actionIngredients.forEach(actionIngredient ->
                    ingredientService.decreaseQuantity(actionIngredient.getIngredient().getId(), actionIngredient.getQuantity()));
        } catch (InsufficientQuantityException exception) {
            log.error("Exception in decreaseIngredientQuantities", exception);
            processingStatus = ProcessingStatus.OUT_OF_STOCK;
            throw exception;
        }
    }

    private void sleep() throws InterruptedException {
        try {
            // Какое-то время между обработкой заказов. Само время приготовления кофе складывается из времени выполнения процедур
            Thread.sleep(5000);
        } catch (InterruptedException exception) {
            log.warn("Sleep interrupted");
            throw exception;
        }
    }

    private Procedure createProcedure(ProcedureType procedureType) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Procedure> procedureClass = procedureType.getProcedureClass();
        Constructor<? extends Procedure> constructor = procedureClass.getDeclaredConstructor();
        return constructor.newInstance();
    }

    //

    /*private Procedure createProcedure(ProcedureType procedureType) {
        switch (procedureType) {
            case MIX:
                return new MixProcedure();
            default:
                throw new IllegalArgumentException("Unknown procedure type: " + procedureType);
        }
    }*/
}
