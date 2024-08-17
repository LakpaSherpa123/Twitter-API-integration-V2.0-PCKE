package com.example.TwitterHTTPalert.service;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAFbZuwEAAAAAvZgjPE3wi2auhhaFnC%2F%2FwRW%2FE%2Fs%3D0fTRXErL8ZwLF6vaolR9TGxRsnXOV123Wf9NGVzEN9QCNABBTA";
    private static final String CONSUMER_KEY ="9XDZLLVnqmEcSDI1TTYRgDNvR";
    private static final String CONSUMER_SECRET= "A9hr3i8q3Lij61qJrxBJ7NbfYnPfYa7cG3ABys0GluubTHrzl7";

    private static final String CLIENT_ID = "X0tta2FTTXdyMkRMeGw4MXVSTkk6MTpjaQ";
    private static final String CLIENT_SECRET = "9wRaOjV6JrrProNq_m_Y27enB5KXYOLmuObdZq_MUXS387OJvF";
    private static final String REDIRECT_URI = "http://127.0.0.1:8080/api/callback";
    private final String scope = "users.read tweet.write tweet.read";
    private String code_verifier;
    private String code_challenge;
    private String access_token;

    public void setCode_verifier(String code_verifier) {
        this.code_verifier = code_verifier;
    }

    public void setCode_challenge(String code_challenge) {
        this.code_challenge = code_challenge;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getCode_verifier() {
        return code_verifier;
    }

    public String getCode_challenge() {
        return code_challenge;
    }

    public String getAccess_token() {
        return access_token;
    }

    public static String generateCodeVerifier() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    public String percentEncode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20")
                    .replace("%21", "!")
                    .replace("%27", "'")
                    .replace("%2B", "%20")
                    .replace("%29", ")")
                    .replace("%7E", "~");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateCodeChallenge(String codeVerifier){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        }catch(Exception e){
            return e.toString();
        }
    }


     public String authenticateMyAccount(){

            try {


                setCode_verifier(generateCodeVerifier());
                setCode_challenge(generateCodeChallenge(getCode_verifier()));
                final String STATE = UUID.randomUUID().toString(); // Random state value

                System.out.println("Code Verifier (execute)= " + getCode_verifier());
                System.out.println("Code Challenge (execute)= " + getCode_challenge());

                final String authorizationURL = "https://twitter.com/i/oauth2/authorize" +
                        "?response_type=code" +
                        "&client_id=" + percentEncode(CLIENT_ID) +
                        "&redirect_uri=" + REDIRECT_URI +
                        "&scope=" + percentEncode(scope) +
                        "&state=" + percentEncode(STATE) +
                        "&code_challenge=" + percentEncode(getCode_challenge()) +
                        "&code_challenge_method=plain";

                System.out.println(authorizationURL);

                return authorizationURL;
            }catch(Exception e){
                return e.toString();
            }

    }

    public String getToken(String authCode, String challenge, String verifier) {

        try {
            // Encode Client ID and Client Secret
            String credentials = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            //Creating a new HTTP Client
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.twitter.com/2/oauth2/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + encodedCredentials)
                    // .headers("code", authorizationCode)
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "client_id=" + CLIENT_ID +
                                    "&code=" + authCode +
                                    "&redirect_uri=" + REDIRECT_URI +
                                    "&code_verifier=" + challenge +
                                    "&grant_type=authorization_code"))
                    .build();
            System.out.println("Code Verifier(call back) =  " + verifier);
            System.out.println("Code challenge(call back) =  " + challenge);

            //sending the request from the client with client.send
            //Storing in the HTTP response as a list of string as it returns its status code, and all the requested headers and body
            //HttpResponse.BodyHandlers.ofString() :This specifies to handle the body as String.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonTokenData = new JSONObject((response.body()));

            this.access_token = jsonTokenData.getString("access_token");
            System.out.println(response.body());
            return this.access_token;

        } catch (Exception error){
            return ("Error" + error);
        }
    }
}
