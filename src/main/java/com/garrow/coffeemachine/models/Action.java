package com.garrow.coffeemachine.models;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "procedure_type")
    @Enumerated(EnumType.STRING)
    private ProcedureType procedureType;

    @Column(name = "order_index")
    private Integer orderIndex;

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ActionIngredient> actionIngredients;

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ActionArgument> actionArguments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Beverage beverage;

}
