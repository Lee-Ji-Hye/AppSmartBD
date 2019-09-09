package com.team.smart.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;


public abstract class HeaderActivity_old extends AppCompatActivity {

    abstract void findid();
    abstract void configuListner();
    abstract void init();

    protected FrameLayout mBody;
    protected ImageButton btnMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_old);

        findHeaderid();
        init();
    }
    private void findHeaderid() {
        mBody = (FrameLayout)findViewById(R.id.child);
        btnMenu =(ImageButton)findViewById(R.id.btnmenu);
        btnMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    protected void setMenuBtn(boolean state) {
        if(!state) {
            btnMenu.setVisibility(View.GONE);
        } else {
            btnMenu.setVisibility(View.VISIBLE);
        }
    }

    protected View setView(int layoutid) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(layoutid, null, false);
        mBody.addView(itemView);
        return itemView;
    }
}
