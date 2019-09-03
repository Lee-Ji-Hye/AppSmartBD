package com.team.smart.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Toast;


import com.google.gson.Gson;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodOrderVO;


import org.web3j.crypto.Hash;


import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoWebViewClient extends WebViewClient {
    private Activity activity;
    private String f_ocode;

    private String theme;

    private APIInterface apiInterface;

    public KakaoWebViewClient(Activity activity, String theme, String f_ocode) {
        this.activity = activity;
        this.theme = theme;

        this.f_ocode = f_ocode;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Log.d("KakaoWebViewClient1 : ", url);

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent = null;

            try {

                Log.d("카카오페이 결제요청 영역: ", url);

                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                Uri uri = Uri.parse(intent.getDataString());

                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));

                return true;
            }
            catch (URISyntaxException ex) {
                Log.d("ERR 발생: ", "URISyntaxException ");
                return false;
            }
            catch (ActivityNotFoundException e) {
                Log.d("ERR 발생: ", "ctivityNotFoundException");
                if ( intent == null )   return false;

                String packageName = intent.getPackage(); //packageName should be com.kakao.talk
                if (packageName != null) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    return true;
                }

                return false;
            }
        } else {
            Log.d("카카오페이 결제승인 영역: ", url);
            //결제승인 통신

            successCallAPI(view, url);

        }
        return false;
    }


    public HashMap successCallAPI(WebView view, String reUrl) {
            if(apiInterface == null) {
                apiInterface = APIClient.getClient().create(APIInterface.class);
            }

        try {
            reUrl = URLDecoder.decode(reUrl, "utf-8");
            reUrl = reUrl.replace("localhost:8089", "192.168.123.8:8089");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //통신

            Call<HashMap> call = apiInterface.kakaoPaySuccess(reUrl, f_ocode);
            call.enqueue(new Callback<HashMap>() {
                @Override
                public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                    Log.d("TAG",response.code()+"");
                    if(response.code()==200) {
                        HashMap resource = response.body();

                        view.destroy();//카카오 웹뷰 종료
                        GoMyOrderPage(resource);//마이페이지 이동
                    }
                }

                @Override
                public void onFailure(Call<HashMap> call, Throwable t) {
                    Log.d("카카오페이 통신 fail~~~.", "결제승인 실패..");

                    call.cancel();
                }
            });
        return null;
    }

    private void GoMyOrderPage(HashMap response) {
        Intent myPageintent = null;

        //theme는 멤버변수에 있음
        if(theme.equals("food")) {
            myPageintent = new Intent(activity, FoodOrderComplete.class);
            myPageintent.putExtra("f_ocode", response.get("partner_order_id").toString());

        } else if(theme.equals("parking")) {
            //myPageintent = new Intent(activity, ParkingOrderComplete.class);
            //myPageintent.putExtra("parking_code", response.get("partner_order_id").toString());
        } else if(theme.equals("rental")) {
            //myPageintent = new Intent(activity, RentalOrderComplete.class);
            //myPageintent.putExtra("rt_code", response.get("partner_order_id").toString());
        } else {
            myPageintent = new Intent();
        }

        activity.startActivity(myPageintent);
        activity.finish(); //카카오 액티비티 종료
    }

}