package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.adapter.FoodCartAdapter;
import com.team.smart.adapter.FoodMenuAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodOrderVO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderDetailActivity extends AppCompatActivity {
    private APIInterface apiInterface;// 통신 인터페이스

    RecyclerView recyclerView;
    FoodMenuAdapter foodMenuAdapter;

    String paramFocode;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_detail);

        Intent intent = getIntent(); /*데이터 수신*/
        paramFocode = intent.getExtras().getString("f_ocode"); /*String형*/
        id = "kim";
        //주문 메뉴 통신~~~~

        //리사이클러 뷰
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FoodOrderDetailActivity.this);
//        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(mLayoutManager);
//        foodMenuAdapter = new FoodMenuAdapter(FoodOrderDetailActivity.this, foodVo);
//        recyclerView.setAdapter(foodCartAdapter);
//        tvTitle.setText(fname);
    }

    //카카오페이 결제요청 통신
    protected void callApi() {

        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        //통신
        Call<FoodOrderVO> call = apiInterface.getOrderDerailInfo(paramFocode, id);
        call.enqueue(new Callback<FoodOrderVO>() {
            @Override
            public void onResponse(Call<FoodOrderVO> call, Response<FoodOrderVO> response) {
                //Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodOrderVO resource = response.body(); //결제요청 응답

                }
            }

            @Override
            public void onFailure(Call<FoodOrderVO> call, Throwable t) {
                Log.d("스토어 통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }
}
