package com.bankID_tokenized.demo.controllers;

import com.bankID_tokenized.demo.wrappers.AuthRequest;
import com.bankID_tokenized.demo.wrappers.AuthResponse;
import com.bankID_tokenized.demo.wrappers.CollectRequest;
import com.bankID_tokenized.demo.wrappers.CollectResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("bankID")
public class BankIDController {

    private final BankIDRestTemplate bankIDRestTemplate;

    public BankIDController (BankIDRestTemplate bankIDRestTemplate) {
        this.bankIDRestTemplate = bankIDRestTemplate;
    }

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity <AuthResponse> bankIDAuth (@RequestBody AuthRequest authRequest) {
        System.out.println("Attempting authentication");
        return bankIDRestTemplate.authenticate(authRequest);
    }

    @RequestMapping (value = "/collect",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectResponse> bankIDResultCollect (@RequestBody CollectRequest collectRequest) {
        return bankIDRestTemplate.collect(collectRequest);
    }
}
