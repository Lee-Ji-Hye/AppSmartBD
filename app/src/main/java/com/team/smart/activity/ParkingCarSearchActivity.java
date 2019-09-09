package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.team.smart.R;

public class ParkingCarSearchActivity extends AppCompatActivity {

    //차번호 찾기 페이지
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_car_search);

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //결제하러가기 페이지
        Button paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingPaymentActivity.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });

        //차번호 검색 버튼 클릭시 이동
        Button numSearchBtn =findViewById(R.id.numSearchBtn);

        numSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingCarSearchDetail.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });

    }
}
