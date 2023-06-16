package com.codeup.autoconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(200)")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String car_make;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String car_body;

    @Column(nullable = false, columnDefinition = "INT")
    private int car_year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}