package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.CalendarEvent;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {
    private AppointmentRepository appointmentRepository;
    private UserRepository userDao;

    public AppointmentController(AppointmentRepository appointmentRepository, UserRepository userDao) {
        this.appointmentRepository = appointmentRepository;
        this.userDao = userDao;
    }


    @PostMapping("/create-appointment")
    public String createAppointment(@RequestParam String title,
                                    @RequestParam LocalDate date,
                                    @RequestParam String description,
                                    @RequestParam double price,
                                    @RequestParam(name = "customer") String receiverUsername,
                                    Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userDao.findByUsername(username);
        User receiver = userDao.findByUsername(receiverUsername);


        Appointment appointment = new Appointment(title, date, description, price);
        appointment.setDescription(description);
        System.out.println(description);
        appointment.setPrice(price);
        System.out.println(price);
        appointment.setReceiver(receiver);
        System.out.println(receiver);
        appointment.setRequester(currentUser); // Set the current user as the requester
        appointment.setTitle(title);
        System.out.println(title);
        appointment.setDate(date);
        System.out.println(date);
        appointmentRepository.save(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return "redirect:/paymentDetail?id=" + savedAppointment.getId();    }

}

