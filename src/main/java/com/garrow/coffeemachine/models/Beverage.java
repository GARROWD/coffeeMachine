package com.garrow.coffeemachine.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "beverage")
public class Beverage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "beverage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Action> actions;

    public List<ActionIngredient> getActionsIngredients() {
        List<ActionIngredient> actionIngredients = new ArrayList<>();

        for (Action action : actions) {
            actionIngredients.addAll(action.getActionIngredients());
        }

        return actionIngredients;
    }

}
