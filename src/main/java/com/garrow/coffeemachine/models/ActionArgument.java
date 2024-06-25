package com.garrow.coffeemachine.models;

import com.garrow.coffeemachine.utils.enums.ProcedureType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "action_argument")
public class ActionArgument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Float value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Action action;
}
