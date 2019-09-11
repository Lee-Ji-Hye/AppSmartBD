package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderChkActivity extends AppCompatActivity {

    private TextView tvOrderCode, tvUsername, btnOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_chk);

        findId();
        configuListner();
    }

    protected void findId() {
        tvUsername  = findViewById(R.id.tv_username);
        tvOrderCode = findViewById(R.id.tv_orderCode);
        btnOrder = findViewById(R.id.btnOrder);

    }

    private APIInterface apiInterface;
    protected void configuListner() {

        btnOrder.setOnClickListener(view -> {
            if(tvUsername.getText() == null || tvUsername.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "주문자 성명을 입력하세요", Toast.LENGTH_SHORT).show();
                tvUsername.requestFocus();
                return;
            } else if(tvOrderCode.getText() == null || tvOrderCode.getText().equals("")) {
                Toast.makeText(getApplicationContext(), "주문번호를 입력하세요", Toast.LENGTH_SHORT).show();
                tvOrderCode.requestFocus();
                return;
            }


            //스토어 통신


            if(apiInterface == null) {
                apiInterface = APIClient.getClient().create(APIInterface.class);
            }

            //파라미터 세팅
            String username = tvUsername.getText().toString();
            String f_ocode = tvOrderCode.getText().toString();

            //통신
            Call<HashMap> call = apiInterface.getOrderDetailChk(username, f_ocode); //주문내역 있나 없나만 조회함.
            call.enqueue(new Callback<HashMap>() {
                @Override
                public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                    if(response.code()==200) {
                        HashMap data = response.body();

                        Gson gson3 = new Gson();
                        String json3 = gson3.toJson(data);
                        Log.d("여기에도 오는지~~~>", json3);

                        int orderCnt = Integer.parseInt(data.get("cnt").toString());

                        //정보세팅
                        if(orderCnt > 0) {
                            Intent intent = new Intent(getApplicationContext(), FoodOrderDetailActivity.class);
                            intent.putExtra("f_ocode",f_ocode);        /*메뉴코드*/
                            intent.putExtra("f_name", username); /*업체코드*/
                            startActivity(intent);

                        } else {
                            //예약정보 없다고 창을 띄우기
                            Toast.makeText(getApplicationContext(), "주문정보가 없습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            tvUsername.setText("");
                            tvOrderCode.setText("");
                            tvUsername.requestFocus();
                        }
                    }
                }

                @Override
                public void onFailure(Call<HashMap> call, Throwable t) {
                    Log.d("스토어 통신 fail~~~~~~~~~...", "실패..");
                    call.cancel();
                }
            });

        });
    }
}
