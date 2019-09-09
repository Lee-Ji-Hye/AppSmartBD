package com.team.smart.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

public class FoodOrderCompleteActivity extends AppCompatActivity {


    String paramFocode;
    TextView tvOrderDetailBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food_order_complete);

        Intent intent = getIntent(); /*데이터 수신*/
        paramFocode = intent.getExtras().getString("f_ocode"); /*String형*/

        tvOrderDetailBtn = findViewById(R.id.tv_order_detail_btn);

        configuListner();//클릭이벤트 함수
    }

    protected void configuListner() {
        //주문내역 페이지 이동 버튼
        tvOrderDetailBtn.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "ssss", Toast.LENGTH_SHORT).show();
            Intent myOrderintent = new Intent(FoodOrderCompleteActivity.this, FoodOrderDetailActivity.class);
            myOrderintent.putExtra("f_ocode", paramFocode);
            this.startActivity(myOrderintent);
        });

    }
}
