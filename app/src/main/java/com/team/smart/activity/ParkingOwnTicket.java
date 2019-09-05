package com.team.smart.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;
import com.team.smart.adapter.ParkingTicketAdapter;
import com.team.smart.vo.ParkingTicketVO;

import java.util.ArrayList;

public class ParkingOwnTicket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_own_ticket);


        // 1. 데이터 생성
        ArrayList<ParkingTicketVO.ParkingTicket> data = new ArrayList<>();

        // Weather 매개변수 생성자를 통해 값 전달해서 리스트에 추가
        // String b_code, String b_name, String address, int hourly, int count
        //data.add(new ParkingTicketVO.ParkingTicket("bd001","월드벤처2차","가산디지털단지역",1,5));


        // 2. 어댑터 생성 .. 데이터를 가져오기 위함
        ParkingTicketAdapter adapter = new ParkingTicketAdapter(data);

        // 3. 뷰와 어댑터를 연결
        ListView listview = findViewById(R.id.list_view);
        //listview.setAdapter(adapter);
    }
}
