package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Appointment;
import com.codeup.autoconnect.models.Post;
import com.codeup.autoconnect.models.Review;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.AppointmentRepository;
import com.codeup.autoconnect.repositories.PostRepository;
import com.codeup.autoconnect.repositories.ReviewRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ProfileController {

    private final AppointmentRepository apptDao;
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewDao;
    public final PostRepository postsDao;


    public ProfileController(AppointmentRepository apptDao, UserRepository userDao, PasswordEncoder passwordEncoder, ReviewRepository reviewDao, PostRepository postsDao) {
        this.apptDao = apptDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.reviewDao = reviewDao;
        this.postsDao = postsDao;
    }

    @GetMapping("/profile")
    public String showProfile( Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId()).get();
        List<Review> reviews = reviewDao.findAllByMechanic(user);
        List<Post> posts = postsDao.findAllByUser(user);
        System.out.println(posts);
        model.addAttribute("posts",posts);
        model.addAttribute("user", user);
        model.addAttribute("reviews",reviews);

        return "profile";
    }

    @GetMapping("profile/{id}")
    public String showProfileUsers (@PathVariable long id, Model model){
        User user = userDao.findById(id).get();
        List<Review> reviews = reviewDao.findAllByMechanic(user);
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        return "profile";
//        return "mechanic";
    }

    @GetMapping("/profile/{id}/edit")
    public String showEditProfile(@PathVariable long id,  Model model) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getId() != id){
            throw new AccessDeniedException("You cannot edit other users' profile");
        }
        if(userDao.findById(id).isPresent()){
            model.addAttribute("user", userDao.findById(id).get());
        }
        return "users/edit";
    }
    @PostMapping("/profile/{id}/edit")
    public String submitEditProfile(@PathVariable long id, @ModelAttribute User editProfile) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.getId() != id){
            throw new AccessDeniedException("You cannot edit other users' profile");
        }
        userDao.save(editProfile);

        return "users/edit_success";

    }

    @GetMapping("/profile/{id}/setting")
    public String showSettingForm (@PathVariable long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(userDao.findById(id).isPresent()){
            model.addAttribute("user", user);
        }
        return "users/setting";
    }
    @PostMapping("/profile/{id}/setting/password")
    public String submitResetPW (Model model, @ModelAttribute User user, @PathVariable long id, @RequestParam("password") String password, HttpSession session) throws AccessDeniedException {
        User loggedInUser = userDao.findById(id).get();
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedInUser.getId() != authorizedUser.getId()){
            throw new AccessDeniedException("You are not authorized to change password");
        }
        password = user.getPassword();
        String patternPW = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
        Pattern pattern = Pattern.compile(patternPW);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()){
            model.addAttribute("passwordError", "Password must be between 8-16 characters, and include at least one upper case, one lower case, one digit, and one symbol.");
            return "users/setting";
        }
        loggedInUser.setPassword(passwordEncoder.encode(password));
        System.out.println("New encoded password: "+loggedInUser.getPassword());

        userDao.save(loggedInUser);
        session.invalidate();

        return "users/pw_success";
    }

    @PostMapping("/profile/{id}/setting/delete")
    public String submitDelete (HttpSession session, @PathVariable long id) throws AccessDeniedException {
        User loggedInUser = userDao.findById(id).get();
        User authorizedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(loggedInUser.getId() != authorizedUser.getId()){
            throw new AccessDeniedException("You cannot delete other user's account");
        }
        userDao.deleteById(id);
        session.invalidate();
        return "users/delete_success";
    }


    }


