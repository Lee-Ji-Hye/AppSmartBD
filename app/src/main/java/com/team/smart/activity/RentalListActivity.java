package com.team.smart.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.adapter.RoomRecyclerAdapter;
import com.team.smart.vo.RoomVO;

import java.util.ArrayList;

public class RentalListActivity extends AppCompatActivity {

    private ArrayList<RoomVO> roomListModelClassArrayList;
    private RecyclerView recyclerView;
    private RoomRecyclerAdapter mAdapter;

    private String r_img[] = {"room13","room11"};               //매물 사진
    private String r_name[] = {"403","302"};                    //매물명
    private String r_type[] = {"전세","월세"};                   //거래 타입
    private String r_price[] = {"1000","70"};                   //매물 가격
    private String r_reposit[] = {"0","2000"};                  //보증금
    private String r_ofer_fee[] = {"7","7"};                    //관리비
    private String r_floor[] = {"63","105"};                    //해당층수
    private String r_indi_space[] = {"회의실","회의실,탕비실"};  //독립공간(회의실,탕비실 등) 유무
    private String r_able_date[] = {"2019-08-25","2019-08-25"}; //입주가능일
    private String regidate[] = {"2019-08-25","2019-08-25"};    //등록일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_list);
        RentalListActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        roomListModelClassArrayList = new ArrayList<>();

        for (int i = 0; i < r_name.length; i++) {
            RoomVO beanClassForRecyclerView_contacts = new RoomVO(r_img[i],r_name[i],r_type[i],r_price[i],r_reposit[i],r_ofer_fee[i],r_floor[i],r_indi_space[i],r_able_date[i],regidate[i]);

            roomListModelClassArrayList.add(beanClassForRecyclerView_contacts);
        }
        mAdapter = new RoomRecyclerAdapter(RentalListActivity.this, roomListModelClassArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RentalListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

}