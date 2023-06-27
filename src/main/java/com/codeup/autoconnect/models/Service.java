package com.codeup.autoconnect.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Service {
    private String title;
    private String description;
    private double price;
    private String clientSecret;

}