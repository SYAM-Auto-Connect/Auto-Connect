package com.codeup.autoconnect.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class CalendarEvent {
    private String title;
    private Date date;


    public CalendarEvent(String title, Date date) {
        this.title = title;
        this.date = date;
    }

}




