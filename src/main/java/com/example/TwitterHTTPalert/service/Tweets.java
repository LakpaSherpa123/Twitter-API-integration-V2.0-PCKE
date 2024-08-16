package com.example.TwitterHTTPalert.service;

import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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



    public void parseTweet(){
        String tweet = " #ALERT\n" +
                "\n" +
                "BTO $SPY 8/16 552C\n" +
                "\n" +
                "1.49\n" +
                "\n" +
                "_____\n" +
                "\n" +
                "Friday #LOTTO\n" +
                "\n" +
                "SIZE/EXPECT 0\n" +
                "\n" +
                "Room to add once on weakness";

        Pattern pattern = Pattern.compile("BTO (\\$\\w+) (\\d+/\\d+) (\\d+(\\.\\d+)?C)");
        Matcher matcher = pattern.matcher(tweet);

        // Extract the data
        if (matcher.find()) {
            String ticker = matcher.group(1).replace("$","").toUpperCase(); // $SPY
            String date = matcher.group(2);   // 8/16
            String strike = matcher.group(3); // 552C
            char callPut = strike.charAt(strike.length() -1);

            //Converting Strike into Option 8 digit format
            String parsingStrike = strike.substring(0,strike.length() -1);
            Double d = Double.parseDouble(parsingStrike) *1000;
            String formattedStrike = String.format("%08d", Integer.parseInt(Double.toString(d).substring(0,Double.toString(d).length() -2)));

            //storing date in different variables
            String [] dateApart = date.split("/");
            String year = "24";
            String month = String.format("%02d" , Integer.parseInt(dateApart[0]) );
            String day = String.format("%02d" , Integer.parseInt(dateApart[1]) );

            System.out.println("Ticker: " + ticker);
            System.out.println("Date: " + date);
            System.out.println("Strike: " + formattedStrike);


            String optionContract = ticker+ year +month+day+callPut+ formattedStrike;

            System.out.println("Converted Option Contract =  " +optionContract);
            //Creating an option Contract
        }

    }

}

