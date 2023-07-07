package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class CalendarRestController {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public CalendarRestController(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    // Fetch currently logged in users events
    @GetMapping("/api/appointments")
    public List<Appointment> getAppointments(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username);

        if(currentUser.getIsMechanic()) {
            return appointmentRepository.findAppointmentsByRequesterId(currentUser.getId());
        } else {
            return appointmentRepository.findAppointmentsByReceiverId(currentUser.getId());
        }
    }
}

