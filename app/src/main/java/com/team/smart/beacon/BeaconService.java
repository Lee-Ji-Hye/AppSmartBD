package com.team.smart.beacon;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.minew.beaconset.BluetoothState;
import com.minew.beaconset.MinewBeacon;
import com.minew.beaconset.MinewBeaconManager;
import com.minew.beaconset.MinewBeaconManagerListener;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.FoodCouponVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeaconService extends Service {
    private String TAG = "BackGroundService";
    private MinewBeaconManager mMinewBeaconManager; //비콘매니저 객체
    private Context mContext;
    private int counter = 1;
    private APIInterface apiInterface; //coupon발급할 통신객체

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mContext = getApplicationContext();
            apiInterface = APIClient.getClient().create(APIInterface.class);
            //service lifeCycle :  onCreate -> onStartCommand
        } catch (Exception ex) {
            Log.e(TAG + "onCrea", ex.getMessage());
        }
    }



    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        try {
            if (mContext == null)
                mContext = getApplicationContext();
            counter = 1;
            //shoroe scan beaconha
            initManager(); //비콘매니저 객체 생성
            initListener();//비콘 범위, disapeare, apeare등등 이벤트리스너 생성
            bindService(); //이제 서비스를 실행하겠음.  --> 서비스가 백그라운드에서 돔
            return Service.START_STICKY;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return Service.START_STICKY;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void GetResultForBeacon(String uuid, int Major, int Minor) {

        try {

            String userid = SPUtil.getUserId(getApplicationContext());

            if(userid.equals("")) {
                return;
            }
            if(Major == 40001 && Minor == 26052) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userid", userid);
                map.put("major", Major + "");
                map.put("minor", Minor + "");

                Log.d("Major : ", String.valueOf(Major));
                Log.d("Minor : ", String.valueOf(Minor));
                /**
                 GET List Resources
                 **/
                Call<FoodCouponVO> call = apiInterface.getBeaconCouponList(map);
                call.enqueue(new Callback<FoodCouponVO>() {
                    @Override
                    public void onResponse(Call<FoodCouponVO> call, Response<FoodCouponVO> response) {
                        if (response.code() == 200) {
                            if(response.body().getResponseCode() != 570 && response.body().getResponseCode() != 572 ) {
                                return;
                            }
                            ShowNotification(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<FoodCouponVO> call, Throwable t) {
                        call.cancel();
                        Toast.makeText(getApplicationContext(), "통신 에러 ", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        } catch (Exception ex) {
            Log.e(TAG + "GetRes", ex.getMessage());
        }
    }


    private void bindService() {
        try {
            // barresie vaziate bluetooth device
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Log.e(TAG, "Device does not support Bluetooth");
                Log.d("msg","btnNotEnabled");
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                    Log.d("msg","btenabled");
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mMinewBeaconManager.mScanCallback = new ScanCallback() {
                    @Override
                    public void onScanFailed(int errorCode) {
                        Log.d("msg","scanfailed"+errorCode);
                        super.onScanFailed(errorCode);
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        Log.d("msg","scanResult"+callbackType);
                        super.onScanResult(callbackType, result);

                    }
                };
            }

            // shoroe scan
            if (mMinewBeaconManager == null) {
                initManager();
                initListener();
            }

            mMinewBeaconManager.startService();
            mMinewBeaconManager.startScan(); //비콘 스캔

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    private void ShowNotification(FoodCouponVO coupons) {
        try {
            if (coupons != null && coupons.getCouponList().size() > 0) {
                //쿠폰이 있을때 in~
                //            // classe popup bar asase niaze poroje tarahi mishavad
                Intent resultIntent = new Intent(BeaconService.this, PopUp.class);

                // etelaate set shode roye beacon baraye namayesh be karbar
                //Titre Campaign
                resultIntent.putExtra("title", "쿠폰 알림");
                resultIntent.putExtra("content",coupons.getCouponList().get(0).getComp_org()+" 할인 쿠폰 도착~!");

                //namayesh notification be user va tanzime namayeshe popup bad az click user
                PendingIntent PendingIntentFragment =
                        PendingIntent.getActivity(BeaconService.this, 2019, resultIntent, 0); //그냥 무조건 넘길 2019 pass

                //핸드폰 스크린 띄우기 --> notify, alert)
                //핸드폰 스크린이 꺼져있어도 스크린이 뜸
                PowerManager.WakeLock screenLock = ((PowerManager) getApplicationContext().getSystemService(POWER_SERVICE)).newWakeLock(
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
                screenLock.acquire(); //락풀고
                screenLock.release(); //커밋

                //노티피 다이얼로그 창(생성)
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(BeaconService.this)
                                .setSmallIcon(android.R.mipmap.sym_def_app_icon) //아이콘 바꿀수있음
                                .setContentTitle(coupons.getCouponList().get(0).getComp_org()+"쿠폰이왔쪄염")
                                .setContentText(coupons.getCouponList().get(0).getF_coupon_num())
                                .setAutoCancel(true)
                                .setNumber(counter)
                                .setDefaults(android.app.Notification.DEFAULT_ALL)
                                .setPriority(android.app.Notification.PRIORITY_HIGH);
                mBuilder.setContentIntent(PendingIntentFragment);
                mBuilder.build().flags |= android.app.Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                // Will display the notification in the notification bar
                notificationManager.notify(0, mBuilder.build());
                startActivity(resultIntent);
            }
        } catch (Exception ex) {
            Log.e(TAG + "ShowNo", ex.getMessage());
        }
    }

    //인터넷 연결유무 체크
    public final boolean isInternetOn() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception ex) {
            return false;
        }
    }
    private void initManager() {
        try {
            if (mMinewBeaconManager == null)
                mMinewBeaconManager = MinewBeaconManager.getInstance(BeaconService.this);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }
    List<String> minors = new ArrayList<String>(); /* 마이너 관리 변수 (중복없음) */

    private void callBeaconChk(List<MinewBeacon> beacons)
    {
        if(beacons==null || beacons.size()<=0)return;

        try {
            List<MinewBeacon> uniqueBeacons = new ArrayList<MinewBeacon>();
            //sakhte liste gheyre tekrari az beaconhaye scan shode
            for (int i = 0; i < beacons.size(); i++) {
                if (!minors.contains(beacons.get(i).getMinor())) {
                    minors.add(beacons.get(i).getMinor());
                    uniqueBeacons.add(beacons.get(i));
                }
            }

            for (int i = 0; i < uniqueBeacons.size(); i++) {
                // barresie vaziate internet va farakhanie api baraye beacone peyda shode
                if (isInternetOn()) {
                    //서버로 쿠폰 통신할 아이
                    GetResultForBeacon(uniqueBeacons.get(i).getUuid(), Integer.valueOf(uniqueBeacons.get(i).getMajor()),
                            Integer.valueOf(uniqueBeacons.get(i).getMinor()));
                } else {
                    Toast.makeText(mContext, "인터넷연결이 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private  MinewBeaconManagerListener beaconListner = new MinewBeaconManagerListener()
    {

        @Override
        public void onUpdateBluetoothState(BluetoothState bluetoothState) {
            switch (bluetoothState) {
                case BluetoothStatePowerOff:
                    break;
                case BluetoothStatePowerOn:
                    bindService();
                    break;
            }
        }

        @Override
        public void onRangeBeacons(List<MinewBeacon> list) {
            callBeaconChk(list);
        }

        @Override
        public void onAppearBeacons(List<MinewBeacon> list) {
            Log.d("msg","onAppered");
        }

        @Override
        public void onDisappearBeacons(List<MinewBeacon> beacons) {
            Log.d("msg","onDisppare");

            //거리가 멀어지는 비콘이있을시, 기존에 저장해두었던 마이너와 비교하여, 새로 값을 받을수있도록 마이너값 삭제
            if(beacons!=null && beacons.size()>0 && minors!=null)
            {
                for(int i = 0; i< beacons.size();i++)
                {
                    for(int j = 0; j< minors.size();j++)
                    {
                        if(beacons.get(i).getMinor().equals(minors.get(j)))
                        {
                            minors.remove(j);
                            break;
                        }
                    }
                }
            }
        }
    };

    private void initListener() {
        //scan listener;
        try {
            if (mMinewBeaconManager.getMinewBeaconManagerListener() == null)
                mMinewBeaconManager.setMinewbeaconManagerListener(beaconListner);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bindService();
    }

}

