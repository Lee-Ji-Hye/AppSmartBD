package com.team.smart.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.team.smart.R;
import com.team.smart.beacon.BeaconService;

public class MainActivity extends HeaderActivity {

    public Button btnFood,btnParking,bntRental,bntReservation;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //공통헤더 쓸때 필요한 부분=================
        setView(R.layout.activity_main);//자식창
        setMenuDisplay(true);
        setSearchDisplay(false);
        setTitle("Smart");
        //=======================================

        findid();         // 아이디 일괄 셋팅
        configuListner(); // 클릭 리스너 일괄 세팅
        checkBluetooth();

        //비콘서비스 시작
        //비콘서비스는 beacon패키지 안에 들어있고 service란, 앱이 종료되어도 백단에서 돌고있는 서비스임.
        //즉, 앱을 깔고 앱을 처음 실행한 모든 유저(회원, 비회원)는 앱을 종료해도 백단에서 비콘 서비스가 돌고있기 때문에
        //앱을 실행하지 않아도 쿠폰을 자동으로 발송받을 수 있음.
        startService(new Intent(MainActivity.this, BeaconService.class));
    }

    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "bluetooth not supported", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "ble not supported", Toast.LENGTH_SHORT).show();
        }

        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            try {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            } catch (Exception ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }
    }

    //아이디 일괄 셋팅
    @Override
    void findid() {
        btnFood = (Button) findViewById(R.id.btnFood);
        btnParking = findViewById(R.id.btnParking);
        bntRental = findViewById(R.id.bntRental);
        bntReservation = findViewById(R.id.bntReservation);
    }

    //클릭 리스너 일괄 세팅
    @Override
    protected void configuListner() {

        //음식점 페이지 이동
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "음식점 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), FoodListActivity.class); //FoodListActivity로 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        btnParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "주차장 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ParkingMainPageActivity.class); //ParkingMainActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        bntRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "사무실 임대 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), RoomMainActivity.class); // RoomMainActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        bntReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "회의실 예약 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), RoomMainActivity.class); // ReservationActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });


    }

    @Override
    void init() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == reqCode) {

        }
    }
}
