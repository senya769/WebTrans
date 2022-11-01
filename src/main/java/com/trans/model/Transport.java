package com.trans.model;


import com.trans.model.enums.TypeTransport;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private boolean isDelete = false;
    @Enumerated(EnumType.STRING)
    private TypeTransport type;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private User user;
    @OneToMany(mappedBy = "transport",cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Order> orderList;

    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transport transport = (Transport) o;
        return id != null && Objects.equals(id, transport.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
