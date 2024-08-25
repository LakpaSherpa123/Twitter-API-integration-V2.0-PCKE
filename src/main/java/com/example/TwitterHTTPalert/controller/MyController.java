package com.example.TwitterHTTPalert.controller;
//import com.example.TwitterHTTPalert.service.AlpacaService;
//import com.example.TwitterHTTPalert.service.Alpaca;
//import com.example.TwitterHTTPalert.service.TwitterService;
//import com.example.TwitterHTTPalert.service.Tweets;
//import org.apache.catalina.webresources.JarContents;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.awt.*;
//import java.io.IOException;


import com.example.TwitterHTTPalert.model.ApplicationLogin;
import com.example.TwitterHTTPalert.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyController {

    private final ApplicationService applicationService;

    public static void openWebPage(String urlString) {

        try {
            URI uri = new URI(urlString);
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "", "\"" + uri + "\""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ApplicationLogin user)
    {
        ApplicationLogin isLoggedIn = applicationService.findUser(user);
        if(isLoggedIn != null){
            return ResponseEntity.ok("Welcome back " + isLoggedIn.getFullName());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username or Password");

    }
    @PostMapping("/signup")
    public ResponseEntity<String> signUP(@RequestBody ApplicationLogin data) {
        ApplicationLogin usernameAvailability = applicationService.findUser(data);

        if (usernameAvailability == null) {
            ApplicationLogin signedUpUser = applicationService.signUp(data);
            return ResponseEntity.ok( data.getUsername() + " successfully signed up");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username not available");
        }
    }
}
