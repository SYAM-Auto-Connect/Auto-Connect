package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.repositories.AppointmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final AppointmentRepository apptDao;

    public ProfileController(AppointmentRepository apptDao) {
        this.apptDao = apptDao;
    }


    @GetMapping("/profile")
    public String showProfile(Model model) {
        System.out.println(apptDao.findAll());
        // Add any necessary model attributes for profile information
        return "profile";
    }
}

