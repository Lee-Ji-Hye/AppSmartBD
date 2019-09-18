package com.team.smart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.team.smart.R;
import com.team.smart.blockchain.Web3jAPI;

import java.math.BigDecimal;

import dmax.dialog.SpotsDialog;

public class WalletTransferActivity extends AppCompatActivity {

    @ViewInject(R.id.etxt_address)
    private EditText address;
    @ViewInject(R.id.etxt_amount)
    private EditText amount;

    private SpotsDialog dialog;
    private final int TRANSFER_MSG = 13164;
    //private final int INIT_MSG = 14791;

    private Double user_amount = 0.0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /*case INIT_MSG:

                    break;*/
                case TRANSFER_MSG:
                    if (dialog != null) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                    Toast.makeText(WalletTransferActivity.this,"성공적인 이체",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transfer);
        ViewUtils.inject(this);
        //initToolBar();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Web3jAPI fishAPI = Web3jAPI.getInstance();
                BigDecimal balance = fishAPI.getETHBalance();
                user_amount = Double.parseDouble(balance.toString());
            }
        }).start();

    }


    /*private void initToolBar(){


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransferActivity.this.finish();
            }
        });

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    @OnClick(R.id.btn_transfer)
    public void transfer(View view){
        final String add = address.getText().toString().trim();
        final Double eth_amount = Double.parseDouble(amount.getText().toString().trim());

        if (eth_amount >= user_amount){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("이체").setMessage("잔액 부족！\n당신의 잔액:\t"+user_amount+" ETH\n당신의 잔액:"+eth_amount+" ETH").setPositiveButton("결정",null).show();
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("이체").setMessage("주기로 결정\n" + add + "\n변환" + eth_amount + " ETH?").setNeutralButton("취소", null).setPositiveButton("결정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog = new SpotsDialog(WalletTransferActivity.this, "loading...");
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Web3jAPI fishAPI = Web3jAPI.getInstance();
                            fishAPI.sendETH(add,eth_amount);

                            Message msg = handler.obtainMessage();
                            msg.what = TRANSFER_MSG;
                            msg.obj = "ok";
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }).show();
        }

    }
}
