package com.trans.model;

import com.trans.model.enums.OrderStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateCreated;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Transport transport;

}
