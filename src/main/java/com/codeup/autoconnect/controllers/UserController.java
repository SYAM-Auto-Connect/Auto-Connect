package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
   private final UserRepository usersDao;
   private final PasswordEncoder passwordEncoder;



   public UserController (UserRepository userDao, PasswordEncoder passwordEncoder) {
       this.usersDao = userDao;
       this.passwordEncoder = passwordEncoder;
   }

   @GetMapping("/registration")
    public String showRegistrationForm(Model model){
       model.addAttribute("user", new User());
       return "/users/registration";
   }

   @PostMapping("/registration")
    public String submitRegistrationForm(@ModelAttribute User user, @RequestParam String userType){
       boolean isMechanic = userType.equals("mechanic");
       user.setIsMechanic(isMechanic);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       usersDao.save(user);
       return "/users/registration_success";
   }

}
