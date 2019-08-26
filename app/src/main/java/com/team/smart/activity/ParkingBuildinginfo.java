package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.team.smart.R;
import com.team.smart.adapter.FoodMainMenuAdapter;
import com.team.smart.adapter.ParkingTicketAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.ParkingTicketVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingBuildinginfo extends AppCompatActivity {
    TextView titleTv,addressTv,opendayTv,openWeekTv,phoneTV,parkNameTV;
    private String b_code;
    private APIInterface apiParkingInterface,apiTicketInterface;
    TextView paymentBtn;
    ImageButton openBtn;
    private ParkingBDVO parkingDB;
    public ParkingTicketVO parkingTicketVO;
    private ParkingTicketAdapter ticketAdapter;
    HashMap<String, Object> spinnerMap;

    public ArrayList<ParkingTicketVO.ParkingTicket> t_list;
    interface NetworkResponse {
        void  success(ParkingBDVO data);
        void  failed(String message);
    };

    //주차장 정보 통신
    protected NetworkResponse networkResponse;
    protected void callParkingBDListApi(String b_code) {

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


    //주차권 정보 api 통신
    protected NetworkResponse2 networkResponse2;
    interface NetworkResponse2 {
        void  success(ParkingTicketVO data);
        void  failed(String message);
    };

    //스토어 통신
    protected void callParkingTicketApi(String b_code) {
        if(apiTicketInterface == null) {
            apiTicketInterface = APIClient.getClient().create(APIInterface.class);
        }

        //메뉴 디테일리스트 api통신
        networkResponse2 = new NetworkResponse2() {
            @Override
            public void success(ParkingTicketVO data) {

                LinearLayout tikectInfoTv = findViewById(R.id.tikectInfoTv);
                paymentBtn = findViewById(R.id.paymentBtn);
                openBtn = findViewById(R.id.openBtn);
                tikectInfoTv.setVisibility(View.VISIBLE);
                paymentBtn.setVisibility(View.VISIBLE);
                openBtn.setVisibility(View.VISIBLE);

                if (data.getParkingTickets().size()>0) {
                    //=================== 주차권 =====================
                    // 리사이클러뷰에 표시할 데이터 리스트 생성.
                    t_list = data.getParkingTickets();

                    // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                    RecyclerView recyclerView = findViewById(R.id.recycler1) ;
                    recyclerView.setLayoutManager(new LinearLayoutManager(ParkingBuildinginfo.this)) ;

                    // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
                    ticketAdapter = new ParkingTicketAdapter(t_list);
                    recyclerView.setAdapter(ticketAdapter);

                    List<ParkingTicketVO.ParkingTicket> ticket=data.getParkingTickets();
                    Spinner spinner = findViewById(R.id.spinner);

                    //넣어줄 값 정리
                    ArrayList arrayList = new ArrayList<>();
                    ArrayList valueList = new ArrayList<>();
                    String h_type ="";
                    String S_text= "";
                    for(int i=0;i<ticket.size();i++){
                        h_type = ticket.get(i).getP_type();

                        if (h_type.equalsIgnoreCase("h")){
                            S_text="시간권 ["+ticket.get(i).getHourly()+"시간]";
                        }else if (h_type.equalsIgnoreCase("d")){
                            S_text="일일권";
                        }else{
                            S_text="시간권 ["+ticket.get(i).getHourly()+"분]";
                        }
                        arrayList.add(S_text);
                        valueList.add(ticket.get(i).getP_code());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
                    spinner.setAdapter(arrayAdapter);

                    //스피너 클릭 이벤트

                }else{ //주차권정보가 없으면


                }
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<ParkingTicketVO> call = apiTicketInterface.getParkingTicketInfo(b_code);
        call.enqueue(new Callback<ParkingTicketVO>() {
            @Override
            public void onResponse(Call<ParkingTicketVO> call, Response<ParkingTicketVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingTicketVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getParkingTickets());
                    Log.d("주차권 정보 통신~~~~",json3);

                    if(networkResponse2!=null) {
                        networkResponse2.success(resource);


                    }
                }
            }
            @Override
            public void onFailure(Call<ParkingTicketVO> call, Throwable t) {
                Log.d("티켓 정보 fail~~~~~~~~~...", "실패..");
                call.cancel();
                if(networkResponse2!=null) {
                    networkResponse2.failed("주차권 정보 통신실패");
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_buildinginfo);

        LinearLayout tikectInfoTv = findViewById(R.id.tikectInfoTv);
        paymentBtn = findViewById(R.id.paymentBtn);
        openBtn = findViewById(R.id.openBtn);
        tikectInfoTv.setVisibility(View.GONE);
        paymentBtn.setVisibility(View.GONE);
        openBtn.setVisibility(View.GONE);

        //b_code값 받아오기
        Intent intent = getIntent();
        b_code = intent.getExtras().getString("b_code");

        //통신 메소드 호출
        callParkingBDListApi(b_code);
        callParkingTicketApi(b_code);

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //결제버튼

        paymentBtn = findViewById(R.id.paymentBtn);
        openBtn = findViewById(R.id.openBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParkingTicketPayment.class); //ParkingMainActivity 이동
                startActivity(intent);
            }
        });

        //옵션창 여는 버튼
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
