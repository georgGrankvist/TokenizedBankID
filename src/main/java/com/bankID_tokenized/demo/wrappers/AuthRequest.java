package com.bankID_tokenized.demo.wrappers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AuthRequest {

        private String endUserIp;
        private String personalNumber;

}
