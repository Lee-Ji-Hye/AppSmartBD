package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;
import com.team.smart.blockchain.Web3jAPI;

import java.math.BigInteger;
import java.util.List;

public class RoomContractDetailActivity extends AppCompatActivity {
    private String b_area1,b_area2,b_address,b_year,b_landarea,b_buildarea,b_buildscale
            ,r_code,rt_hash,rt_email,rt_mobile,r_price,r_deposit,r_premium
            ,name,email,hp;
    private int r_blockCode,r_total;

    private static Web3jAPI web3jAPI;
    TextView tv_b_address,tv_b_year,tv_b_landarea,tv_b_buildscale,tv_b_buildarea
            ,tv_r_price2,tv_r_premium,tv_r_deposit3,tv_rt_total
            ,walletAddress
            ,et_rt_name, et_rt_businessNum,et_rt_mobile,et_rt_email
            ,tv_name,tv_hp,tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract_detail);

        findid();
        getContract();
    }

    private void findid() {
        Intent intent = getIntent(); //데이터 수신
        b_area1 = intent.getExtras().getString("b_area1");
        b_area2 = intent.getExtras().getString("b_area2");
        b_address = intent.getExtras().getString("b_address");
        b_year = intent.getExtras().getString("b_year");
        b_landarea = intent.getExtras().getString("b_landarea");
        b_buildarea = intent.getExtras().getString("b_buildarea");
        b_buildscale = intent.getExtras().getString("b_buildscale");

        r_code = intent.getExtras().getString("r_code");
        rt_hash = intent.getExtras().getString("rt_hash");
        r_blockCode = intent.getExtras().getInt("r_blockCode");
        r_price = intent.getExtras().getString("r_price");
        r_deposit = intent.getExtras().getString("r_deposit");
        r_premium = intent.getExtras().getString("r_premium");

        rt_email = intent.getExtras().getString("rt_email");
        rt_mobile = intent.getExtras().getString("rt_mobile");

        name = intent.getExtras().getString("name");
        email = intent.getExtras().getString("email");
        hp = intent.getExtras().getString("hp");

        tv_b_address = findViewById(R.id.tv_b_address);
        tv_b_year = findViewById(R.id.tv_b_year);
        tv_b_landarea = findViewById(R.id.tv_b_landarea);
        tv_b_buildscale = findViewById(R.id.tv_b_buildscale);
        tv_b_buildarea = findViewById(R.id.tv_b_buildarea);

        tv_r_price2 = findViewById(R.id.tv_r_price2);
        tv_r_deposit3 = findViewById(R.id.tv_r_deposit3);
        tv_r_premium = findViewById(R.id.tv_r_premium);
        tv_rt_total = findViewById(R.id.tv_rt_total);

        walletAddress = findViewById(R.id.walletAddress);

        et_rt_name = findViewById(R.id.rt_name);
        et_rt_businessNum = findViewById(R.id.rt_businessNum);
        et_rt_mobile = findViewById(R.id.rt_mobile);
        et_rt_email = findViewById(R.id.rt_email);

        tv_name = findViewById(R.id.tv_name);
        tv_hp = findViewById(R.id.tv_hp);
        tv_email = findViewById(R.id.tv_email);

        tv_b_address.setText(b_area1+" "+b_area2+" "+b_address);
        tv_b_year.setText(b_year);
        tv_b_landarea.setText(b_landarea);
        tv_b_buildscale.setText(b_buildscale);
        tv_b_buildarea.setText(b_buildarea);

        tv_r_price2.setText(r_price);
        tv_r_deposit3.setText(r_deposit);
        tv_r_premium.setText(r_premium);
        r_total = Integer.parseInt(r_price) + Integer.parseInt(r_deposit);
        tv_rt_total.setText(String.valueOf(r_total));

        et_rt_mobile.setText(rt_mobile);
        et_rt_email.setText(rt_email);

        tv_name.setText(name);
        tv_hp.setText(hp);
        tv_email.setText(email);
    }

    //Bytes32toString
    public static String hexToASCII(String hexValue){
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue.length(); i += 2)
        {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public void getContract() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Web3jAPI web3jAPI = Web3jAPI.getInstance();

                        List<Object> details = web3jAPI.getBuyerInfo(BigInteger.valueOf(r_blockCode));

                        if (details.get(0).toString() != null) {
                            System.out.println(details.get(0).toString());
                            System.out.println(details.get(1).toString());
                            System.out.println(details.get(2).toString());
                            walletAddress.setText(details.get(0).toString());
                            et_rt_name.setText(hexToASCII(details.get(1).toString()));
                            et_rt_businessNum.setText(hexToASCII(details.get(2).toString()));
                        }
                    }
                });
            }
        }).start();
    }
}
