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
import org.web3j.tx.response.Callback;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.QueuingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import wrapper.TokenizedBankID;

import java.math.BigInteger;
import java.net.URI;
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
        web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/ee8a83a7efef41bb88178cf740c69511"));
        walletCredentials = Credentials.create("fe559c7814450d6f6c9a635c0d88afa70244598342be87a249f0c9fa3bfc83c7");
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

            TransactionReceiptProcessor transactionReceiptProcessor = new PollingTransactionReceiptProcessor(web3j,3000,40);
            TransactionManager transactionManager = new FastRawTransactionManager(web3j,walletCredentials,transactionReceiptProcessor);
            TokenizedBankID whiteListContract = TokenizedBankID.load("0xd655da810B2a1304eBB6707f9396C5c2138819c4",web3j, transactionManager,new DefaultGasProvider());
            CompletableFuture<TransactionReceipt> transactionReceipt = whiteListContract.addUser(address).sendAsync();

            transactionReceiptProcessor.waitForTransactionReceipt(transactionReceipt.get().getTransactionHash());

            if (transactionReceipt.isDone()) {
                System.out.println("Whitelisting successful, TX HASH = " + transactionReceipt.get().getTransactionHash());
                System.out.println("Address " + address + " is whitelisted.");
                System.out.println("TX on block explorer: "  + URI.create("https://goerli.etherscan.io/tx/") + transactionReceipt.get().getTransactionHash());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
