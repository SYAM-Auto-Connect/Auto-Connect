package com.codeup.autoconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {
    @GetMapping("/calendar")
        public String showCal(){
            return "calendar";
        }
}
