package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.FoodCartVO;
import com.team.smart.vo.FoodOrderDetailVO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderDetailActivity extends AppCompatActivity {
    private APIInterface apiInterface;// 통신 인터페이스
    private APIInterface apiCancelInterface;// 취소 통신 인터페이스
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마

    private String userid, username;

    private String paramFocode, paramFname;

    private TextView tvOcode,tvStatus,tvName,tvPersonNum,tvRegidate,tvReceive_time,tvMenu_list,tvMessage,tvAmount,tvSalePrice,tvTotPayPrice,tvCompOrg,tvCompName,tvCompHp,paymentBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_detail);

        userid = SPUtil.getUserId(FoodOrderDetailActivity.this); //아이디 없으면 ""

        Intent intent = getIntent(); /*데이터 수신*/
        paramFocode = intent.getExtras().getString("f_ocode"); /*String형*/
        paramFname = intent.getExtras().getString("f_name"); /*String형*/
        Log.d("", paramFocode + ":::::"+paramFname);

        findId();
        configuListner();

        //주문 정보 가져오기
        if(paramFocode != null && !paramFocode.equals("")) {
            callApi();
        }

    }

    //주문정보 보기 통신
    protected void callApi() {

        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }

        username = paramFname;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("f_ocode", paramFocode);
        map.put("userid", userid);
        map.put("username", username);

        Call<FoodOrderDetailVO> call = apiInterface.getOrderDerail(map);
        call.enqueue(new Callback<FoodOrderDetailVO>() {
            @Override
            public void onResponse(Call<FoodOrderDetailVO> call, Response<FoodOrderDetailVO> response) {
                if(response.code()==200) {
                    FoodOrderDetailVO resource = response.body(); //응답

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource);
                    Log.d("여기에도 오는지~~~>", json3);


                    FoodOrderDetailVO.Detail detail = resource.getOrderDetail().get(0);
                    ArrayList<FoodCartVO> menulist = detail.getMenulist();

                    String menuStr = "";
                    for(int i=0; i < menulist.size(); i++) {
                        FoodCartVO menu = menulist.get(0);
                        menuStr += menu.getF_name()+" " + menu.getF_cnt()+"개"+"\n";
                    }

                    int amount = Integer.parseInt(detail.getF_amount());
                    int salePrice = Integer.parseInt(detail.getF_sale_price());
                    int totPayPrice = Integer.parseInt(detail.getF_amount()) - Integer.parseInt(detail.getF_sale_price());

                    tvOcode.setText(detail.getF_ocode());
                    tvStatus.setText(detail.getF_status());
                    tvName.setText(detail.getF_name());
                    tvPersonNum.setText(detail.getF_person_num()+" 명");
                    tvRegidate.setText(detail.getF_regidate());
                    tvReceive_time.setText(detail.getF_receive_time());
                    tvMenu_list.setText(menuStr);
                    tvMessage.setText(detail.getF_message());
                    tvAmount.setText(numberComma.format(amount));
                    tvSalePrice.setText(numberComma.format(salePrice));
                    tvTotPayPrice.setText(numberComma.format(totPayPrice));
                    tvCompName.setText(detail.getComp_org());
                    tvCompOrg.setText(detail.getComp_org());
                    tvCompHp.setText(detail.getComp_hp());

                    //주문접수일때만 주문 취소하기가 가능
                    if(detail.getF_status().equals("주문접수") || detail.getF_status().equals("주문대기") ) {
                        paymentBtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodOrderDetailVO> call, Throwable t) {
                Log.d("주문정보 통신 fail~~", "실패..");
                call.cancel();
            }
        });
    }

    protected void findId() {
        tvOcode  = findViewById(R.id.tv_ocode);
        tvStatus = findViewById(R.id.tv_status);
        tvName   = findViewById(R.id.tv_name);
        tvPersonNum = findViewById(R.id.tv_person_num);
        tvRegidate  = findViewById(R.id.tv_regidate);
        tvReceive_time = findViewById(R.id.tv_receive_time);
        tvMenu_list    = findViewById(R.id.tv_menu_list);
        tvMessage   =  findViewById(R.id.tv_message);
        tvAmount    =  findViewById(R.id.tv_amount);
        tvSalePrice =  findViewById(R.id.tv_sale_price);
        tvTotPayPrice = findViewById(R.id.tv_tot_pay_price);
        tvCompOrg     = findViewById(R.id.tv_comp_org);
        tvCompName    = findViewById(R.id.tv_comp_name);
        tvCompHp      = findViewById(R.id.tv_comp_hp);
        paymentBtn      = findViewById(R.id.paymentBtn);
    }

    protected void configuListner() {
        paymentBtn.setOnClickListener(view -> {
            callModifyStatusApi();
        });
    }

    //주문정보 보기 통신
    protected void callModifyStatusApi() {

        if(apiCancelInterface == null) {
            apiCancelInterface = APIClient.getClient().create(APIInterface.class);
        }

        Call<HashMap> call = apiCancelInterface.modifyFoodStatus(paramFocode, "주문취소");
        call.enqueue(new Callback<HashMap>() {
            @Override
            public void onResponse(Call<HashMap> call, Response<HashMap> response) {
                if(response.code()==200) {
                    HashMap resource = response.body(); //응답

                    String new_status = resource.get("new_status").toString();
                    if(new_status.equals("주문취소")) {
                        Toast.makeText(getApplicationContext(), "주문 취소되었습니다.", Toast.LENGTH_SHORT).show();
                        //새로고침~
                        Intent intent =new Intent(FoodOrderDetailActivity.this, FoodOrderDetailActivity.class);
                        intent.putExtra("f_ocode",paramFocode);
                        intent.putExtra("f_name", paramFname); //이거 비어서 에러날 수 있으니 주의..
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Call<HashMap> call, Throwable t) {
                Log.d("주문정보 통신 fail~~", "실패..");
                call.cancel();
            }
        });
    }
}
