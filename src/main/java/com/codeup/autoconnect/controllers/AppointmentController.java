package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.Post;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

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

        appointment.setPrice(price);

        appointment.setReceiver(receiver);
        System.out.println(receiver);
        appointment.setRequester(currentUser); // Set the current user as the requester
        appointment.setTitle(title);

        appointment.setDate(date);
        System.out.println(date);
        appointmentRepository.save(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return "appointments/appointment_success";
    }
    @GetMapping("/paymentDetail/{id}/edit")
    public String showAppointmentEditForm(@PathVariable long id, Model model, Principal principal){
        if (appointmentRepository.findById(id).isPresent()) {
            Appointment appointment = appointmentRepository.findById(id).get();
            model.addAttribute("appointment", appointment);
            User user = userDao.findByUsername(principal.getName());
            model.addAttribute("currentUser", user);
        }
        return "appointments/appointment_edit";
    }
    @PostMapping("paymentDetail/{id}/edit")
    public String submitAppointmentEditForm(@PathVariable long id,
                                            @ModelAttribute Appointment editAppointment,
                                            Principal principal,
                                            Model model){
        Appointment appointment = appointmentRepository.findById(id).get();

        User user = userDao.findByUsername(principal.getName());

        model.addAttribute("currentUser", user);
        appointment.setTitle(editAppointment.getTitle());
        appointment.setDescription(editAppointment.getDescription());
        appointment.setDate(editAppointment.getDate());
        appointment.setPrice(editAppointment.getPrice());

        appointmentRepository.save(appointment);

        return "/appointments/appointment_edit_success";

    }
    @PostMapping("paymentDetail/{id}/delete")
    public String deleteAppointmentForm(@PathVariable long id,
                                        Principal principal) {

        User user = userDao.findByUsername(principal.getName());
        Appointment appointment = appointmentRepository.findById(id).get();
        if(appointment.getRequester().getId() == user.getId()) {
            appointmentRepository.delete(appointment);
        }
        return "/appointments/appointment_delete_success";
    }

}

