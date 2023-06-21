package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {


    private final AppointmentRepository apptDao;
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;


    public ProfileController(AppointmentRepository apptDao, UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.apptDao = apptDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/profile")
    public String showProfile( Model model) {

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
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

    @GetMapping("/profile/{id}/setting")
    public String showSettingForm (@PathVariable long id, Model model) {
        if(userDao.findById(id).isPresent()){
            model.addAttribute("user", userDao.findById(id).get());
        }
        return "users/setting";
    }
    @PostMapping("/profile/{id}/setting/password")
    public String submitResetPW (@PathVariable long id, @RequestParam("password") String password, HttpSession session){
        User loggedInUser = userDao.findById(id).get();
        loggedInUser.setPassword(passwordEncoder.encode(password));
        userDao.save(loggedInUser);
        session.invalidate();
        return "redirect:/login";
    }
    @PostMapping("/profile/{id}/setting/delete")
    public String submitDelete (HttpSession session, @PathVariable long id){
        userDao.deleteById(id);
        session.invalidate();
        return "redirect:/";
    }
}

