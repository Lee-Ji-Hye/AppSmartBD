package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.RoomRecyclerAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.RoomBVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomRecyclerAdapter roomAdapter;

    private String paramBuildingCode; //Intent parameter

    private APIInterface apiRoomInterface;

    //정보
    private ArrayList<RoomBVO.Room> roomList;

    protected NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(RoomBVO data);
        void  failed(String message);
    };

    //private String r_img[] = {"room13","room11"};               //매물 사진
    //private String r_name[] = {"403","302"};                    //매물명
    //private String r_type[] = {"전세","월세"};                   //거래 타입
    //private String r_price[] = {"1000","70"};                   //매물 가격
    //private String r_reposit[] = {"0","2000"};                  //보증금
    //private String r_ofer_fee[] = {"7","7"};                    //관리비
    //private String r_floor[] = {"63","105"};                    //해당층수
    //private String r_indi_space[] = {"회의실","회의실,탕비실"};  //독립공간(회의실,탕비실 등) 유무
    //private String r_able_date[] = {"2019-08-25","2019-08-25"}; //입주가능일
    //private String regidate[] = {"2019-08-25","2019-08-25"};    //등록일

    //매물정보 통신
    protected void callRoomListApi(String paramBuildingCode) {

        if(apiRoomInterface == null) {
            apiRoomInterface = APIClient.getClient().create(APIInterface.class);
        }

        //매물정보 api통신
        networkResponse = new NetworkResponse() {
            @Override
            public void success(RoomBVO data) {
                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                //매물정보 세팅
                roomList = data.getRoomList();

                roomAdapter = new RoomRecyclerAdapter(RoomListActivity.this, roomList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RoomListActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(roomAdapter);
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<RoomBVO> call = apiRoomInterface.getRoomList(paramBuildingCode);
        call.enqueue(new Callback<RoomBVO>() {
            @Override
            public void onResponse(Call<RoomBVO> call, Response<RoomBVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    RoomBVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getRoomList());
                    Log.d("매물정보 통신",json3);

                    if(networkResponse!=null) {
                        networkResponse.success(resource);
                    }
                }
            }
            @Override
            public void onFailure(Call<RoomBVO> call, Throwable t) {
                Log.d("통신 실패~~~~~...", "실패...");
                call.cancel();
                if(networkResponse!=null) {
                    networkResponse.failed("통신실패");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        findid();

        roomList = new ArrayList<>();

        callRoomListApi(paramBuildingCode);

        //roomAdapter = new RoomRecyclerAdapter(RentalListActivity.this, roomList);

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RentalListActivity.this);
        //recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(roomAdapter);
    }

    private void findid() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_room);

        Intent intent = getIntent(); //데이터 수신
        paramBuildingCode = intent.getExtras().getString("b_code"); //String형
    }
}
