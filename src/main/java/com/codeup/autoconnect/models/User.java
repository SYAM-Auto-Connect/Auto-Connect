package com.codeup.autoconnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String first_name;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String last_name;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String username;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(240)")
    private String email;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isMechanic;

    @Column(columnDefinition = "VARCHAR(255)")
    private String certification;

    @Column(columnDefinition = "VARCHAR(100)")
    private String address_street;

    @Column(columnDefinition = "VARCHAR(50)")
    private String address_city;

    @Column(columnDefinition = "VARCHAR(50)")
    private String address_state;

    @Column(columnDefinition = "VARCHAR(10)")
    private String address_zip;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requester")
    private List<Appointment> requester;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private List<Appointment> receiver;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Message> sender;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipient")
    private List<Message> recipient;


    public User(User copy){
        this.id = copy.id;
        this.email = copy.email;
        this.username = copy.username;
        this.password = copy.password;
    }

}
