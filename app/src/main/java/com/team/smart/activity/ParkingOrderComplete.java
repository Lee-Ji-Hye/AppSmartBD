package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team.smart.R;

public class ParkingOrderComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_order_complete);
        Button homeBtn = findViewById(R.id.homeBtn);
        Button mypageBtn = findViewById(R.id.mypageBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), ParkingMainPageActivity.class); //ParkingMainActivity 이동할 준비
                startActivity(intentHome);
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMypage = new Intent(getApplicationContext(), ParkingOwnTicket.class); //ParkingMainActivity 이동할 준비
                startActivity(intentMypage);
            }
        });

    }
}
