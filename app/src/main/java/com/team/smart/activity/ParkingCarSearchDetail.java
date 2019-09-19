package com.team.smart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.ListViewBtnAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.ParkingCarHistoryVO;
import com.team.smart.vo.UserCarVO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingCarSearchDetail extends AppCompatActivity implements ListViewBtnAdapter.ListBtnClickListener{
    String carnum,b_code;
    ImageView carNumImg;
    private APIInterface apiCarInterface;
    ParkingCarHistoryVO carVO;
    List<String> imageList,b_nameList,inoutcodeList,b_codeList,carnumList;

    protected void callParkingCarhistory(String carnum) {
        if(apiCarInterface == null) {
            apiCarInterface = APIClient.getClient().create(APIInterface.class);
        }
        //통신
        Call<ParkingCarHistoryVO> call = apiCarInterface.getCarHistory(carnum);
        call.enqueue(new Callback<ParkingCarHistoryVO>() {
            @Override
            public void onResponse(Call<ParkingCarHistoryVO> call, Response<ParkingCarHistoryVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingCarHistoryVO resource = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("입출차 정보 통신~~~~",json3);

                    if(resource.getCarHistories().size() >0){
                        ListView listview ;
                        ListViewBtnAdapter adapter;
                        ArrayList<ListViewBtnItem> items = new ArrayList<ListViewBtnItem>() ;
                        imageList = new ArrayList<String>();
                        b_codeList = new ArrayList<String>();
                        inoutcodeList = new ArrayList<String>();
                        b_nameList = new ArrayList<String>();
                        carnumList = new ArrayList<String>();

                        for(int i =0;i<resource.getCarHistories().size();i++){
                            String carnum = resource.getCarHistories().get(i).getCar_number();
                            String time = resource.getCarHistories().get(i).getIn_time();
                            ListViewBtnItem item = new ListViewBtnItem(carnum,time);
                            items.add(item);
                            imageList.add(resource.getCarHistories().get(i).getCar_number_img());
                            b_nameList.add(resource.getCarHistories().get(i).getB_name());
                            inoutcodeList.add(resource.getCarHistories().get(i).getInoutcode());
                            Log.d("건물코드~~~~",resource.getCarHistories().get(i).getB_code());
                            b_codeList.add(resource.getCarHistories().get(i).getB_code());
                            carnumList.add(resource.getCarHistories().get(i).getCar_number());
                        }
                        // Adapter 생성
                        adapter = new ListViewBtnAdapter(ParkingCarSearchDetail.this,R.layout.carhistorylist,items,ParkingCarSearchDetail.this);

                        // 리스트뷰 참조 및 Adapter달기
                        listview = (ListView) findViewById(R.id.listview);
                        listview.setAdapter(adapter);
                        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView parent, View v, int position, long id) {
                                // TODO : item click
                            }
                        }) ;
                    }else {
                        Toast.makeText(getApplicationContext(), "해당하는 차번호가 없습니다.다시입력하세요.", Toast.LENGTH_LONG).show();
                        // 다이얼로그 바디
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(ParkingCarSearchDetail.this);
                        // 다이얼로그 메세지
                        alertdialog.setMessage("해당하는 차번호가 없습니다.\n다시입력하세요.");

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
                        alert.setTitle("차량검색");
                        // 다이얼로그 보기
                        alert.show();

                    }

                }
            }

            @Override
            public void onFailure(Call<ParkingCarHistoryVO> call, Throwable t) {
                Log.d("입출차 정보 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }

    String inoutcode,img,b_name ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        carnum = intent.getExtras().getString("carnum");
        carNumImg = findViewById(R.id.carNumImg);
        setContentView(R.layout.activity_parking_car_search_detail);
        //뒤로가기
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //결제하기
        TextView paymentBtn = findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inoutcode.equals("")){
                    Toast.makeText(getApplicationContext(), "차량을 선택하세요", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ParkingPaymentActivity.class); //ParkingPaymentActivity 이동
                    intent.putExtra("inoutcode",inoutcode);
                    intent.putExtra("b_code",b_code);
                    startActivity(intent);
                }
            }
        });
        callParkingCarhistory(carnum);
    }

    @Override
    public void onListBtnClick(int position) {
        Toast.makeText(this, Integer.toString(position+1) + " Item is selected..", Toast.LENGTH_SHORT).show() ;
        img =imageList.get(position);
        b_name = b_nameList.get(position);
        inoutcode = inoutcodeList.get(position);
        b_code = b_codeList.get(position);
        carnum = carnumList.get(position);
        ImageView carNumImg = findViewById(R.id.carNumImg);
        Glide.with(this).load(img).placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).into(carNumImg);
        TextView b_nameTv = findViewById(R.id.b_nameTv);
        b_nameTv.setText(b_name+"  /  "+carnum);
    }
}
