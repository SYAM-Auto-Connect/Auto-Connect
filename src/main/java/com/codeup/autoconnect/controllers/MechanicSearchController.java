package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.MechanicSearchRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MechanicSearchController {


    private final MechanicSearchRepository MechanicSearchDao;

    public MechanicSearchController(MechanicSearchRepository MechanicSearchDao) {
        this.MechanicSearchDao = MechanicSearchDao;
    }

//    @GetMapping("/MechanicSearchPage/{id}")
//    public String viewSearchedMechanics(@PathVariable long id, Model model) {
//        User Mechanic1 = MechanicSearchDao.findById(id).get();
//        model.addAttribute("MechanicProfile", Mechanic1);
//        return "/MechanicSearchPage";
//
//    }

//    @GetMapping("/MechanicSearchPage")
//    public String viewAllMechanics(Model model)  {
//        model.addAttribute("ViewAllMechanics", MechanicSearchDao.findAllMechanicUsers());
//
//        return "/MechanicSearchPage";
//    }
//
//    @GetMapping("/MechanicSearchPage/{keyword}")
//    public String searchMechanicsByKeyword(@PathVariable String keyword, Model model) {
//        List<User> mechanics = new ArrayList<>();
//
//        for (User mechanic : MechanicSearchDao.findAllMechanicUsers()) {
//            if (mechanic.getFirst_name().toLowerCase().contains(keyword.toLowerCase()) ||
//                    mechanic.getLast_name().toLowerCase().contains(keyword.toLowerCase()) ||
//                    mechanic.getAddress_city().toLowerCase().contains(keyword.toLowerCase()) ||
//                    mechanic.getAddress_street().toLowerCase().contains(keyword.toLowerCase())) {
//                mechanics.add(mechanic);
//            }
//        }
//        model.addAttribute("searchResults", mechanics);
//        return "/MechanicSearchPage";
//    }











    @GetMapping("/MechanicSearchPage")
    public String viewAllMechanics(@RequestParam(required = false) String keyword, Model model) {

        model.addAttribute("ViewAllMechanics", MechanicSearchDao.findAllMechanicUsers());
        List<User> mechanics;

        if (keyword != null && !keyword.isEmpty()) {
            mechanics = new ArrayList<>();
            for (User mechanic : MechanicSearchDao.findAllMechanicUsers()) {
                if (mechanic.getFirst_name().toLowerCase().contains(keyword.toLowerCase()) ||
                        mechanic.getLast_name().toLowerCase().contains(keyword.toLowerCase()) ||
                        mechanic.getAddress_city().toLowerCase().contains(keyword.toLowerCase()) ||
                        mechanic.getAddress_street().toLowerCase().contains(keyword.toLowerCase()) || String.valueOf(mechanic.getAddress_zip()).contains(keyword)) {
                    mechanics.add(mechanic);
                }
            }
            model.addAttribute("searchResults", mechanics);
//        } else {
//            mechanics = MechanicSearchDao.findAllMechanicUsers();
//            model.addAttribute("ViewAllMechanics", mechanics);
        }

        return "/MechanicSearchPage";
    }

}
