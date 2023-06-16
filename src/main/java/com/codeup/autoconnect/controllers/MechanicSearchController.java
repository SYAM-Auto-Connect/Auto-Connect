package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.MechanicSearchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class MechanicSearchController {


    private final MechanicSearchRepository MechanicSearchDao;

    public MechanicSearchController(MechanicSearchRepository MechanicSearchDao) {
        this.MechanicSearchDao = MechanicSearchDao;
    }

    @GetMapping("/MechanicSearchPage/{id}")
    public String viewSearchedMechanics(@PathVariable long id, Model model) {
        User Mechanic1 = MechanicSearchDao.findById(id).get();
        model.addAttribute("MechanicProfile", Mechanic1);
        return "/MechanicSearchPage";

    }

    @GetMapping("/MechanicSearchPage")
    public String viewAllMechanics(Model model) {
        model.addAttribute("ViewAllMechanics", MechanicSearchDao.findAllMechanicUsers());

        return "/MechanicSearchPage";
    }
}
