package com.team.smart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * @author jihye
 * 앱 실행시 대기화면
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Thread.sleep(2000); //대기 초 설정

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
