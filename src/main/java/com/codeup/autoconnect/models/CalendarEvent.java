package com.codeup.autoconnect.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CalendarEvent {
    private String title;
    private LocalDate date;


    public CalendarEvent(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

}




