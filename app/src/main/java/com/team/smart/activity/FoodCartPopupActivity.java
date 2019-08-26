package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.adapter.FoodCartAdapter;
import com.team.smart.customfonts.MyTextView_Roboto_Regular;
import com.team.smart.vo.FoodCartVO;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class FoodCartPopupActivity extends AppCompatActivity {
    private ArrayList<FoodCartVO> foodVo = new ArrayList<FoodCartVO>();
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private TextView tvTitle, tvTotPrice;
    private MyTextView_Roboto_Regular cartBtn;
    private RecyclerView recyclerView;
    private Button btnClear,btnAddFood;
    private FoodCartAdapter foodCartAdapter;
    private String fname;
    private ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_cart_popup);
        getdataIntent();
        findid();
        priceSum();
        setData();
        configuListner();
    }

    //뒤로가기 -
    private void backScreen() {
        Intent intent = new Intent();
        intent.putExtra("foodList", foodVo);
        setResult(200, intent);
        finish();
    }


    private void configuListner() {
        /* 장바구니 결제하기 */
        cartBtn.setOnClickListener((view -> {
            //Intent
            Intent intent = new Intent(getApplicationContext(), FoodOrderActivity.class);
            intent.putExtra("orderMenuList", foodVo);
            this.startActivity(intent);
        }));

        /* 더 담으러 가기 */
        btnAddFood.setOnClickListener(view -> {
            backScreen();
        });

        /* 뒤로가기 버튼 */
        backBtn.setOnClickListener(view -> {
            backScreen();
        });

        btnClear.setOnClickListener(view -> {
            foodVo.clear();
            foodCartAdapter.notifyDataSetChanged();
            priceSum();
        });

        //리사이클러 아이템에 클릭 리스너
        foodCartAdapter.setItemClick(new FoodCartAdapter.ItemClick() {
            @Override
            public void onPlus(View view, int position) {
                if (foodVo.size() > position) {
                    foodVo.get(position).setF_cnt(foodVo.get(position).getF_cnt() + 1);
                    foodCartAdapter.notifyDataSetChanged(); //카트리스트 갱신
                    priceSum();
                }
            }

            @Override
            public void onMinus(View view, int position) {
                if (foodVo.size() > position) {
                    foodVo.get(position).setF_cnt(foodVo.get(position).getF_cnt() - 1);
                    foodCartAdapter.notifyDataSetChanged();//카트리스트 갱신
                    priceSum();

                }

            }

            @Override
            public void onDel(View view, int position) {
                if (foodVo.size() > position) {
                    foodVo.remove(position);
                    foodCartAdapter.notifyDataSetChanged();
                    priceSum();
                }

            }
        });

    }

    private void priceSum() {
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
        tvTotPrice.setText(numberComma.format(sum) + "원");
        cartBtn.setText(numberComma.format(sum) + "원 주문하기");

    }

    private void setData() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(FoodCartPopupActivity.this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        foodCartAdapter = new FoodCartAdapter(FoodCartPopupActivity.this, foodVo);
        recyclerView.setAdapter(foodCartAdapter);
        tvTitle.setText(fname);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("foodList", foodVo);
        setResult(200, intent);
        finish();
    }

    private void findid() {
        tvTitle = (TextView) findViewById(R.id.tv_comp_org);
        tvTotPrice = (TextView) findViewById(R.id.tv_tot_price);
        cartBtn = (MyTextView_Roboto_Regular) findViewById(R.id.cartBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        btnClear = (Button) findViewById(R.id.btn_clear);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        btnAddFood = (Button)findViewById(R.id.btn_addfood);
    }

    private void getdataIntent() {
        Intent intent = getIntent();
        foodVo = (ArrayList<FoodCartVO>) intent.getSerializableExtra("foodList");
        fname = intent.getStringExtra("fname");
    }


}