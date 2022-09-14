package com.trans.model;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Table(name = "transport")
public class Transport extends AbstractEntity{
    private double maxCapacityLoad;
    private boolean isFree = true;
    @Enumerated(EnumType.STRING)
    private TypeTransport typeTransport;
    @ManyToOne
    private User user;

}
