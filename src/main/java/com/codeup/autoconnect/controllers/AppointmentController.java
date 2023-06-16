package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {

    private List<Appointment> appointments = new ArrayList<>();

    @GetMapping("/")
    public String calendar() {
        return "calendar";
    }

    @GetMapping("/appointments")
    @ResponseBody
    public List<Appointment> getAppointments() {
        return appointments;
    }

    @PostMapping("/create-appointment")
    public String createAppointment(@RequestParam String title, @RequestParam String date) {
        Appointment appointment = new Appointment(title, date);
        appointments.add(appointment);
        return "redirect:/profile";
    }
}

