package com.trans.model;

import com.trans.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    private Cargo cargo;
    @ManyToOne
    private Transport transport;
}
