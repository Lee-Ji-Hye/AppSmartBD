package com.team.smart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.RequestUserVO;
import com.team.smart.vo.UserVO;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private APIInterface apiInterface;

    TextView btnSignIn, btnSingUp, tvUserId, tvUserPw;
    ProgressBar progressbar;
    LinearLayout liMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findId();
        configuListner();

    }

    private void findId() {
        btnSignIn=(TextView)findViewById(R.id.btnSignIn);
        btnSingUp=(TextView)findViewById(R.id.btnSingUp);
        tvUserId=(TextView)findViewById(R.id.tv_userid);
        tvUserPw=(TextView)findViewById(R.id.tv_userpw);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        liMain = (LinearLayout)findViewById(R.id.li_main);

    }

    private void configuListner() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbarShow();//프로그레스바 보임
                boolean error = validationChk(); //회원가입 폼 검증
                if(error) {
                    progressbarHide();//프로그레스바 숨김
                    return;
                }

                callSignInApi();

//                Intent intent =new Intent(SignInActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    protected boolean validationChk() {
        boolean error = false;

        if(tvUserId.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"아이디를 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvUserId.requestFocus();

        } else if(tvUserPw.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvUserPw.requestFocus();
        }
        return error;
    }

    //로그인 통신
    protected void callSignInApi() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        RequestUserVO vo = new RequestUserVO();
        vo.setUserid(tvUserId.getText().toString());
        vo.setUserpw(tvUserPw.getText().toString());

        //로그인 통신
        Call<UserVO> call = apiInterface.memberSignIn(vo);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                Log.d("TAG",response.code()+"");
                progressbarHide();//프로그레스바 숨김

                if(response.code()==200) {
                    UserVO resource = response.body();

                    String responseCode = String.valueOf(resource.getResponseCode());
                    String responseMsg = resource.getResponseMsg();

                    if(!responseCode.equals("400")) {
                        Toast toast = Toast.makeText(getApplicationContext(),responseMsg, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        UserVO.User data = resource.getUser().get(0);
                        Gson gson3 = new Gson();
                        String json3 = gson3.toJson(data);
                        Log.d("로그인 통신~~~~",json3);

                        //로그인정보 저장 (쿠키개념)
                        SPUtil.setUserId(SignInActivity.this, data.getUserid());
                        SPUtil.setUserName(SignInActivity.this, data.getName());
                        SPUtil.setUserHP(SignInActivity.this, data.getHp());
                        SPUtil.setUserEmail(SignInActivity.this, data.getEmail());

                        //참고사이트 : https://arabiannight.tistory.com/entry/286
                        //FLAG_ACTIVITY_CLEAR_TOP: 루트액티비티와 중복된 액티비티만 남고 그게 아니면 액티비티가 디스트로이됨
                        Intent intent =new Intent(SignInActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                Log.d("로그인 통신 fail...", t.getMessage());
                call.cancel();
                progressbarHide();//프로그레스바 숨김
            }
        });
    }

    private void progressbarShow() {
        int gray = Color.parseColor("#BDBDBD");
        liMain.setBackgroundColor(gray);
        progressbar.setVisibility(View.VISIBLE);//프로그레스바 보임
    }

    private void progressbarHide() {
        int white = Color.parseColor("#FFFFFF");
        liMain.setBackgroundColor(white);
        progressbar.setVisibility(View.GONE);//프로그레스바 숨김
    }
}
