package com.example.TwitterHTTPalert.controller;
import com.example.TwitterHTTPalert.service.TwitterService;
import com.example.TwitterHTTPalert.service.Tweets;
import org.springframework.http.ResponseEntity;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api")
public class TwitterController {

    private String passed_code_verifier;
    private String passed_code_challenge;
    private String passed_token;

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {

        try {
            TwitterService http = new TwitterService();
            String link = http.authenticateMyAccount();
            passed_code_verifier = http.getCode_verifier();             //Saving data for this session
            passed_code_challenge = http.getCode_challenge();
            MyController.openWebPage(link);
            return ResponseEntity.ok(link);
        }catch (Exception e)
        {
            return ResponseEntity.ok(e.toString());
        }
    }


    //@RequestParam will get the value from the URL itself
    @GetMapping("/callback")
    public ResponseEntity<String> callBackURL(@RequestParam(value = "code") String response) throws URISyntaxException, IOException, InterruptedException {

        try {
            TwitterService http = new TwitterService();
            String token = http.getToken(response, passed_code_challenge, passed_code_verifier);
            System.out.println("Token Generated = " + token);
            this.passed_token = token;                                                              //Storing this data for this session

            return ResponseEntity.ok("Token Generated " + response);

        }catch(Exception e){
            return ResponseEntity.ok(e.toString());
        }
    }

    @GetMapping("/data")
    public void getData(){

        System.out.println("Inside data parsing");
        Tweets t = new Tweets();
        t.parseTweet();

    }
}
