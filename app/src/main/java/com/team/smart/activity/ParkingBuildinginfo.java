package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.ParkingTicketAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.ParkingTicket;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingBuildinginfo extends AppCompatActivity {
    TextView titleTv,addressTv,opendayTv,openWeekTv,phoneTV,parkNameTV;
    private String b_code;
    private APIInterface apiParkingInterface;

    private ParkingBDVO parkingDB;
    protected NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(ParkingBDVO data);
        void  failed(String message);
    };

    //통신
    protected void callParkingBDListApi(String paramCompSeq) {

        if(apiParkingInterface == null) {
            apiParkingInterface = APIClient.getClient().create(APIInterface.class);
        }

        //api통신
        networkResponse = new NetworkResponse() {
            @Override
            public void success(ParkingBDVO data) {
                Toast.makeText(getApplicationContext(),"sucess",Toast.LENGTH_SHORT).show();
                //정보세팅
                parkingDB = data;
                //btnMenu.performClick(); //메뉴버튼 강제 클릭
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<ParkingBDVO> call = apiParkingInterface.getParkingBDInfo(b_code);
        call.enqueue(new Callback<ParkingBDVO>() {
            @Override
            public void onResponse(Call<ParkingBDVO> call, Response<ParkingBDVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingBDVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getParkingBDsList());
                    Log.d("주차장빌딩정보 통신~~~~",json3);
                    Log.d("주차장빌딩정보 통신~~~~테스트",resource.getParkingBDsList().get(0).getB_code());
                    if(networkResponse!=null) {
                        networkResponse.success(resource);
                        //titleTv,addressTv,opendayTv,openWeekTv,phoneTV,parkNameTV
                        titleTv= findViewById(R.id.titleTv); //건물명
                        titleTv.setText(parkingDB.getParkingBDsList().get(0).getB_name());

                        String totalAddress = parkingDB.getParkingBDsList().get(0).getB_area1()+
                                parkingDB.getParkingBDsList().get(0).getB_area2()+
                                parkingDB.getParkingBDsList().get(0).getB_address();
                        addressTv= findViewById(R.id.addressTv); //주소
                        addressTv.setText(totalAddress);

                        opendayTv= findViewById(R.id.opendayTv); //운영시간_평일
                        opendayTv.setText(parkingDB.getParkingBDsList().get(0).getOperate_time_day());

                        openWeekTv= findViewById(R.id.openWeekTv); //운영시간_주말
                        openWeekTv.setText(parkingDB.getParkingBDsList().get(0).getOperate_time_week());

                        phoneTV= findViewById(R.id.phoneTV); //관리자 전화번호
                        phoneTV.setText(parkingDB.getParkingBDsList().get(0).getOperate_tel());

                        parkNameTV= findViewById(R.id.parkNameTV); //관리사무소 이름
                        parkNameTV.setText(parkingDB.getParkingBDsList().get(0).getB_name()+" 관리사무소");
                    }
                }
            }
            @Override
            public void onFailure(Call<ParkingBDVO> call, Throwable t) {
                Log.d("통신 fail~~~~~~~~~...", "실패..");
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
        setContentView(R.layout.activity_parking_buildinginfo);

        //b_code값 받아오기
        Intent intent = getIntent();
        b_code = intent.getExtras().getString("b_code");

        //통신 메소드 호출
        callParkingBDListApi(b_code);


        //=================== 주차권 =====================
        // 1. 데이터 생성
        ArrayList<ParkingTicket> data = new ArrayList<>();

        //매개변수 생성자를 통해 값 전달해서 리스트에 추가
        data.add(new ParkingTicket("a001","bd001", 30,3000));
        data.add(new ParkingTicket("a002","bd001", 60,3000));


        // 2. 어댑터 생성 .. 데이터를 가져오기 위함
        ParkingTicketAdapter adapter = new ParkingTicketAdapter(data);

        // 3. 뷰와 어댑터를 연결
        ListView listview = findViewById(R.id.list_view);
        listview.setAdapter(adapter);

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //결제버튼
        TextView paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingTicketPayment.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });

        ImageButton openBtn = findViewById(R.id.openBtn);

        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout spinnerLayout = findViewById(R.id.spinnerLayout);

                if(spinnerLayout.getVisibility() == view.GONE) {
                    spinnerLayout.setVisibility(view.VISIBLE);
                } else {
                    spinnerLayout.setVisibility(view.GONE);
                }
            }
        });

    }
}
