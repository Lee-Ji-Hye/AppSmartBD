package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.team.smart.R;

public class ParkingCarSearchActivity extends AppCompatActivity {
    EditText carNumEdit;
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

        //주차권확인 페이지
        Button paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingPaymentActivity.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });
        carNumEdit = findViewById(R.id.carNumEdit);
        //차번호 검색 버튼 클릭시 이동
        Button numSearchBtn =findViewById(R.id.numSearchBtn);

        numSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carNumEdit.getTextSize()<5){
                    Toast.makeText(getApplicationContext(),"차번호 4자리를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ParkingCarSearchDetail.class); //ParkingMainActivity 이동
                    String carnum = carNumEdit.getText().toString();
                    intent.putExtra("carnum",carnum);
                    startActivity(intent);
                }
            }
        });

    }
}
