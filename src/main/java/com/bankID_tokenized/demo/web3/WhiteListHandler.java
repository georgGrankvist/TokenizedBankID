package com.bankID_tokenized.demo.web3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
public class WhiteListHandler {

    public void outputHash (String hashedID) {
        System.out.println(hashedID);
    }
}
