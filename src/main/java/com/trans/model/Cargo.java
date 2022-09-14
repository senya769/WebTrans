package com.trans.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


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

    public Cargo() {
    }

    public Cargo(String weight, String length, String height, String country_from, String country_to, LocalDateTime localDateDeadline, LocalDateTime localDateCreated, User user) {
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.country_from = country_from;
        this.country_to = country_to;
        this.localDateDeadline = localDateDeadline;
        this.localDateCreated = localDateCreated;
        this.user = user;
    }

/*
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCountry_from() {
        return country_from;
    }

    public void setCountry_from(String country_from) {
        this.country_from = country_from;
    }

    public String getCountry_to() {
        return country_to;
    }

    public void setCountry_to(String country_to) {
        this.country_to = country_to;
    }

    public LocalDateTime getLocalDateDeadline() {
        return localDateDeadline;
    }

    public void setLocalDateDeadline(LocalDateTime localDateDeadline) {
        this.localDateDeadline = localDateDeadline;
    }

    public void setLocalDateDeadline(String localDateDeadline) {
        this.localDateDeadline = LocalDateTime.parse(localDateDeadline);
    }

    public LocalDateTime getLocalDateCreated() {
        return localDateCreated;
    }

    public void setLocalDateCreated(LocalDateTime localDateCreated) {
        this.localDateCreated = localDateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
