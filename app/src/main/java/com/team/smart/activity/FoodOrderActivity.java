package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.vo.FoodCartVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodOrderActivity extends AppCompatActivity {
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private ArrayList<FoodCartVO> foodVo;
    private int salePrice = 0; //할인액

    private ArrayAdapter myAdapter;
    private Spinner spinArriveTime;

    //findid
    TextView tvCompOrg,tvAddress,tvFcnt,editMessage,tvAmount,tvSalePrice,tvTotPayPrice,tvOpen,tvOpenWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_form);

        //Intent 받기
        Intent intent = getIntent();
        foodVo = (ArrayList<FoodCartVO>) intent.getSerializableExtra("cartMenuList");
        calcPrice();
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(foodVo);
        Log.d("자 주문 메뉴~~~>", json3);

        //여기서 업체정보 불러오는 통신 한번 하고~

        findid();

        //스피너 세팅
        spinArriveTime = (Spinner)findViewById(R.id.spinArrivedTime);
        String[] strList =  getResources().getStringArray(R.array.spinner_counts);
        myAdapter = new ArrayAdapter(FoodOrderActivity.this,android.R.layout.simple_spinner_dropdown_item,strList);
        spinArriveTime.setAdapter(myAdapter);
        spinArriveTime.setSelection(0);

    }

    private void findid() {
        tvCompOrg = findViewById(R.id.tv_comp_org);           //업체명
        tvAddress = findViewById(R.id.tv_address);            //업체주소
        tvFcnt = findViewById(R.id.tv_fCnt);                  //인원수
        editMessage = findViewById(R.id.edit_message);       //요청사항
        tvOpen = findViewById(R.id.tv_open);                  //운영시간(평일)
        tvOpenWeek = findViewById(R.id.tv_open_week);        //운영시간(주말)
        tvAmount = findViewById(R.id.tv_amount);             //주문금액
        tvSalePrice = findViewById(R.id.tv_sale_price);     //할인금액
        tvTotPayPrice = findViewById(R.id.tv_tot_pay_price);//최종 결제 금액

        /*값 세팅 */
        tvCompOrg.setText(foodVo.get(0).getComp_org());
        tvOpen.setText("24시간");
        tvOpenWeek.setText("18시간");
    }

    /* 금액 계산 */
    private void calcPrice() {
        int sum = 0;
        try {
            if (foodVo != null) {
                for (int i = 0; i < foodVo.size(); i++) {
                    sum += foodVo.get(i).getF_cnt() * Integer.parseInt(foodVo.get(i).getF_price());
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "금액설정오류", Toast.LENGTH_LONG).show();
        }

        //할인액
        if(salePrice > 0) {
            sum = sum - salePrice;
            tvSalePrice.setText(salePrice);
        } else {
            tvSalePrice.setText("0원");
        }
        tvAmount.setText(numberComma.format(sum)+"원");//주문 금액
        tvTotPayPrice.setText(numberComma.format(sum)+"원"); //최종 결제금액

    }
}
