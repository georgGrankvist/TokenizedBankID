package com.bankID_tokenized.demo.web3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.springframework.stereotype.Component;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import wrapper.TokenizedBankID;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@Component
@AllArgsConstructor
public class WhiteListHandler {

    private final Web3j web3j;
    private final Credentials walletCredentials;

    public void outputHash (String address) {
        System.err.println("whitelisthandler");
        web3Connect(address);
    }

    public WhiteListHandler () {
        web3j = Web3j.build(new HttpService("https://polygon-mumbai.infura.io/v3/ee8a83a7efef41bb88178cf740c69511"));
        walletCredentials = Credentials.create("");
    }

    public void web3Connect (String address) {
        System.out.println("Owner address =  " + walletCredentials.getAddress());
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            EthGasPrice gasPrice =  web3j.ethGasPrice().send();

            System.out.println("Client version: " + web3ClientVersion.getWeb3ClientVersion());
            System.out.println("Block number: " + blockNumber.getBlockNumber());
            System.out.println("Gas price: " + gasPrice.getGasPrice());
            System.out.println("Address to be Whitelisted: " + address);

            TransactionManager transactionManager = new FastRawTransactionManager(web3j,walletCredentials, 80001);


            TokenizedBankID whiteListContract = TokenizedBankID.load("0xb493914c7b574efdf53dee89a824b7347c0d8c09",web3j, transactionManager,new DefaultGasProvider());
            TransactionReceipt transactionReceipt = whiteListContract.addUser( address ).send();

                System.out.println(transactionReceipt.getTransactionHash());
                System.out.println("Address: " + address + " is Whitelisted");
                System.out.println(transactionReceipt.getStatus());
                System.out.println(transactionReceipt.getGasUsed());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
