package com.team.smart.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodOrderVO;

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

    private APIInterface apiInterface;

    public KakaoWebViewClient(Activity activity, String f_ocode) {
        this.activity = activity;
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

                        Gson gson3 = new Gson();
                        String json3 = gson3.toJson(resource);

                        view.destroy();

                    }
                }

                @Override
                public void onFailure(Call<HashMap> call, Throwable t) {
                    Log.d("카카오 success 통신 fail~~~.", "실패..");
                    call.cancel();
                }
            });
        return null;
    }
}