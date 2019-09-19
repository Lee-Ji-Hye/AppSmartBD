package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;
import com.team.smart.blockchain.Wallet;
import com.team.smart.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class parkingmypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkingmypage);

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //차량 정보 버튼 클릭
        TextView carInfoBtn = findViewById(R.id.carInfoBtn);
        carInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingUserCarInfo.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });


        //지갑 정보 버튼 클릭
        TextView btnWalletInfo = findViewById(R.id.btnWalletInfo);
        btnWalletInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> names = new ArrayList<>();
                Wallet wallet = Wallet.getInstance();
                String filepath = getFilesDir()+"/keystore";
                try {
                    wallet.getLists(filepath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                names = wallet.getNames();

                if (names.size() == 0){
                    Intent intent = new Intent(getApplicationContext(), WalletCreateActivity.class); //지갑 없음 이동
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), WalletStartActivity.class); //지갑있음 이동
                    startActivity(intent);
                }

            }
        });

        TextView btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtil.removeAllPreferences(parkingmypageActivity.this);//로그인정보 제거

                Intent intent =new Intent(parkingmypageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //내 정보 수정
        TextView tvEditMyinfo = findViewById(R.id.tv_edit_myinfo);
        tvEditMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignEditActivity.class); //내정보 수정하기 페이지 이동
                startActivity(intent);
            }
        });

        //비밀번호 변경
        TextView tvChangePw = findViewById(R.id.tv_change_pw);
        tvChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignEditPwdActivity.class); //내정보 수정하기 페이지 이동
                startActivity(intent);
            }
        });

        //회원탈퇴
        TextView outBtn = findViewById(R.id.outBtn);
        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignWithdrawActivity.class); // 회원 탈퇴
                startActivity(intent);
            }
        });
    }
}