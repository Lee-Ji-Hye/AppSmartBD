package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.team.smart.R;
import com.team.smart.util.SPUtil;


public abstract class HeaderActivity extends AppCompatActivity {

    abstract void findid();
    abstract void configuListner();
    abstract void init();

    protected int reqCode = 1000;
    protected LinearLayout layGuestTitle, layUserTitle;
    protected FrameLayout mBody;
    protected ImageButton btnOpenDrawer, btSearchBtn;
    protected Button btnCloseDrawer, btnSignIn, btnSingUp, btnSignOut;
    protected TextView userIdView, mypageBtn, foodOrderBtn;

    protected DrawerLayout drawerLayout;
    protected View drawerView;

    //유저 정보
    String userid, username;

    //부모 layout 안에 자식 layout생성
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);//부모(헤더)로 사용할 layout 불러옴
        userChk();
        findHeaderid();      //불러온 부모(헤더)layout의 요소들 선언해놓기
        UI_Load();
        commonClickListner();//불러온 부모(헤더) 클릭리스너 달기

        init();//초기화
    }

    private void userChk() {
        userid = SPUtil.getUserId(HeaderActivity.this);
        username = SPUtil.getUserName(HeaderActivity.this);
    }

    private void findHeaderid() {

        mBody = (FrameLayout)findViewById(R.id.childFrame);//자식 프레임

        layGuestTitle = (LinearLayout) findViewById(R.id.lay_guestTitle);
        layUserTitle = (LinearLayout) findViewById(R.id.lay_userTitle);
        userIdView = findViewById(R.id.userIdView);
        mypageBtn = findViewById(R.id.mypageBtn);
        btnSignOut = findViewById(R.id.btnSignOut);


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

        foodOrderBtn = findViewById(R.id.foodOrderBtn);
    }

    private void UI_Load() {
        if(!userid.equals("")) {
            //로그인 OK
            userIdView.setText(username + "님");
            layUserTitle.setVisibility(View.VISIBLE); //oo님 환영합니다. show
            layGuestTitle.setVisibility(View.GONE); //로그인을하세요 hide
            mypageBtn.setVisibility(View.VISIBLE); //마이페이지 show

            btnSignIn.setVisibility(View.GONE); //로그인 hide
            btnSingUp.setVisibility(View.GONE); //회원가입hide
            btnSignOut.setVisibility(View.VISIBLE); //로그아웃 show
        } else {
            //로그인 NO
            layUserTitle.setVisibility(View.GONE); //oo님 환영합니다. hide
            layGuestTitle.setVisibility(View.VISIBLE); //로그인을하세요 show
            mypageBtn.setVisibility(View.GONE); //마이페이지 hide

            btnSignIn.setVisibility(View.VISIBLE); //로그인 show
            btnSingUp.setVisibility(View.VISIBLE); //회원가입 show
            btnSignOut.setVisibility(View.GONE); //로그아웃 hide

        }

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

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그아웃 버튼 눌림", Toast.LENGTH_LONG).show();

                SPUtil.removeAllPreferences(HeaderActivity.this);//로그인정보 제거

                //참고사이트 : https://arabiannight.tistory.com/entry/286
                //FLAG_ACTIVITY_CLEAR_TOP: 루트액티비티와 중복된 액티비티만 남고 그게 아니면 액티비티가 디스트로이됨
                Intent intent =new Intent(HeaderActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        foodOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userid.equals("")) {
                    Intent intent =new Intent(HeaderActivity.this, FoodOrderChkActivity.class); //비회원 주문조회 페이지
                    startActivity(intent);
                } else {
                    Intent intent =new Intent(HeaderActivity.this, FoodOrderListActivity.class); //주문리스트 페이지
                    startActivity(intent);
                }

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
