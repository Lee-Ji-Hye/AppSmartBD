package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.team.smart.R;


public abstract class HeaderActivity extends AppCompatActivity {

    abstract void findid();
    abstract void configuListner();
    abstract void init();

    protected int reqCode = 1000;

    protected FrameLayout mBody;
    protected ImageButton btnOpenDrawer, btSearchBtn;
    protected Button btnCloseDrawer, btnSignIn, btnSingUp;

    protected DrawerLayout drawerLayout;
    protected View drawerView;

    //부모 layout 안에 자식 layout생성
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);//부모(헤더)로 사용할 layout 불러옴
        findHeaderid();      //불러온 부모(헤더)layout의 요소들 선언해놓기
        commonClickListner();//불러온 부모(헤더) 클릭리스너 달기

        init();//초기화
    }


    private void findHeaderid() {

        mBody = (FrameLayout)findViewById(R.id.childFrame);//자식 프레임
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSingUp = findViewById(R.id.btnSingUp);
        //searchbtn
        btSearchBtn = findViewById(R.id.bt_searchbtn);
        // Drawer 화면을 열고 닫을 버튼 객체 참조
        btnOpenDrawer = findViewById(R.id.btn_OpenDrawer);
        btnCloseDrawer =findViewById(R.id.btn_CloseDrawer);

        // 전체 화면인 DrawerLayout 객체 참조
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        drawerView = (View) findViewById(R.id.drawer);
    }


    private void commonClickListner() {
        // 왼쪽 상단 메뉴버튼 시작-----------------------
        // Drawer 여는 버튼 리스너
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        // Drawer 닫는 버튼 리스너
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });
        // 왼쪽 상단 메뉴버튼 종료------------------------

        //메뉴 내부 시작---------------------------------
        //내정보로 이동
        Button mypageBtn = findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), parkingmypageActivity.class); //parkingmypageActivity 이동
                startActivity(intent);
            }
        });
        //슬라이딩페이지 홈으로 버튼 클릭시 메인페이지로 이동
        TextView homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //parkingsearchActivity 이동
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그인 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class); // LoginActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "회원가입 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class); // SignUpActivity 이동할 준비
                startActivityForResult(intent,reqCode);
            }
        });
        //메뉴 내부 종료---------------------------------
    }

    //메뉴 버튼 show or hide
    protected void setMenuDisplay(boolean state) {
        if(!state) {
            btnOpenDrawer.setVisibility(View.GONE);
        } else {
            btnOpenDrawer.setVisibility(View.VISIBLE);
        }
    }

    //검색 버튼 show or hide
    protected void setSearchDisplay(boolean state) {
        if(!state) {
            btSearchBtn.setVisibility(View.GONE);
        } else {
            btSearchBtn.setVisibility(View.VISIBLE);
        }
    }

    //헤더 타이틀 셋팅
    protected void setTitle(String titleTxt) {
        TextView tvHeaderTtitle = findViewById(R.id.tv_headerTitle);
        tvHeaderTtitle.setText(titleTxt);
    }

    protected View setView(int layoutid) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(layoutid, null, false);
        mBody.addView(itemView);
        return itemView;
    }
}
