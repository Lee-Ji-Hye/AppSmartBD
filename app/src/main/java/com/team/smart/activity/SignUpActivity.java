package com.team.smart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.RequestUserVO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private APIInterface apiInterface;

    ProgressBar progressbar;
    LinearLayout liMain, liEdit;
    TextView btnSignIn;
    EditText tvUserId, tvUserPw, tvUserPwRe, tvName, tvHp, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findId();
        configuListner();//클릭이벤트함수

    }

    protected void findId() {
        btnSignIn = findViewById(R.id.btnSignIn); //회원가입버튼
        tvUserId = findViewById(R.id.tv_userid);
        tvUserPw = findViewById(R.id.tv_userpw);
        tvUserPwRe = findViewById(R.id.tv_userpw_re);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvHp = findViewById(R.id.tv_hp);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        liMain = (LinearLayout)findViewById(R.id.li_main);
        liEdit = (LinearLayout)findViewById(R.id.li_edit);

        //기본세팅
        tvHp.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); //숫자 키패드
        tvEmail.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); //키패드에 @ 보이게
    }

    protected void configuListner() {
        //회원가입하기 버튼
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbarShow();//프로그레스바 보임
                boolean error = validationChk(); //회원가입 폼 검증
                if(error) {
                    progressbarHide();//프로그레스바 숨김
                    return;
                }

                callSignUpApi();

//                Intent intent =new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(intent);
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

        } else if(tvUserPwRe.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"비밀번호확인을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvUserPwRe.requestFocus();

        } else if(tvName.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"이름을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvName.requestFocus();

        } else if(tvEmail.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"이메일을 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvEmail.requestFocus();

        } else if(tvHp.getText().toString().equals("")) {
            error = true;
            Toast toast = Toast.makeText(getApplicationContext(),"휴대번호를 입력하세요", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            tvHp.requestFocus();

        }
        return error;
    }

    //회원가입 통신
    protected void callSignUpApi() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        RequestUserVO vo = new RequestUserVO();
        vo.setUserid(tvUserId.getText().toString());
        vo.setUserpw(tvUserPw.getText().toString());
        vo.setName(tvName.getText().toString());
        vo.setEmail(tvEmail.getText().toString());
        vo.setHp(tvHp.getText().toString());

        //회원가입 통신
        Call<Map<String, String>> call = apiInterface.memberSignUp(vo);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                Log.d("TAG",response.code()+"");
                progressbarHide();//프로그레스바 숨김

                if(response.code()==200) {
                    Map<String, String> data = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(data);
                    Log.d("회원가입 통신~~~~",json3);

                    String responseCode = data.get("responseCode");
                    String responseMsg = data.get("responseMsg");

                    if(!responseCode.equals("300")) {
                        Toast toast = Toast.makeText(getApplicationContext(),responseMsg, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                       return;

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),"회원가입 성공!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //참고사이트 : https://arabiannight.tistory.com/entry/286
                        //FLAG_ACTIVITY_CLEAR_TOP: 루트액티비티와 중복된 액티비티만 남고 그게 아니면 액티비티가 디스트로이됨
                        Intent intent =new Intent(SignUpActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.d("회원가입 통신 fail...", "실패..");
                call.cancel();
                progressbarHide();//프로그레스바 숨김
            }
        });
    }

    private void progressbarShow() {
        int gray = Color.parseColor("#BDBDBD");
        liMain.setBackgroundColor(gray);
        liEdit.setVisibility(View.GONE);//입력폼 숨김
        progressbar.setVisibility(View.VISIBLE);//프로그레스바 보임
    }

    private void progressbarHide() {
        int white = Color.parseColor("#FFFFFF");
        liMain.setBackgroundColor(white);
        liEdit.setVisibility(View.VISIBLE);//입력폼 보임
        progressbar.setVisibility(View.GONE);//프로그레스바 숨김
    }
}
