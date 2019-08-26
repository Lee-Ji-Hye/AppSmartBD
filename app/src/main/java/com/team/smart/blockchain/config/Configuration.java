package com.team.smart.blockchain.config;

import java.math.BigInteger;

public class Configuration {

    // see https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    // http://ethereum.stackexchange.com/questions/1832/cant-send-transaction-exceeds-block-gas-limit-or-intrinsic-gas-too-low
    public static final BigInteger GAS_LIMIT_ETHER_TX = BigInteger.valueOf(21_000);
    public static final BigInteger GAS_LIMIT_GREETER_TX = BigInteger.valueOf(500_000L);
    public static String RPC_URL = "https://ropsten.infura.io/v3/8540293cf7684d2594ff48e7cb44d34f";

    public static String contractAddress="0x7c64d7f1d9e74e7f0a4d9a33a6d147cc235ca749";
    public static String contractOwner = "0x80753a86047d27b51d542ab453c24f5ad1401f79";

    public static String[] rares = new String[]{"","","","","","","",""};

    public static int getIndexOfRares(String str){
        int index = 0;
        for (String rare:rares){
            if (rare.equals(str)){
                return index;
            }
            index ++;
        }
        return index;
    }

    public static String getColorOfRare(String str){
        String color = "";
        if (str.equals("")) {color = "#C4C4C4";}
        else if(str.equals("")) {color = "#32CD32";}
        else if(str.equals("")) {color = "#00B2EE";}
        else if(str.equals("")) {color = "#0000FF";}
        else if(str.equals("")) {color = "#9B30FF";}
        else if(str.equals("")) {color = "#EE6AA7";}
        else if(str.equals("")) {color = "#EEB422";}
        else if(str.equals("")) {color = "#EE0000";}

        return color;
    }
}
