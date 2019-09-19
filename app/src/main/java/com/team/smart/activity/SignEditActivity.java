package com.team.smart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.FoodOrderListAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.FoodOrderListVO;
import com.team.smart.vo.RequestUserVO;
import com.team.smart.vo.UserVO;

import org.web3j.crypto.Sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignEditActivity extends AppCompatActivity {
    private APIInterface apiInterface;
    ImageButton backBtn;
    TextView btnEdit;
    EditText tvUserId, tvUserPw, tvName, tvHp, tvEmail;
    ProgressBar progressbar;
    LinearLayout liMain;

    String userid,username,userhp,useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_edit);

        userid = SPUtil.getUserId(SignEditActivity.this);
        username = SPUtil.getUserName(SignEditActivity.this);
        userhp = SPUtil.getUserHP(SignEditActivity.this);
        useremail = SPUtil.getUserEmail(SignEditActivity.this);

        findId();
        configuListner();//클릭이벤트함수

    }

    protected void findId() {
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        btnEdit = findViewById(R.id.btnEdit); //회원가입버튼
        tvUserId = findViewById(R.id.tv_userid);
        tvUserPw = findViewById(R.id.tv_userpw);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvHp = findViewById(R.id.tv_hp);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        liMain = (LinearLayout)findViewById(R.id.li_main);

        //기본세팅
        tvHp.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); //숫자 키패드
        tvEmail.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); //키패드에 @ 보이게

        tvUserId.setEnabled(false); //아이디 입력 비활성화
        tvUserId.setText(userid);   //아이디 셋팅
        tvName.setText(username);
        tvHp.setText(userhp);
        tvEmail.setText(useremail);

        tvUserPw.requestFocus();
    }

    protected void configuListner() {
        /* 뒤로가기 버튼 */
        backBtn.setOnClickListener(view -> {
            finish();
        });

        //정보 수정하기 버튼
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbarShow();//프로그레스바 보임
                boolean error = validationChk(); //내정보 수정하기 폼 검증
                if(error) {
                    progressbarHide();//프로그레스바 숨김
                    return;
                }
                callSignEditApi();
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
            tvUserPw.setHintTextColor(Color.parseColor("#FF0000"));
            tvUserPw.setHint("정보 변경을 위해 비밀번호 입력");
            tvUserPw.requestFocus();

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
    protected void callSignEditApi() {
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
        Call<Map<String, Object>> call = apiInterface.modifyUserInfo(vo);
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.d("TAG",response.code()+"");

                progressbarHide();//프로그레스바 숨김

                if(response.code()==200) {
                    Map<String, Object> data = response.body();

                    String responseCode = String.valueOf(data.get("responseCode"));
                    String responseMsg = data.get("responseMsg").toString();

                    if(!responseCode.equals("200")) {
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
                        Toast toast = Toast.makeText(getApplicationContext(),"정보가 수정되었습니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        SPUtil.removeAllPreferences(SignEditActivity.this);

                        //수정정보 저장 (쿠키개념)
                        SPUtil.setUserId(SignEditActivity.this, tvUserId.getText().toString());
                        SPUtil.setUserName(SignEditActivity.this, tvName.getText().toString());
                        SPUtil.setUserHP(SignEditActivity.this, tvHp.getText().toString());
                        SPUtil.setUserEmail(SignEditActivity.this, tvEmail.getText().toString());

                        Intent intent =new Intent(SignEditActivity.this, parkingmypageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("회원가입 통신 fail...", "실패..");
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
