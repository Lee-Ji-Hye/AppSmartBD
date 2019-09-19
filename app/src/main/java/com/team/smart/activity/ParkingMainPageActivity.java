package com.team.smart.activity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.ParkingMarkerItem;

import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingMainPageActivity extends HeaderActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LinearLayout chooseLayout;
    Button carNumPayBtn;

    private static final int REQUEST_CODE_PERMISSIONS = 1000;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ArrayList<ParkingMarkerItem> sampleList;

    Marker selectedMarker;
    View marker_root_view;
    TextView tv_marker;

    private int reqCode = 1000;
    //정보
    ArrayList<ParkingBDVO.Parking> ParkingList;
    //ArrayList<FoodStoreVO.Store> foodStore;

    private APIInterface apiParkingInterface;

    protected NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(ParkingBDVO data);
        void  failed(String message);
    };


    //메뉴 통신
    protected void callParkingListApi() {

        if(apiParkingInterface == null) {
            apiParkingInterface = APIClient.getClient().create(APIInterface.class);
        }

        //메뉴 api통신
        networkResponse = new NetworkResponse() {
            @Override
            public void success(ParkingBDVO data) {
                Toast.makeText(getApplicationContext(),"sucess",Toast.LENGTH_SHORT).show();
                //정보세팅
                //업체정보세팅
                ParkingList = data.getParkingBDsList();
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<ParkingBDVO> call = apiParkingInterface.getParkingInfo();
        call.enqueue(new Callback<ParkingBDVO>() {
            @Override
            public void onResponse(Call<ParkingBDVO> call, Response<ParkingBDVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    ParkingBDVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getParkingBDsList());
                    Log.d("주차장 정보~~~~",json3);

                    if(networkResponse!=null) {
                        networkResponse.success(resource);
                        //마커 넣기
                        getSampleMarkerItems(ParkingList);
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



    String userid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //공통헤더 쓸때 필요한 부분=================
        setView(R.layout.activity_parkingmainpage);
        setMenuDisplay(true);
        setSearchDisplay(true);
        setTitle("주차장");
        //=======================================

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) this.getFragmentManager()
                .findFragmentById(R.id.parkingMap);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //슬라이딩페이지 홈으로 버튼 클릭시 메인페이지로 이동
        TextView homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //parkingsearchActivity 이동
                startActivity(intent);
            }
        });
        userid="";
        userid = SPUtil.getUserId(this); //아이디

        //차번호로 결제 버튼 클릭시 로그인 전이면 로그인/비회원 묻는 페이지 나오기
        carNumPayBtn = findViewById(R.id.carNumPayBtn);
        carNumPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), BeaconActivity.class); //parkingsearchActivity 이동
                // startActivity(intent);
                Log.d("로그인한 아이디~~~~~",""+userid);
                if (userid.equals("")){
                    chooseLayout = findViewById(R.id.chooseLayout);
                    if(chooseLayout.getVisibility() == view.GONE) {
                        chooseLayout.setVisibility(view.VISIBLE);
                        carNumPayBtn.setText("닫기");
                    } else {
                        chooseLayout.setVisibility(view.GONE);
                        carNumPayBtn.setText("차번호로 바로 결제하기");
                    }
                }else{
                    Intent intent2 = new Intent(getApplicationContext(), ParkingCarSearchActivity.class); //parkingsearchActivity 이동
                    intent2.putExtra("userid",userid); //아이디 전달
                    startActivity(intent2);

                }

            }
        });

        //로그인 버튼 눌렀을때
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class); //parkingsearchActivity 이동
                startActivity(intent);
            }
        });

        //비회원 결제 눌렀을때 차번호 찾기 페이지로 이동
        Button anyonePayBtn = findViewById(R.id.anyonePayBtn);

        anyonePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), ParkingCarSearchActivity.class); //parkingsearchActivity 이동
                intent1.putExtra("userid",""); //아이디 전달
                startActivity(intent1);
            }
        });

        //통신
        callParkingListApi();

    }
    //onCreate 끝

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.480372,126.877127), 16));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        setCustomMarkerView();
    }

    private void setCustomMarkerView() {

        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    private void getSampleMarkerItems(ArrayList<ParkingBDVO.Parking> ParkingList) {
        ArrayList<ParkingMarkerItem> sampleList = new ArrayList();

        for(int i= 0;i<ParkingList.size();i++) {
            sampleList.add(new ParkingMarkerItem(ParkingList.get(i).getLat(), ParkingList.get(i).getLon(), ParkingList.get(i).getB_name(), ParkingList.get(i).getB_code()));
        }
        for (ParkingMarkerItem ParkingMarkerItem : sampleList) {
            addMarker(ParkingMarkerItem, false);
        }
    }

    private Marker addMarker(ParkingMarkerItem markerItem, boolean isSelectedMarker) {

        LatLng position = new LatLng(markerItem.getLat(), markerItem.getLon());
        String b_name = markerItem.getB_name();
        String b_code = markerItem.getB_code();
        String formatted = b_name;

        tv_marker.setText(formatted);

        if(isSelectedMarker) {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
        } else {
            tv_marker.setBackgroundResource(R.drawable.ic_marker_phone_blue);
            tv_marker.setTextColor(Color.WHITE);
            tv_marker.setTextSize(12);
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(b_code);
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker_root_view)));

        return mMap.addMarker(markerOptions);
    }

    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {

        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String b_code = marker.getTitle();
        String b_name =marker.getId();
        ParkingMarkerItem temp = new ParkingMarkerItem(lat, lon, b_name,b_code);
        return addMarker(temp, isSelectedMarker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        Toast.makeText(getApplicationContext(), marker.getTitle()+" 마커 버튼 눌림", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ParkingBuildinginfo.class); //ParkingMainActivity 이동할 준비
        intent.putExtra("b_code",marker.getTitle());
        startActivity(intent);
        return true;
    }

    private void changeSelectedMarker(Marker marker) {

        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onLastLocationButtonClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(myLocation)
                            .title("현재 위치"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    void findid() {

    }

    @Override
    void configuListner() {

    }

    @Override
    void init() {

    }
}
