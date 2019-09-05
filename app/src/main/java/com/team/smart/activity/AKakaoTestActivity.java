package com.team.smart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodCartVO;
import com.team.smart.vo.FoodOrderVO;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AKakaoTestActivity extends Activity {
    private APIInterface apiInterface;
    private WebView webview;
    private Activity kakaoActivity;
    private final String APP_SCHEME = "kakaotalk://";

    FoodOrderVO orderInfoVO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akakaopay);

        kakaoActivity = this;

        //인텐트 값 받음
        Intent intent = getIntent();
        ArrayList<FoodCartVO> foodVo = (ArrayList<FoodCartVO>) intent.getSerializableExtra("orderMenuList");//orderInfo
        orderInfoVO =  (FoodOrderVO) intent.getSerializableExtra("orderInfo");
        orderInfoVO.setMenulist(foodVo);

        callApi();//카카오페이 결제요청 통신
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("뉴인텐트 ::", "뉴인텐트 들어옴");
        setIntent(intent);
    }


    protected ApprovePayResponse approvePayResponse;
    interface ApprovePayResponse {
        void  success(String theme, HashMap response);//theme = food, parking, room.
        void  failed(String theme, String message);
    };


    //카카오페이 결제요청 통신
    protected void callApi() {

        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        //통신
        Call<HashMap> call = apiInterface.kakaoPayTest(orderInfoVO);
        call.enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                //Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    HashMap resource = response.body(); //결제요청 응답

                    String f_ocode = resource.get("f_ocode").toString();
                    String tid = resource.get("tid").toString();

                    //응답으로 받은 다음 진행 정보를 웹뷰로 띄워주기
                    webview = (WebView)findViewById(R.id.webView);
                    webview.setWebViewClient(new KakaoWebViewClient(kakaoActivity, "food", f_ocode));
                    WebSettings set = webview.getSettings();
                    set.setJavaScriptEnabled(true); //스크립트 허용
                    set.setBuiltInZoomControls(true);//줌허용(?)

                    webview.loadUrl((String) resource.get("next_redirect_app_url")); //https://mockup-pg-web.kakao.com/v1/267b306c4ec2efb1c89517f80c05~~~~


                }
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {
                Log.d("스토어 통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }



//    class WebClient extends WebViewClient {
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }




}