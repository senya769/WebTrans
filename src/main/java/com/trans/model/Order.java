package com.trans.model;

import com.trans.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;

@Builder(access = AccessLevel.PUBLIC)
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
    private Integer id;
    private Integer customerId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.WAITING;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cargo cargo;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Transport transport;
}
