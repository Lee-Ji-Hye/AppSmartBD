package com.team.smart.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;
import com.team.smart.vo.Code;

import java.io.IOException;
import java.util.List;

public class SearchMapActivity extends AppCompatActivity {

    private Geocoder geocoder;
    private TextView button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        editText = (EditText) findViewById(R.id.editText);
        button=(TextView)findViewById(R.id.button);

        geocoder = new Geocoder(this);

        //버튼 이벤트
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                List<Address> addressList = null;

                try {
                    //editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str,             //주소
                            10); //최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                //콤마를 기준으로 split
                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); //주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);                         //위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);                        //경도
                System.out.println(latitude);
                System.out.println(longitude);

                Intent intent = new Intent();

                //데이터 송신
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("address",address);

                setResult(Code.resultCode, intent);
                finish();
            }
        });
    }
}
