package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.MechanicSearchRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/users" ,headers = "Accept=application/json")
public class ListAllMechanicsController {

    private final MechanicSearchRepository MechanicSearchDao;

    public ListAllMechanicsController(MechanicSearchRepository mechanicSearchDao) {
        MechanicSearchDao = mechanicSearchDao;
    }

    @GetMapping("/mechanic-list")
    public List<User>allMechanics(){
//        System.out.println(MechanicSearchDao.findAllMechanicUsers());
        return MechanicSearchDao.findAllMechanicUsers();

    };
}
