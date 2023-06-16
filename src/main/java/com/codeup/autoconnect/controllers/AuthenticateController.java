package com.codeup.autoconnect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticateController {

    @GetMapping("/login")
    public String showLoginForm(){
        return "users/login";
    }

}
