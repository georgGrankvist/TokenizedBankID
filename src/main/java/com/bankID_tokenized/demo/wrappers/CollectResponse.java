package com.bankID_tokenized.demo.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectResponse {

    private String status;
    private String hintCode;
    private CompletionData completionData;
}
