package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {


    private final AppointmentRepository apptDao;
    private final UserRepository userDao;

    public ProfileController(AppointmentRepository apptDao, UserRepository userDao) {
        this.apptDao = apptDao;
        this.userDao = userDao;
    }


    @GetMapping("/profile")
    public String showProfile( Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String showEditProfile(@PathVariable long id,  Model model){
        if(userDao.findById(id).isPresent()){
            model.addAttribute("user", userDao.findById(id).get());
        }
        return "users/edit";
    }
    @PostMapping("/profile/{id}/edit")
    public String submitEditProfile(@ModelAttribute User editProfile){

        userDao.save(editProfile);
        return "redirect:/profile";
    }
}

