package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.ParkingOrderVO;
import com.team.smart.vo.ParkingTicketVO;
import com.team.smart.vo.UserCarVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingTicketPayment extends AppCompatActivity {
    String p_code,userid; //주차권상품 코드
    public ParkingTicketVO.ParkingTicket Ticket;
    public ParkingOrderVO orderVO;
    private APIInterface apiTicketInterface,apiUserInterface;
    TextView b_nameTv,t_nameTv,priceTV,p_countBtn,paymentBtn,TotalpriceTV,nameTv,hpTv;

    EditText countEdit;
    int count;
    String o_count;
    //주차권정보 api
    protected void callParkingTicketApi(String b_code) {
        if(apiTicketInterface == null) {
            apiTicketInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<ParkingTicketVO> call = apiTicketInterface.getParkingTicketOne(p_code);
        call.enqueue(new Callback<ParkingTicketVO>() {
            @Override
            public void onResponse(Call<ParkingTicketVO> call, Response<ParkingTicketVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingTicketVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getParkingTickets());
                    Log.d("주차권 정보 통신~~~~",json3);

                    //하나의 주차권만 가져오므로 0번째 정보만 받아오면 됨
                    Ticket = resource.getParkingTickets().get(0);
                    b_nameTv.setText(Ticket.getB_name()+" ["+Ticket.getB_area2()+" "+Ticket.getB_address()+"]");
                    priceTV.setText(Ticket.getPrice()+"원");
                    String h_type = Ticket.getP_type();
                    String S_text ="";
                    if (h_type.equalsIgnoreCase("h")){
                        S_text="시간권 ["+Ticket.getHourly()+"시간]";
                    }else if (h_type.equalsIgnoreCase("d")){
                        S_text="일일권";
                    }else{
                        S_text="시간권 ["+Ticket.getHourly()+"분]";
                    }
                    t_nameTv.setText(S_text);
                    int price=Integer.parseInt(Ticket.getPrice());

                    TotalpriceTV.setText("0원");

                    //키보드 컨트롤
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    //수량 적용 버튼 클릭시
                    p_countBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //키보드 내리기
                            imm.hideSoftInputFromWindow(countEdit.getWindowToken(), 0);
                            String getEdit = countEdit.getText().toString();
                            getEdit = getEdit.trim();//스페이스 입력시 공백 제거

                            if(getEdit.getBytes().length <= 0) {//빈값이 넘어올때의 처리
                                Toast.makeText(getApplicationContext(), "1 이상의 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                            }else if(Integer.parseInt(getEdit)>100){
                                Toast.makeText(getApplicationContext(), "100개가 최대구매수량입니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                count=Integer.parseInt(getEdit);
                                o_count=getEdit;
                                TotalpriceTV.setText(price*count+"원");
                            }
                        }
                    });

                    //결제 버튼 클릭시
                    paymentBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(count<= 0) { //수량이 0이하면
                                Toast.makeText(getApplicationContext(), "1 이상의 숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                            }else{//수량이 0이상이면
                                //결제 VO에 값 담기
                                int TotalPrice = Integer.parseInt(Ticket.getPrice())*Integer.parseInt(o_count);
                                orderVO = new ParkingOrderVO(Ticket.getP_code().toString(),userid,Integer.toString(TotalPrice),o_count);

                                Gson gson3 = new Gson();
                                String json3 = gson3.toJson(orderVO);
                                Log.d("여기에도 오는지2222222222~~~>", json3);

                                //구매 정보 vo에 담아서 보내기
                                Intent intent2 = new Intent(getApplicationContext(),PakingTicketKakaoActivity.class);
                                intent2.putExtra("orderVO",orderVO);
                                startActivity(intent2);
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ParkingTicketVO> call, Throwable t) {
                Log.d("티켓 정보 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }

    //회원정보 가져오기
    protected void callParkingUserInfo(String userid) {
        if(apiUserInterface == null) {
            apiUserInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<UserCarVO> call = apiUserInterface.getUserInfo(userid);
        call.enqueue(new Callback<UserCarVO>() {
            @Override
            public void onResponse(Call<UserCarVO> call, Response<UserCarVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    UserCarVO resource = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("회원 정보 통신~~~~",json3);
                    nameTv.setText(resource.getName());
                    hpTv.setText(resource.getHp());
                }
            }
            @Override
            public void onFailure(Call<UserCarVO> call, Throwable t) {
                Log.d("회원 정보 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_ticket_payment);

        //p_code값 받아오기
        Intent intent = getIntent();
        p_code = intent.getExtras().getString("p_code");
        Toast.makeText(getApplicationContext(), p_code+" 선택", Toast.LENGTH_SHORT).show();

        //텍스트뷰
        b_nameTv = findViewById(R.id.b_nameTv);
        t_nameTv = findViewById(R.id.t_nameTv);
        countEdit = findViewById(R.id.countEdit);
        priceTV = findViewById(R.id.priceTV);
        TotalpriceTV = findViewById(R.id.totalPriceTV);
        nameTv = findViewById(R.id.nameTv);
        hpTv = findViewById(R.id.hpTv);
        //버튼(버튼역할 텍스트뷰도 포함)
        p_countBtn = findViewById(R.id.p_countBtn); //수량 적용
        paymentBtn = findViewById(R.id.paymentBtn); //결제
        ImageView backBtn = findViewById(R.id.backBtn);//뒤로가기
        count=0;
        //뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         userid = SPUtil.getUserId(this); //아이디
        //주차권 통신 메소드  호출
        callParkingUserInfo(userid);
        callParkingTicketApi(p_code);

    }
}
