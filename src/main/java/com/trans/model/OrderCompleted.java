package com.trans.model;

import com.trans.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders-completed")
public class OrderCompleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    private LocalDate dateCompleted;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.WAITING;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Cargo cargo;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Transport transport;
}
