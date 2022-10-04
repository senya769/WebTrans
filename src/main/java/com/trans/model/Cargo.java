package com.trans.model;

import com.sun.istack.NotNull;
import com.trans.model.enums.Countries;
import com.trans.model.enums.TypeTransport;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cargo")
@Getter
@Setter
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @NotNull
    private double price;
    private String weight;
    private String volume;
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
    private User user;

}
