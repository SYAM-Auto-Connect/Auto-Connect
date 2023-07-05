package com.codeup.autoconnect.controllers;

import com.codeup.autoconnect.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeyController {

    @Autowired
    private Keys keys;

    @GetMapping(value="/keys.js", produces = "application/javascript")
    public String getKeys() {
        return String.format("""
                const MAPBOX_KEY = "%s";
                const API_KEY1 = "%s";
                const FILESTACK_KEY ="%s";
        """, keys.getMAPBOX_KEY(), keys.getAPI_KEY1(),keys.getFILESTACK_KEY());
    }
}





