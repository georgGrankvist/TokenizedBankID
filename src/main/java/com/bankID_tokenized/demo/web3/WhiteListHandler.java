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
import org.web3j.tx.gas.DefaultGasProvider;
import wrapper.TokenizedBankID;

@Getter
@Setter
@Component
@AllArgsConstructor
public class WhiteListHandler {

    private final Web3j web3j;
    private final Credentials walletCredentials;

    public void outputHash (String hashedID) {
        System.out.println(hashedID);
        web3Connect(hashedID);
    }

    public WhiteListHandler () {
        web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/ee8a83a7efef41bb88178cf740c69511"));
        walletCredentials = Credentials.create("b9ab14464545ccfb3f06ae29dc3fd399c8e48c05b6f6b7a31785cb046a899f54");
    }

    public void web3Connect (String hashedID) {
        System.out.println("Owner address = " + walletCredentials.getAddress());
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
            EthGasPrice gasPrice =  web3j.ethGasPrice().send();

            System.out.println("Client version: " + web3ClientVersion.getWeb3ClientVersion());
            System.out.println("Block number: " + blockNumber.getBlockNumber());
            System.out.println("Gas price: " + gasPrice.getGasPrice());

            TokenizedBankID whiteListContract = TokenizedBankID.load("0xDd102b1Ceedab170e155591a5a03042ADf6FB925",web3j,walletCredentials,new DefaultGasProvider());
            TransactionReceipt transactionReceipt = whiteListContract.addUser("0xd202bED19328217Ca7189F7065e2DA34222a5E3f").send();
            System.out.println(transactionReceipt.getTransactionHash());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
