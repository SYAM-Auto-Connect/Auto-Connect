package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.Review;
import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.ReviewRepository;
import com.codeup.autoconnect.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReviewController {

    public final ReviewRepository reviewDao;
    public final UserRepository userDao;

    public ReviewController(ReviewRepository reviewDao, UserRepository userDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

    @GetMapping("/review/{id}")
    public String showReview(@PathVariable long id, Model model) {
        Review review = reviewDao.findById(id).get();
        model.addAttribute("review", review);
        return "reviews/detail";
    }

    @GetMapping("review/{id}/create")
    public String showCreateReview(@PathVariable long id, Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == id) {
            return "redirect:/profile?error=selfReview";
        }

        User mechanic = userDao.findById(id).get();
        Review review = new Review();
        review.setMechanic(mechanic);
        model.addAttribute("review", review);
        model.addAttribute("user", mechanic);
        return "reviews/create";
    }

    @PostMapping("review/{id}/create")
    public String submitCreateReview(@PathVariable long id, @ModelAttribute Review review){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User mechanic = userDao.findById(id).get();

        if (loggedInUser.getId() == mechanic.getId()) {
            return "redirect:/users/not_authorized";
        }

        review.setUser(loggedInUser);
        review.setMechanic(mechanic);
        reviewDao.save(review);
        return "redirect:/profile/" + mechanic.getId();
    }
    @GetMapping("/review/{id}/edit")
    public String showEditReview(@PathVariable long id, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review existingReview = reviewDao.findById(id).get();

        if (loggedInUser.getId() != existingReview.getUser().getId()) {
            return "redirect:/profile?error=notYourReview";
        }

        model.addAttribute("review", existingReview);
        return "reviews/edit";
    }

    @PostMapping("/review/{id}/edit")
    public String submitEditReview(@PathVariable long id, @ModelAttribute Review review) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review existingReview = reviewDao.findById(id).get();

        if (loggedInUser.getId() != existingReview.getUser().getId()) {
            return "redirect:/profile?error=notYourReview";
        }

        existingReview.setRating(review.getRating());
        existingReview.setBody(review.getBody());
        reviewDao.save(existingReview);
        return "redirect:/profile/" + existingReview.getMechanic().getId();
    }

    @PostMapping("/review/{id}/delete")
    public String submitDeleteReview(@PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Review existingReview = reviewDao.findById(id).get();

        if (loggedInUser.getId() != existingReview.getUser().getId()) {
            return "redirect:/profile?error=notYourReview";
        }
        reviewDao.delete(existingReview);
        return "redirect:/profile/" + existingReview.getMechanic().getId();
    }



}
