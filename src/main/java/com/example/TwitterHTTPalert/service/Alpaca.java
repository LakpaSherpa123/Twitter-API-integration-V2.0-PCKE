package com.example.TwitterHTTPalert.service;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Alpaca {

    private String API_KEY_ID = "PKM0OE5UQMFCP7R2XHJD";
    private String API_SECRET_KEY = "UovxFgxk3CgO8epPVz60LglLEfF4pkzPSbZeaO3t";
    private String PAPER_TRADE_URI = "https://paper-api.alpaca.markets";

    public String getAPI_KEY_ID() {
        return API_KEY_ID;
    }

    public String getAPI_SECRET_KEY() {
        return API_SECRET_KEY;
    }

    public void getAPI_KEY_ID(String api_Key) {
        this.API_KEY_ID = api_Key;
    }

    public void getAPI_SECRET_KEY(String api_Secret) {
        this.API_SECRET_KEY = api_Secret;
    }


    public String placeOrder(String symbol, int qty, String side, String type, String timeInForce) throws Exception {
        String endpoint = PAPER_TRADE_URI + "/v2/orders";

        JSONObject jsonInput = new JSONObject();
        jsonInput.put("symbol", symbol);
        jsonInput.put("qty", qty);
        jsonInput.put("side", side);
        jsonInput.put("type", type);
        jsonInput.put("time_in_force", timeInForce);

        System.out.println("Until here is fine  " + jsonInput );
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(endpoint))
                .header("APCA-API-KEY-ID", API_KEY_ID)
                .header("APCA-API-SECRET-KEY", API_SECRET_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput.toString()))
                .build();


        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }
    }
}
