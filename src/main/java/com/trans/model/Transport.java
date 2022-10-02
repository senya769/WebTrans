package com.trans.model;


import com.trans.model.enums.TypeTransport;
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
    private TypeTransport type;
    @ManyToOne
    private User user;
}
