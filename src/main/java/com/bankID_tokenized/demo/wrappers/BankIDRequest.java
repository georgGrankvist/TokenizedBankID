package com.bankID_tokenized.demo.wrappers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class BankIDRequest {

    private String endUserIp;
    private String personalNumber;

}
