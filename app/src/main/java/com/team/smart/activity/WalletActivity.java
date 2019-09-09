package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

public class WalletActivity extends AppCompatActivity {

    TextView btn_register_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        btn_register_1 = findViewById(R.id.btn_register_1);

        btn_register_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "지갑 생성하기 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), WalletCreateActivity.class); //WalletCreateActivity로 이동할 준비
                startActivity(intent);
            }
        });
    }
}
