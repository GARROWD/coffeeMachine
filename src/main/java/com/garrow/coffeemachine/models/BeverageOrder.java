package com.garrow.coffeemachine.models;

import com.garrow.coffeemachine.utils.enums.BeverageOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "beverage_order")
public class BeverageOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BeverageOrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beverage_id", referencedColumnName = "id")
    private Beverage beverage;

    @Column(name = "created")
    private LocalDateTime created;

}
