package com.example.TwitterHTTPalert.service;

import com.example.TwitterHTTPalert.TwitterHttPalertApplication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Tweets {


    public void postTweets(String passedToken, String posts) throws URISyntaxException, IOException, InterruptedException {

        String postThisTweet = posts;

        String token = passedToken;



        System.out.println("This is token while posting " + token);
        JSONObject payload = new JSONObject();
        payload.put("text",postThisTweet);

        //Creating a new HTTP Client
        HttpClient client = HttpClient.newHttpClient();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.twitter.com/2/tweets"))
                //.header("Authorization", "Basic " + encoded)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
 

        //sending the request from the client with client.send
        //Storing in the HTTP response as a list of string as it returns its status code, and all the requested headers and body
        //HttpResponse.BodyHandlers.ofString() :This specifies to handle the body as String.

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response + response.body());

    }
}
