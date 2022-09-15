package com.trans.model;

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
public class Cargo extends AbstractEntity{
    private String weight;
    private String length;
    private String height;
    private String country_from;
    private String country_to;
    @Column(name = "date_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateDeadline;
    @Column(name = "date_created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateCreated = LocalDateTime.now();
    @ManyToOne
    private User user;

}
