package com.codeup.autoconnect.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PhotoUploadController {

    @PostMapping("/update-profile-photo/{userId}")
    public ResponseEntity<String> updateProfilePhoto(@PathVariable String userId, @RequestBody String photoUrl) {

        return new ResponseEntity<>("Profile photo updated successfully for user ID: " + userId, HttpStatus.OK);
    }
}
