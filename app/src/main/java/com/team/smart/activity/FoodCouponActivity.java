package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.FoodCouponListAdapter;
import com.team.smart.adapter.FoodOrderListAdapter;
import com.team.smart.vo.FoodCartVO;
import com.team.smart.vo.FoodCouponVO;
import com.team.smart.vo.FoodOrderListVO;

import java.util.ArrayList;

public class FoodCouponActivity extends AppCompatActivity {
    private ArrayList<FoodCouponVO.Coupon> couponList;
    private FoodCouponVO.Coupon couponVo;
    int sum;
    int salePrice = 0;

    private RecyclerView recyclerView;
    private FoodCouponListAdapter foodListAdapter;
    private FrameLayout frTab;

    private ImageButton backBtn;
    private String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_coupon_list);

        getdataIntent();
        findId();
        setCouponList();

    }

    private void getdataIntent() {
        Intent intent = getIntent();
        couponList = (ArrayList<FoodCouponVO.Coupon>) intent.getSerializableExtra("couponList");

        Gson gson = new Gson();
        String json = gson.toJson(couponList);
        Log.d("쿠폰쿠폰 :::: ", json);

        sum = intent.getIntExtra("sum", 0);
    }

    private void findId() {
        frTab   = (FrameLayout) findViewById(R.id.fr_tab);
        backBtn = (ImageButton) findViewById(R.id.img_back_btn);
    }

    private void setCouponList() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view2 = inflater.inflate(R.layout.fragment_food_coupon_list, null);

        recyclerView = view2.findViewById(R.id.rv_couponlist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FoodCouponActivity.this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //상단에서 푸드데이터 통신해서 가져오기~~
        //지금은 new FoodDetailVO()로 넣어놓고 new FoodDetailVO()생성자에서 일단 데이터 셋팅하기.

        foodListAdapter = new FoodCouponListAdapter(FoodCouponActivity.this, couponList);  //<-- 여기에 클릭리스너를 넘긴다~?
        recyclerView.setAdapter(foodListAdapter);

        //리사이클러 아이템에 클릭 리스너
        foodListAdapter.setItemClick((view1, position) -> {
            couponVo = foodListAdapter.getItem(position);
            Gson gson = new Gson();
            String json = gson.toJson(couponVo);
            Log.d("쿠폰asd쿠폰 :::: ", json);
            salePrice = Integer.parseInt(couponVo.getF_coupon_price());
            int totPrice = sum - salePrice;
            //최종 결제금액이 1000원 미만이면 쿠폰 사용 못함.
            if( totPrice < 1000) {
                couponVo = null;
                Toast.makeText(getApplicationContext(), "더 많은 메뉴를 담은 후 쿠폰을 사용해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            backScreen();
        });

        frTab.addView(view2);//프레임뷰

        //프로그레스바 숨김
        if(couponList == null) {
            findViewById(R.id.list_empty).setVisibility(View.VISIBLE);
            findViewById(R.id.rv_orderlist).setVisibility(View.GONE);
        }
    }

    //뒤로가기 -
    private void backScreen() {
        Intent intent = new Intent();
        intent.putExtra("useCoupon", couponVo);
        setResult(200, intent);
        finish();
    }
}
