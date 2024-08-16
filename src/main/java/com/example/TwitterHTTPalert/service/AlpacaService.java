package com.example.TwitterHTTPalert.service;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class AlpacaService {

    private final String CLIENT_ID =  "44b17b4f06db4fc079fe434c76876427";
    private final String CLIENT_SECRET = "0721f50089fa5bb4c86c5662282396c5fd55ba9c";
    private final String REDIRECT_URI = "http://127.0.0.1:8080/api/callbackAlpaca";
    private final String SCOPE = "account:write trading data";

    public String authenticateAlpaca() {


        try {
            final String STATE = UUID.randomUUID().toString(); // Random state value
            TwitterService methodsFromHttpAuthClass = new TwitterService();

            final String authorizationAlpacaURL = "https://app.alpaca.markets/oauth/authorize" +
                    "?response_type=code" +
                    "&client_id=" +  CLIENT_ID +
                    "&redirect_uri=" + REDIRECT_URI +
                    "&scope=" + methodsFromHttpAuthClass.percentEncode(SCOPE) +
                    "&state=" + methodsFromHttpAuthClass.percentEncode(STATE);


            return authorizationAlpacaURL;


        } catch (Exception error) {
                System.out.println(error);
                return error.toString();
        }

    }

    public String getTokenAlpaca(String authCode) throws URISyntaxException, IOException, InterruptedException {
        // Encode Client ID and Client Secret

        //Creating a new HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.alpaca.markets/oauth/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(
                                "client_id=" + CLIENT_ID +
                                "&client_secret="+CLIENT_SECRET+
                                "&code=" + authCode +
                                "&redirect_uri=" + REDIRECT_URI +
                                "&grant_type=authorization_code"))
                .build();

        //sending the request from the client with client.send
        //Storing in the HTTP response as a list of string as it returns its status code, and all the requested headers and body
        //HttpResponse.BodyHandlers.ofString() :This specifies to handle the body as String.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonTokenData = new JSONObject((response.body()));


        System.out.println("Token Generated = " + jsonTokenData.getString("access_token"));
        System.out.println(response.body());

//        //trades
//        try {
//
//            AlpacaTrades orders = new AlpacaTrades();
//            // Place an order
//            String orderResponse = orders.placeOrder("TSLA", 1, "buy", "market", "gtc");
//            System.out.println("Order Response: " + orderResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return jsonTokenData.getString("access_token");

    }

}
