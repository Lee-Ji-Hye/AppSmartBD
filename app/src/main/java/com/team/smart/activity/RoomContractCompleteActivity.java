package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

public class RoomContractCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract_complete);
        Button btn_roomMain = findViewById(R.id.btn_roomMain);
        Button bnt_contractMyPage = findViewById(R.id.bnt_contractMyPage);

        btn_roomMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), RoomMainActivity.class); //RoomMainActivity 이동할 준비
                startActivity(intentHome);
            }
        });

        bnt_contractMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMypage = new Intent(getApplicationContext(), RoomContractListActivity.class); //RoomContractListActivity 이동할 준비
                startActivity(intentMypage);
            }
        });
    }
}
