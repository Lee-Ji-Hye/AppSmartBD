package com.team.smart.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ar_st01.AR_ST01Activity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team.smart.R;
import com.team.smart.adapter.MyCustomPagerAdapter;

public class RoomDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap; //Google Map 참조 변수

    ViewPager viewPager;
    int images[] = {R.drawable.property, R.drawable.property, R.drawable.property, R.drawable.property};
    MyCustomPagerAdapter myCustomPagerAdapter;

    private String b_area1,b_area2,b_address,b_year,b_landarea,b_buildarea,b_buildscale
                  ,r_code,r_name,r_type,r_price,r_deposit,r_ofer_fee,r_floor,r_indi_space,r_able_date,regidate,r_area,r_desc
                  ,name,email,hp
                  ,comp_seq;
    private double b_lat,b_lon;
    TextView imageNo,rentalContract
            ,tv_r_type,tv_r_price,tv_r_deposit
            ,tv_r_name,tv_r_indi_space,tv_r_floor,tv_r_ofer_fee
            ,tv_r_able_date,tv_regidate
            ,tv_r_type2,tv_r_floor2,tv_r_ofer_fee2,tv_r_area2,tv_r_able_date2,tv_r_deposit2
            ,tv_r_indi_space2
            ,tv_r_desc2
            ,tv_user_name,tv_user_eamil,tv_user_hp,btnAR;
    ImageView btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        findid();

        myCustomPagerAdapter = new MyCustomPagerAdapter(RoomDetailActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                imageNo.setText("" + (position +1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rentalContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "계약서 작성 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), RoomContractActivity.class); //ParkingMainActivity 이동할 준비
                intent.putExtra("b_area1", b_area1);
                intent.putExtra("b_area2", b_area2);
                intent.putExtra("b_address", b_address);
                intent.putExtra("b_year", b_year);
                intent.putExtra("b_landarea", b_landarea);
                intent.putExtra("b_buildarea", b_buildarea);
                intent.putExtra("b_buildscale", b_buildscale);

                intent.putExtra("r_price", r_price);
                intent.putExtra("r_deposit", r_deposit);

                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("hp", hp);
                intent.putExtra("comp_seq", comp_seq);
                startActivity(intent);
            }
        });

        //SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 Google Map을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread.
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(b_lat, b_lon), 17.0f));
        mMap.addMarker(new MarkerOptions().position(new LatLng(b_lat, b_lon)));
    }

    private void findid() {
        //Intent Parameter Data
        Intent intent = getIntent(); //데이터 수신
        b_area1 = intent.getExtras().getString("b_area1");
        b_area2 = intent.getExtras().getString("b_area2");
        b_address = intent.getExtras().getString("b_address");
        b_year = intent.getExtras().getString("b_year");
        b_landarea = intent.getExtras().getString("b_landarea");
        b_buildarea = intent.getExtras().getString("b_buildarea");
        b_buildscale = intent.getExtras().getString("b_buildscale");

        r_code = intent.getExtras().getString("r_code");
        r_name = intent.getExtras().getString("r_name");
        r_type = intent.getExtras().getString("r_type");
        r_price = intent.getExtras().getString("r_price");
        r_deposit = intent.getExtras().getString("r_deposit");
        r_ofer_fee = intent.getExtras().getString("r_ofer_fee");
        r_floor = intent.getExtras().getString("r_floor");
        r_indi_space = intent.getExtras().getString("r_indi_space");
        r_able_date = intent.getExtras().getString("r_able_date");
        regidate = intent.getExtras().getString("regidate");
        r_area = intent.getExtras().getString("r_area");
        r_desc = intent.getExtras().getString("r_desc");

        b_lat = intent.getExtras().getDouble("b_lat");
        b_lon = intent.getExtras().getDouble("b_lon");

        name = intent.getExtras().getString("name");
        email = intent.getExtras().getString("email");
        hp = intent.getExtras().getString("hp");

        comp_seq = intent.getExtras().getString("comp_seq");

        //find id
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        imageNo = (TextView)findViewById(R.id.imageNo);
        rentalContract = findViewById(R.id.RentalContract);

        btnCall = findViewById(R.id.btnCall);
        btnAR = findViewById(R.id.btnAR);

        tv_r_type = findViewById(R.id.r_type);
        tv_r_price = findViewById(R.id.r_price);
        tv_r_deposit = findViewById(R.id.r_deposit);
        tv_r_name = findViewById(R.id.r_name);
        tv_r_indi_space = findViewById(R.id.r_indi_space);
        tv_r_floor = findViewById(R.id.r_floor);
        tv_r_ofer_fee = findViewById(R.id.r_ofer_fee);
        tv_r_able_date = findViewById(R.id.r_able_date);
        tv_regidate = findViewById(R.id.regidate);

        tv_r_type2 = findViewById(R.id.detail_r_type);
        tv_r_floor2 = findViewById(R.id.detail_r_floor);
        tv_r_ofer_fee2 = findViewById(R.id.detail_r_ofer_fee);
        tv_r_area2 = findViewById(R.id.detail_r_area);
        tv_r_able_date2 = findViewById(R.id.detail_r_able_date);
        tv_r_deposit2 = findViewById(R.id.detail_r_deposit);
        tv_r_indi_space2 = findViewById(R.id.detail_r_indi_space);
        tv_r_desc2 = findViewById(R.id.detail_r_desc);

        tv_user_name = findViewById(R.id.user_name);
        tv_user_eamil = findViewById(R.id.user_email);
        tv_user_hp = findViewById(R.id.user_hp);

        //find id -> text setting
        tv_r_type.setText(r_type);
        tv_r_price.setText(r_price);
        tv_r_deposit.setText(r_deposit);
        tv_r_name.setText(r_name);
        tv_r_indi_space.setText(r_indi_space);
        tv_r_floor.setText(r_floor);
        tv_r_ofer_fee.setText(r_ofer_fee);
        tv_r_able_date.setText(r_able_date);
        tv_regidate.setText(regidate);

        tv_r_type2.setText(r_type);
        tv_r_floor2.setText(r_floor);
        tv_r_ofer_fee2.setText(r_ofer_fee);
        tv_r_area2.setText(r_area);
        tv_r_able_date2.setText(r_able_date);
        tv_r_deposit2.setText(r_deposit);
        tv_r_indi_space2.setText(r_indi_space);
        tv_r_desc2.setText(r_desc);

        tv_user_name.setText(name);
        tv_user_eamil.setText(email);
        tv_user_hp.setText(hp);

        btnCall.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+hp));
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        btnAR.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "매물 AR 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AR_ST01Activity.class); //RoomUnityActivity 이동할 준비
                startActivity(intent);
            }
        });
    }
}
