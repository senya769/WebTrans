package com.trans.model;

import com.sun.istack.NotNull;
import com.trans.model.enums.Countries;
import com.trans.model.enums.TypeTransport;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "cargo")
public class Cargo {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @NotNull
    private double price;
    private double weight;
    private double volume;
    private boolean isFree = true;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Countries country_from;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Countries country_to;
    @NotNull
    private String city_from;
    @NotNull
    private String city_to;
    @Enumerated(EnumType.STRING)
    private TypeTransport typeTransport;

    @Column(name = "date_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateDeadline;
    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateCreated = LocalDateTime.now();
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "transport")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orderList;

}
