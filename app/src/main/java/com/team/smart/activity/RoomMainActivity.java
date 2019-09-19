package com.team.smart.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

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
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.Code;
import com.team.smart.vo.MarkerItem;
import com.team.smart.vo.RoomBVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomMainActivity extends HeaderActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_PERMISSIONS = 1000;

    //Google Map에 현재 위치 정보
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    //Google Map에 Custom Marker 추가
    private GoogleMap mMap;
    View marker_root_view;
    TextView tv_marker;

    //지오 코딩(Geocoding)으로 주소를 해당 좌표(위도, 경도)로 변환
    private Geocoder geocoder;

    //정보
    ArrayList<RoomBVO.Room> RoomList;

    private APIInterface apiRoomCntInterface;

    protected NetworkResponse networkResponse;

    interface NetworkResponse {
        void  success(RoomBVO data);
        void  failed(String message);
    };

    //매물 수량 통신
    protected void callRoomCntApi() {

        if(apiRoomCntInterface == null) {
            apiRoomCntInterface = APIClient.getClient().create(APIInterface.class);
        }

        //매물 수량 api통신
        networkResponse = new NetworkResponse() {
            @Override
            public void success(RoomBVO data) {
                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                //매물 수량 세팅
                RoomList = data.getRoomList();
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<RoomBVO> call = apiRoomCntInterface.getRoomCnt();
        call.enqueue(new Callback<RoomBVO>() {
            @Override
            public void onResponse(Call<RoomBVO> call, Response<RoomBVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    RoomBVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getRoomList());
                    Log.d("매물 수량 통신~~~",json3);

                    if(networkResponse!=null) {
                        networkResponse.success(resource);

                        getSampleMarkerItems(RoomList);
                    }
                }
            }
            @Override
            public void onFailure(Call<RoomBVO> call, Throwable t) {
                Log.d("통신 실패...", "실패...");
                call.cancel();
                if(networkResponse!=null) {
                    networkResponse.failed("통신 실패");
                }
            }
        });
    }

    private void getSampleMarkerItems(ArrayList<RoomBVO.Room> RoomList) {
        ArrayList<MarkerItem> sampleList = new ArrayList();

        if(RoomList != null) {
            for(int i=0; i<RoomList.size(); i++) {
                sampleList.add(new MarkerItem(RoomList.get(i).getB_lat(), RoomList.get(i).getB_lon(), RoomList.get(i).getR_cnt(), RoomList.get(i).getB_code()));
            }

            for (MarkerItem markerItem : sampleList) {
                addMarker(markerItem);
            }
        }
    }

    private void setCustomMarkerView() {
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker_layout, null);
        tv_marker = (TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    private Marker addMarker(MarkerItem markerItem) {

        LatLng position = new LatLng(markerItem.getPosition().latitude, markerItem.getPosition().longitude);
        String r_cnt = markerItem.getR_cnt();
        String b_code = markerItem.getB_code();

        tv_marker.setText(r_cnt);
        tv_marker.setBackgroundResource(R.drawable.circle2);
        tv_marker.setTextColor(Color.WHITE);

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

    private Marker addMarker(Marker marker) {

        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String r_cnt = marker.getTitle();
        String b_code =marker.getId();
        MarkerItem temp = new MarkerItem(lat, lon, r_cnt, b_code);
        return addMarker(temp);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        Toast.makeText(getApplicationContext(), marker.getTitle()+" 마커 버튼 눌림", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), RoomListActivity.class); //RentalListActivity 이동할 준비
        intent.putExtra("b_code",marker.getTitle());
        startActivity(intent);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //공통헤더 쓸 때 필요한 부분=================
        setView(R.layout.activity_room_main);
        setMenuDisplay(true);
        setSearchDisplay(true);
        setTitle("사무실 임대");
        //=========================================

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) this.getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Google Map에 현재 위치 정보
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        callRoomCntApi(); //통신
        findid();         //아이디 일괄 세팅
        configuListner(); //클릭 리스너 일괄 세팅
    }

    //지오 코딩(Geocoding)으로 주소를 해당 좌표(위도, 경도)로 변환
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == Code.requestCode && resultCode == Code.resultCode) {

            String latitude = intent.getStringExtra("latitude");
            String longitude = intent.getStringExtra("longitude");
            String address = intent.getStringExtra("address");

            System.out.println(latitude);
            System.out.println(longitude);
            System.out.println(address);

            //좌표(위도, 경도) 생성
            LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            //해당 좌표로 화면 줌
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
        }
    }

    //Google Map에 Custom Marker 추가
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.480372, 126.877127), 17.0f));
        mMap.setOnMarkerClickListener(this);

        setCustomMarkerView();
    }

    //Google Map에 현재 위치 정보
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
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
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
    public void onConnected(@Nullable Bundle bundle) { }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    //공통헤더
    @Override
    void findid() { }

    @Override
    void configuListner() {
        btSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "주소 검색 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SearchMapActivity.class); //SearchMapActivity 이동할 준비
                startActivityForResult(intent, Code.requestCode);
            }
        });
    }

    @Override
    void init() { }
}
