package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.team.smart.R;

public class MainActivity extends HeaderActivity {

    public Button btnFood,btnParking,bntRental,bntReservation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //공통헤더 쓸때 필요한 부분=================
        setView(R.layout.activity_main);//자식창
        setMenuDisplay(true);
        setSearchDisplay(false);
        setTitle("Smart");
        //=======================================

        findid();         // 아이디 일괄 셋팅
        configuListner(); // 클릭 리스너 일괄 세팅
    }

    //아이디 일괄 셋팅
    @Override
    void findid() {
        btnFood = (Button) findViewById(R.id.btnFood);
        btnParking = findViewById(R.id.btnParking);
        bntRental = findViewById(R.id.bntRental);
        bntReservation = findViewById(R.id.bntReservation);
    }

    //클릭 리스너 일괄 세팅
    @Override
    protected void configuListner() {

        //음식점 페이지 이동
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "음식점 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), FoodListActivity.class); //FoodListActivity로 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "주차장 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ParkingMainPageActivity.class); //ParkingMainActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        bntRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "사무실 임대 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),RentalActivity.class); // RentalActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        bntReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "회의실 예약 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),RentalActivity.class); // ReservationActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });


    }

    @Override
    void init() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == reqCode) {

        }
    }
}
