package com.team.smart.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.team.smart.R;
import com.team.smart.blockchain.Web3jAPI;

public class MyWalletActivity extends AppCompatActivity {

    private static final String TAG = "MyWalletActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ViewUtils.inject(this);
    }

    // 잔액 조회
    @OnClick(R.id.txt_my_currency)
    public void myCurrency(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Web3jAPI Web3jAPI = com.team.smart.blockchain.Web3jAPI.getInstance().getInstance();
        builder.setTitle("잔액 조회").setMessage(Web3jAPI.getETHBalance().toString()+" ETH\n1ETH = 10^18 WEI").setPositiveButton("확인", null).show();

    }

    // 개인 키
    @OnClick(R.id.txt_my_private_key)
    public void toMyPrivateKey(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Web3jAPI Web3jAPI = com.team.smart.blockchain.Web3jAPI.getInstance().getInstance();
        builder.setTitle("개인 키").setMessage(Web3jAPI.exportPrivateKey()).setNeutralButton("복사", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "복사 성공", Toast.LENGTH_LONG).show();
                ClipboardManager clipboardManager =
                        (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, Web3jAPI.exportPrivateKey()));
                if (clipboardManager.hasPrimaryClip()){
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
            }
        }).setPositiveButton("확인", null).show();
    }

    // 주소
    @OnClick(R.id.txt_my_address)
    public void toMyAddress(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Web3jAPI Web3jAPI = com.team.smart.blockchain.Web3jAPI.getInstance().getInstance();
        builder.setTitle("주소").setMessage(Web3jAPI.getAddress()).setNeutralButton("복사", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "복사 성공", Toast.LENGTH_LONG).show();
                ClipboardManager clipboardManager =
                        (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, Web3jAPI.getAddress()));
                if (clipboardManager.hasPrimaryClip()){
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
            }
        }).setPositiveButton("확인", null).show();
    }

    // 송금
    @OnClick(R.id.txt_my_transfer)
    public void myTransfer(View view){
        Intent intent = new Intent(this, WalletTransferActivity.class);
        startActivity(intent);
    }
}
