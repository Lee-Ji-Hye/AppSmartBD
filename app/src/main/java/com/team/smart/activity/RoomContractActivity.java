package com.team.smart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.blockchain.Wallet;
import com.team.smart.blockchain.Web3jAPI;
import com.team.smart.blockchain.config.Web3jUtil;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.RoomContractVO;
import com.team.smart.vo.WalletVO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomContractActivity extends AppCompatActivity implements
        View.OnClickListener {

    private APIInterface apiInterface;

    private SpotsDialog mDialog;

    private List<String> names = new ArrayList<>();
    private List<String> passwords = new ArrayList<>();
    private List<String> address = new ArrayList<>();
    private List<String> filepaths = new ArrayList<>();

    private String b_area1,b_area2,b_address,b_year,b_landarea,b_buildarea,b_buildscale
                  ,r_code,r_price,r_deposit,userid,r_able_date
                  ,name,email,hp;
    String tname, taddress, userId, rt_hash;
    private int r_blockCode;

    Calendar cal;

    int r_total;

    TextView tv_b_address,tv_b_year,tv_b_landarea,tv_b_buildscale,tv_b_buildarea
            ,tv_r_price2,tv_r_price3,tv_r_deposit3,tv_r_deposit4,tv_total_price
            ,walletName,walletAddress
            ,tv_name,tv_comp_seq,tv_hp,tv_email;

    EditText et_rt_name, et_rt_businessNum,et_rt_mobile,et_rt_email,et_rt_able_date;

    final Context context = this;
    private Button finishContract, cancelContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract);

        findid();
        initData();

        userId = SPUtil.getUserId(RoomContractActivity.this);

        finishContract = findViewById(R.id.finishContract);
        cancelContract = findViewById(R.id.cancelContract);

        finishContract.setOnClickListener(this);
        cancelContract.setOnClickListener(this);
    }

    public void initData(){
        Wallet wallet = Wallet.getInstance();
        String filepath = getFilesDir()+"/keystore";
        try {
            wallet.getLists(filepath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        names = wallet.getNames();
        passwords =wallet.getPasswords();
        address = wallet.getAddresses();
        filepaths = wallet.getFilepaths();
    }

    private void findid() {
        Intent intent = getIntent(); //데이터 수신
        b_area1 = intent.getExtras().getString("b_area1");
        b_area2 = intent.getExtras().getString("b_area2");
        b_address = intent.getExtras().getString("b_address");
        b_year = intent.getExtras().getString("b_year");
        b_landarea = intent.getExtras().getString("b_landarea");
        b_buildarea = intent.getExtras().getString("b_buildarea");
        b_buildscale = intent.getExtras().getString("b_buildscale");

        r_code = intent.getExtras().getString("r_code");
        r_blockCode = intent.getExtras().getInt("r_blockCode");
        System.out.println("블록코드~~~~~"+r_blockCode);
        r_price = intent.getExtras().getString("r_price");
        r_able_date = intent.getExtras().getString("r_able_date");
        r_deposit = intent.getExtras().getString("r_deposit");
        userid = intent.getExtras().getString("userid");

        name = intent.getExtras().getString("name");
        email = intent.getExtras().getString("email");
        hp = intent.getExtras().getString("hp");

        //find id
        tv_b_address = findViewById(R.id.tv_b_address);
        tv_b_year = findViewById(R.id.tv_b_year);
        tv_b_landarea = findViewById(R.id.tv_b_landarea);
        tv_b_buildscale = findViewById(R.id.tv_b_buildscale);
        tv_b_buildarea = findViewById(R.id.tv_b_buildarea);

        tv_r_price2 = findViewById(R.id.tv_r_price2);
        tv_r_deposit3 = findViewById(R.id.tv_r_deposit3);
        tv_r_price3 = findViewById(R.id.tv_r_price3);
        tv_r_deposit4 = findViewById(R.id.tv_r_deposit4);
        tv_total_price = findViewById(R.id.tv_total_price);

        walletName = findViewById(R.id.walletName);
        walletAddress = findViewById(R.id.walletAddress);

        tv_name = findViewById(R.id.tv_name);
        tv_comp_seq = findViewById(R.id.tv_comp_seq);
        tv_hp = findViewById(R.id.tv_hp);
        tv_email = findViewById(R.id.tv_email);

        et_rt_name = findViewById(R.id.rt_name);
        et_rt_businessNum = findViewById(R.id.rt_businessNum);
        et_rt_mobile = findViewById(R.id.rt_mobile);
        et_rt_email = findViewById(R.id.rt_email);
        et_rt_able_date = findViewById(R.id.rt_able_date);

        //find id -> text setting
        tv_b_address.setText(b_area1+" "+b_area2+" "+b_address);
        tv_b_year.setText(b_year);
        tv_b_landarea.setText(b_landarea);
        tv_b_buildscale.setText(b_buildscale);
        tv_b_buildarea.setText(b_buildarea);

        tv_r_price2.setText(r_price);
        tv_r_price3.setText(r_price);
        tv_r_deposit3.setText(r_deposit);
        tv_r_deposit4.setText(r_deposit);
        r_total = Integer.parseInt(r_price) + Integer.parseInt(r_deposit);
        tv_total_price.setText(String.valueOf(r_total));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, Integer.valueOf(r_able_date));

        // 특정 형태의 날짜로 값을 뽑기
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = df.format(cal.getTime());
        System.err.println(strDate);

        et_rt_able_date.setText(strDate+" 이후");

        Wallet wallet= Wallet.getInstance();
        String filepath = getFilesDir()+"/keystore";

        try {
            List<WalletVO>lists=wallet.getLists(filepath);
            for (WalletVO walletBean:lists) {
                tname = walletBean.getName();
                taddress = walletBean.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        walletName.setText(tname);
        walletAddress.setText(taddress);

        tv_name.setText(name);
        tv_hp.setText(hp);
        tv_email.setText(email);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finishContract:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // 제목셋팅
                alertDialogBuilder.setTitle("계약서 작성");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("계약서 작성을 완료하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("완료",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        ru();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog.show();
                break;
            case R.id.cancelContract:
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(
                        context);

                // 제목셋팅
                alertDialogBuilder1.setTitle("계약서 작성 취소");

                // AlertDialog 셋팅
                alertDialogBuilder1
                        .setMessage("계약서 작성을 취소하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("완료",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 프로그램을 종료한다
                                        RoomContractActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog1 = alertDialogBuilder1.create();

                // 다이얼로그 보여주기
                alertDialog1.show();
                break;
            default:
                break;
        }
    }

    //StringToBytes32
    public static byte[] stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return byteValueLen32;
    }


    //지혜추가~~~~~~~~
    protected NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(String hashCode);
        void  failed(String message);
    };
    //~~~~~

    public void ru(){

        mDialog = new SpotsDialog(this,"loading....");
        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run(){
                Web3jAPI web3jAPI = Web3jAPI.getInstance();
                Boolean valid = true;
                BigInteger balance =  Web3jUtil.etherToWei(web3jAPI.getETHBalance());

                BigDecimal seq = new BigDecimal(r_total);

                if (BigInteger.valueOf(r_total).compareTo(balance) > 0) {
                    if(mDialog!=null && mDialog.isShowing()){
                        mDialog.dismiss();
                        Log.d("돈부족 확인", "돈부족 확인");
                    }
                    valid = false;
                }

                rt_hash = web3jAPI.rentalRoom(BigInteger.valueOf(r_blockCode), stringToBytes32(et_rt_name.getText().toString()), stringToBytes32(et_rt_businessNum.getText().toString()), Web3jUtil.etherToWei(seq));

                Log.d("hash~~~~~~~" , "zzzzzzzzzzzzzzzzzzzzzzzzz");

                //메뉴 api통신
                networkResponse = new NetworkResponse() {
                    @Override
                    public void success(String hashCode) {
                        Log.d("hash~~~~~~~" , rt_hash+"@@@@@@@@@@@@@@@@@@@@@@@@@");
                        callContractApi();
                    }

                    @Override
                    public void failed(String message) {
                    }
                };


                if(networkResponse!=null) {
                    networkResponse.success(rt_hash);
                }


            }
        }).start();
    }

    //계약 정보 통신
    protected void callContractApi() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        RoomContractVO vo = new RoomContractVO();

        vo.setRt_hash(rt_hash);
        vo.setR_code(r_code);
        vo.setUserid(userId);
        vo.setRt_mobile(et_rt_mobile.getText().toString());
        vo.setRt_email(et_rt_email.getText().toString());
        vo.setRt_date1(r_able_date);
        vo.setRt_deposit(r_deposit);
        vo.setStaff_id(userid);

        //계약 정보 통신
        Call<HashMap<String, Object>> call = apiInterface.insertContract(vo);
        call.enqueue(new Callback<HashMap<String, Object>>() {
            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    HashMap<String, Object> data = response.body();
                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(data);
                    Log.d("계약 정보 통신~~~~",json3);

                    String responseCode = data.get("responseCode").toString();
                    String responseMsg = data.get("responseMsg").toString();

                    Log.d("응답코드~~~~~~~~~", responseCode);
                    Toast toast = Toast.makeText(getApplicationContext(), "계약 정보 insert 성공!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    //참고사이트 : https://arabiannight.tistory.com/entry/286
                    //FLAG_ACTIVITY_CLEAR_TOP: 루트액티비티와 중복된 액티비티만 남고 그게 아니면 액티비티가 디스트로이됨
                    Intent intent = new Intent(RoomContractActivity.this, RoomContractCompleteActivity.class);
                    startActivity(intent);

                    if(!responseCode.equals("500")) {
                        toast = Toast.makeText(getApplicationContext(),responseMsg, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                Log.d("계약 정보 통신 실패했습니다.", t.getMessage());

                call.cancel();
            }
        });
    }
}
