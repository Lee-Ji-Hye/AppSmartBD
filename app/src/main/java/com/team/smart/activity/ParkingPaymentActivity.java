package com.team.smart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.ListViewBtnAdapter;
import com.team.smart.adapter.MyCustomPagerAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.ParkingBasicOrderVO;
import com.team.smart.vo.ParkingBasicVO;
import com.team.smart.vo.ParkingCarHistoryVO;
import com.team.smart.vo.ParkingPayOrderDetailVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingPaymentActivity extends AppCompatActivity {
    private TextView paymentBtn;
    LinearLayout userTicketLayout;
    String inoutcode,b_code,userid,carnum; //입출차코드
    ImageView carNumImg;
    private APIInterface apiCarInterface,apiParkingPriceInterface,apiParkingOrderInterface;
    TextView carnumTv,b_nameTv,intimeTv,parkingTimeTv,ParkingTime2Tv,payTimeTv,payTotalTv,totalMoneyTv,timeTv,payTv,freeTv,useTicketBtn;
    long totalMin,nowPayTime;
    long payTotalMin;//결제해야할 총 시간
    int totalPrice; //최종 결제금액
    int payCnt = 0;//결제한 횟수 -> ex ) 4분 주차시 처음 이면 무조건 기본요금이 결제 되지만 한번 결제한 이후면 10분미만은 봐줌
    //입출차내역정보 가져오기
    protected void callParkingCarhistory(String carnum) {
        if(apiCarInterface == null) {
            apiCarInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<ParkingCarHistoryVO> call = apiCarInterface.getCarHistory2(inoutcode);
        call.enqueue(new Callback<ParkingCarHistoryVO>() {
            @Override
            public void onResponse(Call<ParkingCarHistoryVO> call, Response<ParkingCarHistoryVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingCarHistoryVO resource = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("입출차 정보 통신~~~~",json3);
                    carnumTv.setText(resource.getCarHistories().get(0).getCar_number());
                    b_nameTv.setText(resource.getCarHistories().get(0).getB_name());
                    String intime = resource.getCarHistories().get(0).getIn_time();
                    intimeTv.setText(intime);
                    Glide.with(ParkingPaymentActivity.this).load(resource.getCarHistories().get(0).getCar_number_img()).placeholder(R.drawable.no_img)
                            .error(R.drawable.no_img).into(carNumImg);
                    //시간 계산
                    try {
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                        Date d1 = f.parse(intime);
                        Date d2 = new Date();
                        long diff = d2.getTime() -d1.getTime();
                        totalMin= diff / 60000; //차이나는 시간 만큼의 분
                        String timeStr1 = minTohour(totalMin);
                        parkingTimeTv.setText(timeStr1);
                        ParkingTime2Tv.setText(timeStr1);
                        payTotalTv.setText(timeStr1); //처음 셋팅

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    callParkingOrder();
                }
            }

            @Override
            public void onFailure(Call<ParkingCarHistoryVO> call, Throwable t) {
                Log.d("입출차 정보 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }
    //결제정보 가져오기
    protected void callParkingOrder() {
        if(apiParkingOrderInterface == null) {
            apiParkingOrderInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<ParkingPayOrderDetailVO> call = apiParkingOrderInterface.getPayTime(inoutcode);
        call.enqueue(new Callback<ParkingPayOrderDetailVO>() {
            @Override
            public void onResponse(Call<ParkingPayOrderDetailVO> call, Response<ParkingPayOrderDetailVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingPayOrderDetailVO resource = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("구매 정보 통신~~~~",json3);

                    if (resource.getPayOrders().size()>0){
                        int payMin=0; //결제한 시간
                        for(int i=0;i<resource.getPayOrders().size();i++){
                            int time = Integer.parseInt(resource.getPayOrders().get(i).getPay_enable_time());
                            payMin=payMin+time;
                            payCnt++; //결제한 횟수 ->
                        }
                        //결제한 시간 = 총 주차시간 - 결제했던 시간
                        payTotalMin = totalMin - payMin;
                        String timeStr1 = minTohour(payMin);
                        String timeStr2 = minTohour(payTotalMin);
                        //결제 했더 ㄴ시간 넣기
                        if (payTotalMin <0){ //결제할 시간이 저 작을때
                            payTimeTv.setText(timeStr1);
                            payTotalTv.setText(0+"분");
                        }else{
                            payTimeTv.setText(timeStr1);
                            payTotalTv.setText(timeStr2);
                        }
                    }else {
                        payTimeTv.setText(0+"분");
                        payTotalMin=totalMin;
                    }
                    callParkingPrice(b_code);
                }
            }

            @Override
            public void onFailure(Call<ParkingPayOrderDetailVO> call, Throwable t) {
                Log.d("구매 정보 fail~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }
    public String minTohour(long totalmin){
        String result = "";
        if(totalmin>=60){
            int hour = (int)totalmin/60;
            int min = (int)totalmin-(60*hour);
            result = hour+"시간 "+min+"분";
        }else{
            result = totalmin+"분";
        }
        return result;
    }

    //주차요금 가져오기
    protected void callParkingPrice(String carnum) {
        if(apiParkingPriceInterface == null) {
            apiParkingPriceInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<ParkingBasicVO> call = apiParkingPriceInterface.getParkingPrice(b_code);
        call.enqueue(new Callback<ParkingBasicVO>() {
            @Override
            public void onResponse(Call<ParkingBasicVO> call, Response<ParkingBasicVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingBasicVO resource = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("주차요금 정보 통신~~~~",json3);
                    if (resource.getbasicPrices().size()>0){
                        ParkingBasicVO.basicInfo data = resource.getbasicPrices().get(0);
                        String type= data.getBp_type();
                        //주차요금 결제
                        totalPrice = 0; //결제할 금액
                        int parkingPrice = data.getPb_price();//기본시간당 금액

                        payTv.setText(data.getPb_price()+"원");
                        freeTv.setText(data.getPb_free()+"분당/"+data.getPb_free_price()+"원");
                        if(type.equalsIgnoreCase("h")){ //기본요금이 시간당일때
                            timeTv.setText(data.getPb_time()+"시간");
                            int hourtype = data.getPb_time()*60;
                            int payhourcnt =0; //몇시간인지
                            if (payTotalMin>0){
                                if(payTotalMin>=hourtype && payTotalMin>0 || totalMin >=hourtype ){ //기본요금 적용 시간
                                    payhourcnt = (int)payTotalMin/hourtype;
                                    nowPayTime =hourtype*payhourcnt;
                                    totalPrice = (int)payhourcnt*parkingPrice; //기본 돈 넣기

                                    if(payTotalMin%hourtype>=data.getPb_free() &&data.getPb_free() !=0 ){ //몇분 추가당
                                        int payhourfreecnt = (int)(payTotalMin-nowPayTime)/data.getPb_free();
                                        totalPrice = totalPrice+payhourfreecnt*data.getPb_free_price();
                                        nowPayTime = nowPayTime+payhourfreecnt*data.getPb_free(); //ex 10분 *1
                                    }

                                }else { //기본요금 미초과시
                                    totalPrice = data.getPb_price(); //기본요금만
                                    nowPayTime = data.getPb_time()*60; //최종적으로 구매된 시간 ex 기본 1시간 -> 1*60분
                                }
                            }else{
                                paymentBtn.setText("주차 가능 시간 확인");
                                totalPrice = 0;
                                nowPayTime = 0;
                                payTotalTv.setText("0분");
                            }

                        }else{ //분당
                            timeTv.setText(data.getPb_time()+"분당");
                            int mintype = data.getPb_time(); //기본요금 적용 시간
                            if(payTotalMin>=mintype && payTotalMin>0 ||  totalMin >=mintype) { //기본요금 이상 ex 1시간 43분
                                int paymincnt = (int)payTotalMin/mintype;
                                totalPrice = paymincnt*data.getPb_price();
                                nowPayTime = mintype*paymincnt; //ex 30분 *3
                                if (payTotalMin>0){
                                    if((int)payTotalMin%mintype>=data.getPb_free() &&data.getPb_free() !=0){ //남은 시간이 초과시간 이상일때 ex 13분 남았고 10분당 100원
                                        int payminfreecnt = (int)(payTotalMin-nowPayTime)/data.getPb_free();
                                        totalPrice = totalPrice+payminfreecnt*data.getPb_free_price();
                                        nowPayTime = nowPayTime+payminfreecnt*data.getPb_free(); //ex 10분 *1
                                    }

                                }else{//기본요금 미초과시
                                    totalPrice = data.getPb_price(); //기본요금만
                                    nowPayTime = data.getPb_time(); //최종적으로 구매된 시간 ex 기본 30분 -> 30분
                                }
                            }else{ //결제할 시간이 0이하면
                                paymentBtn.setText("주차 가능 시간 확인");
                                totalPrice = data.getPb_price(); //기본요금만
                                nowPayTime = 0;
                                payTotalTv.setText("0분");
                            }
                            //돈 넣기
                        }
                        totalMoneyTv.setText(totalPrice+"원");

                        payTotalTv.setText("0분");
                        if(totalPrice ==0){ //결제할 돈이 0원이염ㄴ
                            payTotalTv.setText("0분");
                        }else{
                            payTotalTv.setText(minTohour(nowPayTime)); //구매할 시간
                        }
                        Log.d("구매할 시간은 ~~~~~~~~~~~~",totalPrice+"원");
                        Log.d("구매할 시간은 ~~~~~~~~~~~~",nowPayTime+"분");
                    }else{ //등록된 요금 정보가 없을 때
                        // 다이얼로그 바디
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(ParkingPaymentActivity.this);
                        // 다이얼로그 메세지
                        alertdialog.setMessage("등록된 주차 요금 정보가 없습니다. \n관리자에게 문의하세요.");

                        // 확인버튼
                        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        // 메인 다이얼로그 생성
                        AlertDialog alert = alertdialog.create();
                        // 아이콘 설정
                        alert.setIcon(R.drawable.logo);
                        // 타이틀
                        alert.setTitle("주차 결제");
                        // 다이얼로그 보기
                        alert.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ParkingBasicVO> call, Throwable t) {
                Log.d("주차요금 정보 fail~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        nowPayTime = 0;
        //p_code 값 받아오기
        Intent intent = getIntent();
        inoutcode = intent.getExtras().getString("inoutcode");
        b_code = intent.getExtras().getString("b_code");
        Log.d("입출차 코드~~~~~",inoutcode);
        carnumTv =findViewById(R.id.carnumTv);
        b_nameTv =findViewById(R.id.b_nameTv);
        intimeTv =findViewById(R.id.intimeTv);
        parkingTimeTv = findViewById(R.id.parkingTimeTv);
        carNumImg=findViewById(R.id.carNumImg);
        ParkingTime2Tv =findViewById(R.id.ParkingTime2Tv);
        paymentBtn = findViewById(R.id.paymentBtn);
        payTimeTv = findViewById(R.id.payTimeTv);
        payTotalTv = findViewById(R.id.payTotalTv);
        totalMoneyTv = findViewById(R.id.totalMoneyTv);
        timeTv= findViewById(R.id.timeTv);
        userTicketLayout=findViewById(R.id.userTicketLayout);
        useTicketBtn = findViewById(R.id.useTicketBtn);
        useTicketBtn.setText(Html.fromHtml("<u>사용하기></u>"));
        userid = "";
        userid=SPUtil.getUserId(this); //아이디
        if (userid.equals("")){
            userid="비회원";
            userTicketLayout.setVisibility(View.GONE);
        }
        //회원일때만 주차권 정보가 보인다.

        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //이미지뷰에 이미지 넣기
        carNumImg.setImageResource(R.drawable.carnum);
        callParkingCarhistory(inoutcode);



        //결제버튼 클릭시
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "결제 버튼 눌림", Toast.LENGTH_LONG).show();
                if (totalPrice >0){

                    List<Object> completeList = new ArrayList<Object>();
                    ParkingBasicOrderVO vo  = new ParkingBasicOrderVO();
                    vo.setInoutcode(inoutcode);
                    vo.setUserid(userid);
                    vo.setPay_price(totalPrice);
                    vo.setPay_type("money");
                    vo.setPay_enable_time((int)nowPayTime);
                    vo.setCarnum(carnumTv.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), PakingBasicKakaoActivity.class); //PakingBasicKakaoActivity 이동할 준비
                    intent.putExtra("orderVO",vo);
                    startActivity(intent);
                }else{ //0원 일때

                }
            }
        });
    }
}
