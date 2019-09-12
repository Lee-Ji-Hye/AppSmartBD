package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.FoodOrderListAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.FoodOrderListVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//음식 상세 액티비티
public class FoodOrderListActivity extends AppCompatActivity {
    private int REQUESTDETAILFOODCD = 100;
    private int REQUESTFOODCARTCD = 101;

    private RecyclerView recyclerView;
    private FoodOrderListAdapter foodListAdapter;
    private FrameLayout frTab;

    private ImageButton backBtn;

    private String userid;

    private APIInterface apiInterface, apiStoreInterface;
    View viewStoreLongDesc; //업체 긴줄 정보



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_list);

        userid = SPUtil.getUserId(FoodOrderListActivity.this);

        findid();
        configuListner();
        callOrderListApi();
    }


    //메뉴 통신
    protected void callOrderListApi() {

        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }


        //통신
        Call<FoodOrderListVO> call = apiInterface.getFoodOrderList(userid);
        call.enqueue(new Callback<FoodOrderListVO>() {
            @Override
            public void onResponse(Call<FoodOrderListVO> call, Response<FoodOrderListVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodOrderListVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getOrderList());
                    Log.d("디테일 통신~~~~",json3);

                    ArrayList<FoodOrderListVO.List> orderList = resource.getOrderList();

                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View view2 = inflater.inflate(R.layout.fragment_food_order_list, null);

                    recyclerView = view2.findViewById(R.id.rv_orderlist);
                    //recyclerview 사용시 필수  LayoutManager
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(FoodOrderListActivity.this);
                    mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(mLayoutManager);

                    //상단에서 푸드데이터 통신해서 가져오기~~
                    //지금은 new FoodDetailVO()로 넣어놓고 new FoodDetailVO()생성자에서 일단 데이터 셋팅하기.

                    foodListAdapter = new FoodOrderListAdapter(FoodOrderListActivity.this, orderList);  //<-- 여기에 클릭리스너를 넘긴다~?
                    recyclerView.setAdapter(foodListAdapter);

                    //리사이클러 아이템에 클릭 리스너
                    foodListAdapter.setItemClick((view1, position) -> {

                        FoodOrderListVO.List list = foodListAdapter.getItem(position);

                        Intent intent = new Intent(FoodOrderListActivity.this, FoodOrderDetailActivity.class);
                        intent.putExtra("f_ocode",list.getF_ocode());        /*메뉴코드*/
                        intent.putExtra("f_name", list.getF_name()); /*업체코드*/

                        startActivityForResult(intent, REQUESTDETAILFOODCD);

                    });

                    frTab.addView(view2);//프레임뷰

                    //프로그레스바 숨김
                    if(orderList == null) {
                        findViewById(R.id.list_empty).setVisibility(View.VISIBLE);
                        findViewById(R.id.rv_orderlist).setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<FoodOrderListVO> call, Throwable t) {
                Log.d("통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }


    private void findid() {
        frTab   = (FrameLayout) findViewById(R.id.fr_tab);
        backBtn = (ImageButton) findViewById(R.id.img_back_btn);

    }

    private void configuListner() {
        //백버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}