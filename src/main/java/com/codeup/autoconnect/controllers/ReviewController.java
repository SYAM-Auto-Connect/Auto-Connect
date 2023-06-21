package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.repositories.ReviewRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {

    public final ReviewRepository reviewDao;
    public final UserRepository userDao;

    public ReviewController(ReviewRepository reviewDao, UserRepository userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }
    @GetMapping("/reviews")
    public String reviewAllPosts(Model model) {
        model.addAttribute("posts", reviewDao.findAll());
        return "reviews/review";
    }

}
