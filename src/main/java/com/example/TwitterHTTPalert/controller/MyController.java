package com.example.TwitterHTTPalert.controller;
import com.example.TwitterHTTPalert.service.AlpacaAuth;
import com.example.TwitterHTTPalert.service.AlpacaTrades;
import com.example.TwitterHTTPalert.service.HttpAuth;
import com.example.TwitterHTTPalert.service.Tweets;
import org.apache.catalina.webresources.JarContents;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api")
public class MyController {

    private String passed_code_verifier;
    private String passed_code_challenge;

    private String passed_token;
    public void openWebPage(String urlString) {

        try {
            URI uri = new URI(urlString);
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "", "\"" + uri + "\""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/data")
    public String getData(){

        return "Hello from Java17";
    }

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() throws IOException, NoSuchAlgorithmException, URISyntaxException, InterruptedException {
        HttpAuth http = new HttpAuth();
        String link = http.authenticateMyAccount();
        passed_code_verifier = http.getCode_verifier();             //This will save the data while opening /callback
        passed_code_challenge = http.getCode_challenge();
        openWebPage(link);
        return ResponseEntity.ok(link);
    }


    @GetMapping("/callback")
    public ResponseEntity<String> callBackURL(@RequestParam(value = "code") String response) throws URISyntaxException, IOException, InterruptedException {

        HttpAuth http = new HttpAuth();
        String token = http.getToken(response, passed_code_challenge,passed_code_verifier);
        System.out.println("Token Generated = " + token);
        this.passed_token = token;
//        Tweets tweet = new Tweets();
//
//        tweet.postTweets(token);
        return ResponseEntity.ok("Token Generated " + response);
    }

    @PostMapping("/tweet")
    public ResponseEntity<String> post(@RequestBody String text) throws URISyntaxException, IOException, InterruptedException {

            Tweets tweet = new Tweets();
            tweet.postTweets(passed_token, text);

        return ResponseEntity.ok("Posted");
    }
    @GetMapping("/callbackAlpaca")
    public ResponseEntity<String> callBackAlpaca(@RequestParam(value = "code") String response) throws URISyntaxException, IOException, InterruptedException {

        System.out.println("Inside the callback");
        AlpacaAuth http = new AlpacaAuth();
        String token = http.getTokenAlpaca(response);
        System.out.println("Token Generated = " + token);
        return ResponseEntity.ok("Token Generated " + response);
    }
    @GetMapping("/alpacaTest")
    public ResponseEntity<String> tradovateAPI(){
        AlpacaAuth auth = new AlpacaAuth();
        String authUrl  = auth.authenticateAlpaca();
        openWebPage(authUrl);
        return ResponseEntity.ok("This is Alpaca api");
    }

    @PostMapping("/placeAlpacaOrder")
    public ResponseEntity<String> placeAlpacaOrder(@RequestBody String passedOrder) throws Exception {

        System.out.println("Passed Json Order    ------> " + passedOrder);
        JSONObject json = new JSONObject(passedOrder);
        System.out.println("Passed Json Order    ------> " + json.getString("symbol"));
           AlpacaTrades order = new AlpacaTrades();

           String placingOrder = order.placeOrder(json.getString("symbol"), json.getInt("qty") , json.getString("side"), json.getString("market"), json.getString("timeInForce"));


        return ResponseEntity.ok("Posted");

    }
}
