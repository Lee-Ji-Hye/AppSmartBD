package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.smart.R;
import com.team.smart.adapter.MyCustomPagerAdapter;

public class ParkingPaymentActivity extends AppCompatActivity {
    private TextView paymentBtn;
    String p_code; //주차장 코드
    ImageView carNumImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);

        //p_code 값 받아오기
        Intent intent = getIntent();
        p_code = intent.getExtras().getString("p_code");


        carNumImg=findViewById(R.id.carNumImg);
        paymentBtn = findViewById(R.id.paymentBtn);

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //이미지뷰에 이미지 넣기
        carNumImg.setImageResource(R.drawable.carnum);

        //결제버튼 클릭시
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "결제 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ParkingMainPageActivity.class); //ParkingMainActivity 이동할 준비
                startActivity(intent);
            }
        });



    }
}
