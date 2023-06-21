package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.CalendarEvent;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {
    private AppointmentRepository appointmentRepository;
    private UserRepository userDao;

    public AppointmentController(AppointmentRepository appointmentRepository, UserRepository userDao){
        this.appointmentRepository = appointmentRepository;
        this.userDao = userDao;
    }


    @PostMapping("/create-appointment")
    public String createAppointment(@RequestParam String title, @RequestParam LocalDate date, Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userDao.findByUsername(username);

        Appointment appointment = new Appointment(title, date);
        appointment.setDescription("test");
        appointment.setPrice(1.99);
//        appointment.setReceiver(userDao.findById(3L).get());
        appointment.setRequester(currentUser); // Set the current user as the requester
        appointment.setTitle(title);
        appointment.setDate(date);
        appointmentRepository.save(appointment);

        return "redirect:/profile";
    }

}

