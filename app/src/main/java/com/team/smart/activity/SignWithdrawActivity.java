package com.team.smart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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

public class SignWithdrawActivity extends AppCompatActivity {
    private APIInterface apiInterface;

    ImageButton backBtn;
    TextView btnOut;
    TextView tvUserId, tvUserPw;
    ProgressBar progressbar;
    LinearLayout liMain;

    String userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_withraw);

        userid = SPUtil.getUserId(SignWithdrawActivity.this);

        findId();
        configuListner();
    }

    private void findId() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        btnOut = findViewById(R.id.btnOut);
        tvUserId = findViewById(R.id.tv_userid);
        tvUserPw = findViewById(R.id.tv_userpw);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        liMain = (LinearLayout)findViewById(R.id.li_main);

        tvUserId.setEnabled(false); //아이디 입력 비활성화
        tvUserId.setText(userid);   //아이디 셋팅

        tvUserPw.requestFocus();
    }

    private void configuListner() {
        /* 뒤로가기 버튼 */
        backBtn.setOnClickListener(view -> {
            finish();
        });

        //탈퇴하기 버튼
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //프로그레스바 보이기
                progressbarShow();

                boolean error = validationChk(); //내정보 수정하기 폼 검증
                if(error) {
                    progressbarHide();//프로그레스바 숨김
                    return;
                }

                showAlert(); //정말 탈퇴할건지 물어봄 & 탈퇴 통신

            }
        });
    }

    private void showAlert()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SignWithdrawActivity.this);
        alertDialogBuilder.setTitle("탈퇴 서비스");
        // 다이얼로그 생성
        alertDialogBuilder
                .setMessage("정말 탈퇴하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                //탈퇴 통신
                                callWithrawApi();
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                progressbarHide();//프로그레스바 숨김
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
            tvUserPw.setHintTextColor(Color.parseColor("#FF0000"));
            tvUserPw.setHint("정보 변경을 위해 비밀번호 입력");
            tvUserPw.requestFocus();

        }
        return error;
    }

    //회원탈퇴 통신
    private void callWithrawApi() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("userpw", tvUserPw.getText().toString());

        //비밀번호 변경 통신
        Call<Map<String, Object>> call = apiInterface.modifyUserWithdraw(map);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {

                    progressbarHide();//프로그레스바 숨김

                    Map<String, Object> data = response.body();

                    String responseCode = String.valueOf(data.get("responseCode"));
                    String responseMsg = data.get("responseMsg").toString();

                    if(!responseCode.equals("410")) {
                        if(responseCode.equals("405")) {
                            tvUserPw.setText("");
                            tvUserPw.setHintTextColor(Color.parseColor("#FF0000"));
                            tvUserPw.setHint("비밀번호가 일치하지 않습니다.");
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), responseMsg, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        return;

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),"탈퇴 완료. 이용해주셔서 감사합니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //로그아웃 처리
                        SPUtil.removeAllPreferences(SignWithdrawActivity.this);

                        Intent intent =new Intent(SignWithdrawActivity.this, MainActivity.class);
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
}
