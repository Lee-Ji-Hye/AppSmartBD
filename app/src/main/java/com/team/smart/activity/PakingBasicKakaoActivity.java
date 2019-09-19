package com.team.smart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.ParkingBasicOrderVO;
import com.team.smart.vo.ParkingOrderVO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PakingBasicKakaoActivity extends Activity {
    ParkingBasicOrderVO orderVO;
    private APIInterface apiInterface;
    private WebView webview;
    private Activity kakaoActivity;
    private final String APP_SCHEME = "kakaotalk://";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paking_basic_kakao);
        kakaoActivity = this;
        //인텐트 값 받음
        Intent intent =  getIntent();
        orderVO=(ParkingBasicOrderVO)intent.getSerializableExtra("orderVO");
        //Toast.makeText(getApplicationContext(), orderVO.getP_code()+"구매요청", Toast.LENGTH_SHORT).show();
        callApi();//카카오페이 결제요청 통신
        Log.d("test" , "00000000000");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("뉴인텐트 ::", "뉴인텐트 들어옴");
        setIntent(intent);
    }

    //카카오페이 결제요청 통신
    protected void callApi() {

        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(orderVO);
        Log.d("여기에도 오는지~~~>", json3);
        //통신
        Call<HashMap> call = apiInterface.ParkingPayBasic(orderVO);
        call.enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                Log.d("test" , "1.5");
                //Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    HashMap resource = response.body(); //결제요청 응답
                    String json3 = gson3.toJson(response);
                    Log.d("결제 요청 정보~~~>", json3);
                    String p_ocode = resource.get("pay_seq").toString();
                    String tid = resource.get("tid").toString();

                    //응답으로 받은 다음 진행 정보를 웹뷰로 띄워주기
                    webview = (WebView)findViewById(R.id.webView);
                    webview.setWebViewClient(new KakaoWebViewClient(kakaoActivity, "parkingBasic",  p_ocode));
                    WebSettings set = webview.getSettings();
                    set.setJavaScriptEnabled(true); //스크립트 허용
                    set.setBuiltInZoomControls(true);//줌허용(?)

                    Log.d("결제 요청 정보~~~>", (String) resource.get("next_redirect_app_url"));
                    webview.loadUrl((String) resource.get("next_redirect_app_url")); //https://mockup-pg-web.kakao.com/v1/267b306c4ec2efb1c89517f80c05~~~~

                }
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {
                Log.d("카카오페이통신 fail~~~...", "실패..");
                call.cancel();
            }
        });
    }
}
