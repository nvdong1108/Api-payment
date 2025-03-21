package com.dongnv.CorePayment.controller.stripe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ApiStripeController {

    @PostMapping("/stripe/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody String body) {
        try {
            Map<String, Object> result = new HashMap<>();

            result.put("message", "success");
            result.put("code", "200");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
