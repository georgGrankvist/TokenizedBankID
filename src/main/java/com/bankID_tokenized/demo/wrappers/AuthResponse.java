package com.bankID_tokenized.demo.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    private String orderRef;
    private String autoStartToken;
}
