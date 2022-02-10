package com.bankID_tokenized.demo.wrappers;

import lombok.Data;

import java.util.UUID;

@Data
public class CollectRequest {
    private UUID orderRef;
}
