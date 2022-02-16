package com.bankID_tokenized.demo.web3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.springframework.stereotype.Component;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import wrapper.TokenizedBankID;

@Getter
@Setter
@Component
@AllArgsConstructor
public class WhiteListHandler {

    private final Web3j web3j;
    private final Credentials walletCredentials;

    public void outputHash (String address) {

        web3Connect(address);
    }

    public WhiteListHandler () {
        web3j = Web3j.build(new HttpService("https://polygon-mumbai.infura.io/v3/ee8a83a7efef41bb88178cf740c69511"));
        walletCredentials = Credentials.create("d898765ecdb507340694a7ccf04905b51c950648aff310552453f939ab3a8142");
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

            TransactionManager txManager = new RawTransactionManager(
                    web3j, walletCredentials, 80001);

            TokenizedBankID whiteListContract = TokenizedBankID.load("0x19a916be5C39E0acdd66b83e08CA392977acaB46",web3j,txManager,new DefaultGasProvider());
            TransactionReceipt transactionReceipt = whiteListContract.mintNFT("0xb1502403E97b7B072F4139ab874c101cE743a8A6", "hej").send();
            System.out.println(transactionReceipt.getTransactionHash());
            System.out.println("Address: " + address + "is Whitelisted");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
