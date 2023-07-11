package com.codeup.autoconnect.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "appointments")
public class Appointment {
    private String title;
    public Appointment(String title, LocalDate date, String description, Double price){
        this.title = title;
        this.date = date;
        this.description = description;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL")
    private Double price;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    @JsonIgnore
    private User requester;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id")
    private User receiver;
}
