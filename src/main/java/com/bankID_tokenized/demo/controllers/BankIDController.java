package com.bankID_tokenized.demo.controllers;

import com.bankID_tokenized.demo.wrappers.*;
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
        BankIDRequest bankIDRequest = new BankIDRequest("80.217.149.82", authRequest.getPersonalNumber());
        return bankIDRestTemplate.authenticate(bankIDRequest);
    }

    @RequestMapping (value = "/collect",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectResponse> bankIDResultCollect (@RequestParam ("address") String address, @RequestBody CollectRequest collectRequest) {
        System.err.println(collectRequest.getOrderRef() + "Första callet");
        System.err.println(address + "första addressen");
        return bankIDRestTemplate.collect(collectRequest, address);
    }
}
