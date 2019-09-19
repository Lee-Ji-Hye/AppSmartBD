package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.team.smart.R;
import com.team.smart.blockchain.Wallet;
import com.team.smart.vo.WalletVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WalletCreateActivity extends AppCompatActivity {

    private static final String TAG = "WalletCreateActivity";

    //findViewById를 매번 호출할 필요 없이 한번에 바인딩
    @ViewInject(R.id.etxt_pn)
    private TextView mEtxtPn;
    @ViewInject(R.id.etxt_pwd_3)
    private TextView mEtxtPwd_3;
    @ViewInject(R.id.etxt_pwd_4)
    private TextView mEtxtPwd_4;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressBar pgb=findViewById(R.id.progressbar);
            switch (msg.what) {
                case 0:
                    Toast.makeText(WalletCreateActivity.this, "지갑 생성하기 성공.", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);

                    Intent intent = new Intent(WalletCreateActivity.this, WalletStartActivity.class);
                    startActivity(intent);

                    break;
                case 1:
                    Toast.makeText(WalletCreateActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
                case 2:
                    Toast.makeText(WalletCreateActivity.this, "지갑 이름이 이미 존재합니다", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
                default:
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_create);
        ViewUtils.inject(this);
    }

    public void progressBar_Reg(View view) {
        ProgressBar pgb=findViewById(R.id.progressbar);
        pgb.setVisibility(ProgressBar.VISIBLE);
        createWallet(view);
    }

    public void createWallet(View view) {
        List<String> names = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name=mEtxtPn.getText().toString();
                    String password1=mEtxtPwd_3.getText().toString();
                    String password2=mEtxtPwd_4.getText().toString();
                    Message msg = mHandler.obtainMessage();
                    if(!password1.equals(password2)){
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        return;
                    }
                    Wallet wallet= Wallet.getInstance();
                    File f = new File(getFilesDir()+"/keystore");
                    if (!f.exists()){
                        f.mkdirs();
                    }
                    String filepath = getFilesDir()+"/keystore";

                    try {
                        List<WalletVO>lists=wallet.getLists(filepath);
                        for (WalletVO walletBean:lists){
                            String temp=walletBean.getName();
                            String tname=walletBean.getAddress();
                            String tpassword=walletBean.getPath();
                            if(temp.equals(name)){
                                msg.what = 2;
                                mHandler.sendMessage(msg);
                                return;
                            }

                            System.out.println("확인용"       +   temp + "                " +tname + "                       " + tpassword);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    wallet.createWallet(name,password1,filepath);
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
