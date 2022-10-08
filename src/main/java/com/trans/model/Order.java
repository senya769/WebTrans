package com.trans.model;

import com.trans.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cargo cargo;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Transport transport;
}
