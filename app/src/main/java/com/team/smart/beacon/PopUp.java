package com.team.smart.beacon;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.team.smart.R;
import com.team.smart.activity.MainActivity;


/**
 * Created by Niloofar on 2/26/2017.
 */

public class PopUp extends Activity {
    private TextView mTxtAlertTitle;
    private TextView mTxtAlertContent;
    private Button mBtnAlertClose;
    private Button mBtnAlertOk;
    private PowerManager mPowerManager = null;
    private boolean mIsScreenOn = false;
    public static final String ACTION_NOTIFY = "ACTION_NOTIFY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_push);

        findViewByID();
        init();
        listener();
    }

    private void findViewByID() {
        mTxtAlertTitle = (TextView) findViewById(R.id.txtAlertTitle);
        mTxtAlertContent = (TextView) findViewById(R.id.txtAlertContent);
        mBtnAlertClose = (Button) findViewById(R.id.btnAlertCancel);
        mBtnAlertOk = (Button) findViewById(R.id.btnAlertOk);
    }

    private void init() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            mIsScreenOn = mPowerManager.isScreenOn();
        } else {
            mIsScreenOn = mPowerManager.isInteractive();
        }
        if (!mIsScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    // 키잠금 해제하기
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    // 화면 켜기
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        mTxtAlertTitle.setText(title);
        mTxtAlertContent.setText(content);
    }

    private void listener() {
        mBtnAlertOk.setOnClickListener(v->{
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(getIntent().getIntExtra("NOTIFICATION_ID", 0));
            startApp(getIntent());
            finish();
        });
        mBtnAlertClose.setOnClickListener(v->{
            finish();
        });
    }

    private void startApp(Intent intent) {
        intent.setAction(ACTION_NOTIFY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);

    }

}
