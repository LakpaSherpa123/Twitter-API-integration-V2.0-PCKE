package com.example.TwitterHTTPalert.controller;
import com.example.TwitterHTTPalert.service.AlpacaService;
import com.example.TwitterHTTPalert.service.Alpaca;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend's URL
@RequestMapping("/api")
public class AlpacaController {


    @GetMapping("/alpacaTest")
    public ResponseEntity<String> alpacaApi(){
        AlpacaService auth = new AlpacaService();
        String authUrl  = auth.authenticateAlpaca();
        MyController.openWebPage(authUrl);
        return ResponseEntity.ok("This is Alpaca api");
    }
    @GetMapping("/callbackAlpaca")
    public ResponseEntity<String> callBackAlpaca(@RequestParam(value = "code") String response){

        try {
            System.out.println("Inside the callback");
            AlpacaService http = new AlpacaService();
            String token = http.getTokenAlpaca(response);
            System.out.println("Token Generated = " + token);
            return ResponseEntity.ok("Token Generated " + response);
        }catch(Exception e){
            return ResponseEntity.ok(e.toString());
        }

    }

    @PostMapping("/placeAlpacaOrder")
    public ResponseEntity<String> placeAlpacaOrder(@RequestBody String passedOrder) throws Exception {

        JSONObject json = new JSONObject(passedOrder);

        Alpaca order = new Alpaca();
        String placingOrder = order.placeOrder(json.getString("symbol").toUpperCase(), json.getInt("qty") , json.getString("side"), json.getString("market"), json.getString("timeInForce"));

        return ResponseEntity.ok("Posted");

    }

}
