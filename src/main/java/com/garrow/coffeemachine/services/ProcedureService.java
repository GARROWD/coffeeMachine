package com.garrow.coffeemachine.services;

import com.garrow.coffeemachine.models.Action;
import com.garrow.coffeemachine.models.ActionArgument;
import com.garrow.coffeemachine.models.ActionIngredient;
import com.garrow.coffeemachine.models.BeverageOrder;
import com.garrow.coffeemachine.procedures.MixProcedure;
import com.garrow.coffeemachine.procedures.interfaces.Procedure;
import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import com.garrow.coffeemachine.utils.enums.ProcedureType;
import com.garrow.coffeemachine.utils.enums.ProcessingStatus;
import com.garrow.coffeemachine.utils.exceptions.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcedureService {

    private final BeverageOrderService beverageOrderService;
    private final IngredientService ingredientService;

    @Getter
    private volatile ProcessingStatus processingStatus = ProcessingStatus.IDLE;

    @Cacheable("procedureTypes")
    public List<ProcedureType> findAllTypes() {
        return Arrays.asList(ProcedureType.values());
    }

    public void startProcessing(){
        if(processingStatus == ProcessingStatus.IDLE){
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
                }
                sleep();
            } catch (InterruptedException e) {
                log.warn("Thread interrupted, terminating processOrders");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception exception) {
                log.error("Exception in processOrders", exception);
            }
        }
        processingStatus = ProcessingStatus.IDLE;
    }

    private void processBeverageOrder(BeverageOrder beverageOrder) {
        try {
            checkIngredients(beverageOrder);
            List<Action> actions = sortActions(beverageOrder);
            executeActions(actions);
        } catch (Exception exception) {
            log.error("Exception in processBeverageOrder for order: {}", beverageOrder.getId(), exception);
        }
    }

    private void checkIngredients(BeverageOrder beverageOrder) {
        try {
            beverageOrder.getBeverage().getActionsIngredients().forEach(actionIngredient ->
                            ingredientService.checkQuantity(actionIngredient.getIngredient().getId(), actionIngredient.getQuantity()));
        } catch (Exception exception) {
            log.error("Exception in checkIngredients for order: {}", beverageOrder.getId(), exception);
            throw exception;
        }
    }

    private List<Action> sortActions(BeverageOrder beverageOrder) {
        try {
            List<Action> sortedActions = beverageOrder.getBeverage().getActions().stream()
                    .sorted(Comparator.comparingInt(Action::getOrderIndex)).toList();
            return sortedActions;
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
                    ingredientService.decreaseQuantity(actionIngredient.getIngredient().getId(), actionIngredient.getQuantity())
            );
        } catch (Exception exception) {
            log.error("Exception in decreaseIngredientQuantities", exception);
            throw exception;
        }
    }

    private void sleep() throws InterruptedException {
        try {
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
