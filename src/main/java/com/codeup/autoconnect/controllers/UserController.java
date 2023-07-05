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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
       return "users/registration";
   }

   @PostMapping("/registration")
    public String submitRegistrationForm(@ModelAttribute User user, @RequestParam String userType, Model model){
       boolean isMechanic = userType.equals("mechanic");
       user.setIsMechanic(isMechanic);
       String password = user.getPassword();
       String patternPW = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
       Pattern pattern = Pattern.compile(patternPW);
       Matcher matcher = pattern.matcher(password);
       if(!matcher.matches()){
           model.addAttribute("passwordError", "Password must be between 8-16 characters, and include at least one upper case, one lower case, one digit, and one symbol.");
           return "users/registration";
       }
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       String imageUrl = "/img/image.jpeg";
       user.setProfilePicture(imageUrl);
       usersDao.save(user);
       return "users/registration_success";
   }


}
//       if (!validatePassword(password)) {
//           // Return an error view or redirect to registration form
//           return "users/registration_error";
//       }
//    private boolean validatePassword(String password) {
//        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
//        return Pattern.compile(pattern).matcher(password).matches();
//    }