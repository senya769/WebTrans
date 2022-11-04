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
    private boolean isDelete = false;
    private boolean isFree = true;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Countries countryFrom;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Countries countryTo;
    @NotNull
    private String cityFrom;
    @NotNull
    private String cityTo;
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

    @OneToMany(mappedBy = "transport",cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orderList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cargo cargo = (Cargo) o;
        return id != null && Objects.equals(id, cargo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
