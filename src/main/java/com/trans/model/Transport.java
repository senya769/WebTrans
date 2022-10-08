package com.trans.model;


import com.trans.model.enums.TypeTransport;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Table(name = "transport")
public class Transport{
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @EqualsAndHashCode.Include
    private String name;
    private double maxCapacityLoad;
    private boolean isFree = true;
    @Enumerated(EnumType.STRING)
    private TypeTransport type;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private User user;
    @OneToMany(mappedBy = "transport")
    @ToString.Exclude
    private List<Order> orderList;

}
