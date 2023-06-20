package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
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


//    @GetMapping("/")
//    public String calendar() {
//        return "calendar";
//    }

//    @GetMapping("/appointments")
//    @ResponseBody
//    public List<Appointment> getAppointments() {
//        List<Appointment> appointments = appointmentRepository.findAll();
//
//        System.out.println(appointments);
//        return appointments;
//    }

    @PostMapping("/create-appointment")
    public String createAppointment(@RequestParam String title, @RequestParam LocalDate date) {
        System.out.println(title);
        System.out.println(date);
        Appointment appointment = new Appointment(title, date);
        appointment.setDescription("test");
        appointment.setPrice(1.99);
        appointment.setReceiver(userDao.findById(1L).get());
        appointment.setRequester(userDao.findById(2L).get());
        appointment.setTitle(title);
        appointment.setDate(date);
        appointmentRepository.save(appointment);


        return "redirect:/profile";
    }
}

