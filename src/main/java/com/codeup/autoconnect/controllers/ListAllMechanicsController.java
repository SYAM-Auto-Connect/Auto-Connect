package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.models.User;
import com.codeup.autoconnect.repositories.MechanicSearchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/users" ,headers = "Accept=application/json")
public class ListAllMechanicsController {

    private final MechanicSearchRepository MechanicSearchDao;

    public ListAllMechanicsController(MechanicSearchRepository mechanicSearchDao) {
        MechanicSearchDao = mechanicSearchDao;
    }

//    @GetMapping("/mechanic-list")
//    public List<User>allMechanics(){
////        System.out.println(MechanicSearchDao.findAllMechanicUsers());
////        MechanicSearchDao.findAll().get().getAddress_zip();
//
//        return MechanicSearchDao.findAllMechanicUsers();
//
//    };

    @GetMapping("/mechanic-list")
    public List<User> allMechanicsByZip(@RequestParam("zipcode") String zipcode) {
//        System.out.println(MechanicSearchDao.findAllByAddressZip(zipcode));
        return MechanicSearchDao.findAllByAddressZip(zipcode);
    }

}
