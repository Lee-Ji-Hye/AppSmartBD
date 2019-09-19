package com.team.smart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignEditPwdActivity extends AppCompatActivity {
    private APIInterface apiInterface;

    ImageButton backBtn;
    TextView tvUserPw,tvNewPwd,tvNewPwdRe,btnEdit;
    ProgressBar progressbar;
    LinearLayout liMain;

    String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit);

        userid = SPUtil.getUserId(SignEditPwdActivity.this);
        //로그인한 사람이 아니면
        if(userid.equals("")) {
            Toast.makeText(getApplicationContext(), "로그인 후 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(SignEditPwdActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        findId();
        configuListner();
    }

    private void findId() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        tvUserPw = findViewById(R.id.tv_userpw);
        tvNewPwd = findViewById(R.id.tv_new_pwd);
        tvNewPwdRe = findViewById(R.id.tv_new_pwd_re);
        btnEdit = findViewById(R.id.btnEdit);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        liMain = (LinearLayout)findViewById(R.id.li_main);

        tvUserPw.requestFocus();//현재 비밀번호 포커스
    }

    private void configuListner() {
        /* 뒤로가기 버튼 */
        backBtn.setOnClickListener(view -> {
            finish();
        });

        //비밀번호 변경 버튼
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프로그레스바 보이기
                progressbarShow();

                boolean error = validationChk(); //내정보 수정하기 폼 검증
                if(error) {
                    progressbarHide();//프로그레스바 숨김
                    return;
                }
                callPwdEditApi();
            }
        });
    }

    private void callPwdEditApi() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("userpw", tvUserPw.getText().toString());
        map.put("userpw_new", tvNewPwd.getText().toString());

        //비밀번호 변경 통신
        Call<Map<String, Object>> call = apiInterface.modifyUserPwd(map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {

                    progressbarHide();//프로그레스바 숨김

                    Map<String, Object> data = response.body();

                    String responseCode = String.valueOf(data.get("responseCode"));
                    String responseMsg = data.get("responseMsg").toString();

                    if(!responseCode.equals("200")) {
                        if(responseCode.equals("405")) {
                            tvUserPw.setText("");
                            tvUserPw.setHintTextColor(Color.parseColor("#FF0000"));
                            tvUserPw.setHint("현재 비밀번호가 일치하지 않습니다.");
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), responseMsg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        return;

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),"비밀번호 변경 성공!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent intent =new Intent(SignEditPwdActivity.this, parkingmypageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("비번변경 통신 fail...", "실패..");
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

    protected boolean validationChk() {
        boolean error = false;

        if(tvUserPw.getText().toString().equals("")) {
            error = true;
            tvUserPw.setText("");
            tvUserPw.setHintTextColor(Color.parseColor("#FF0000"));
            tvUserPw.setHint("현재 비밀번호를 입력하세요");
            tvUserPw.requestFocus();

        } else if(tvNewPwd.getText().toString().equals("")) {
            error = true;
            tvNewPwd.setText("");
            tvNewPwd.setHintTextColor(Color.parseColor("#FF0000"));
            tvNewPwd.setHint("새 비밀번호를 입력하세요");
            tvNewPwd.requestFocus();

        } else if(tvNewPwdRe.getText().toString().equals("")) {
            error = true;
            tvNewPwd.setText("");
            tvNewPwd.setHintTextColor(Color.parseColor("#FF0000"));
            tvNewPwd.setHint("새 비밀번호를 재입력하세요");
            tvNewPwd.requestFocus();
        } else if(!tvNewPwd.getText().toString().equals(tvNewPwdRe.getText().toString())) {
            error = true;
            tvNewPwdRe.setText("");
            tvNewPwdRe.setHintTextColor(Color.parseColor("#FF0000"));
            tvNewPwdRe.setHint("새 비밀번호 정보가 일치하지 않습니다.");
            tvNewPwdRe.requestFocus();
        }

        return error;
    }
}
