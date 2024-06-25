package com.garrow.coffeemachine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "action_ingredient")
public class ActionIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Action action;

}
