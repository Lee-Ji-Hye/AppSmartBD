package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.FoodCartVO;
import com.team.smart.vo.FoodOrderVO;
import com.team.smart.vo.FoodStoreVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderActivity extends AppCompatActivity {
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private String userid;
    private ArrayList<FoodCartVO> foodVo;
    private int salePrice = 0; //할인액
    int sum=0;
    private String comp_seq;

    private ArrayAdapter myAdapter;
    private Spinner spinArriveTime;

    //findid
    TextView tvCompOrg,tvAddress,tvFcnt,editMessage,tvAmount,tvSalePrice,tvTotPayPrice,tvOpen,tvOpenWeek,paymentBtn,tvCompName, tvCompHp;
    EditText tvName, tvHp, btMinusBtn, btPlusBtn;

    int cnt = 1; //인원 관리

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_form);

        userid = SPUtil.getUserId(FoodOrderActivity.this); //아이디 없으면 ""

        Intent intent = getIntent();
        foodVo = (ArrayList<FoodCartVO>) intent.getSerializableExtra("cartMenuList");
        comp_seq = intent.getExtras().getString("comp_seq"); /*String형*/

        findid();
        calcPrice();      //금액 계산 함수
        configuListner(); // 클릭 리스너 일괄 세팅

        //여기서 업체정보 불러오는 통신 한번 하고(~)
        callStoreInfoApi(comp_seq);


    }

    private APIInterface apiStoreInterface;

    //스토어 통신
    protected void callStoreInfoApi(String paramCompSeq) {

        if(apiStoreInterface == null) {
            apiStoreInterface = APIClient.getClient().create(APIInterface.class);
        }

        //통신
        Call<FoodStoreVO> call = apiStoreInterface.foodStore(paramCompSeq);
        call.enqueue(new Callback<FoodStoreVO>() {
            @Override
            public void onResponse(Call<FoodStoreVO> call, Response<FoodStoreVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodStoreVO data = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(data.getStores());
                    Log.d("오더 스토어 통신~~~~",json3);

                    FoodStoreVO.Store store = data.getStores().get(0);
                    tvAddress.setText(store.getComp_branch()); //업체 주소
                    tvOpen.setText(store.getF_open_stt() +"~"+store.getF_open_end());
                    tvCompName.setText(store.getComp_org());
                    tvCompHp.setText(store.getComp_hp());
                    //정보세팅
                    //foodStore = ;
//                    FoodStoreVO.Store store = data.getStores().get(0);
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                    viewStoreLongDesc = inflater.inflate(R.layout.food_detail_desc, null);
//                    TextView tvLongDesc = viewStoreLongDesc.findViewById(R.id.tv_long_desc);
//                    tvLongDesc.setText(store.getLong_desc());
//                    //mainImg
//                    Glide.with(getApplicationContext()).load(store.getF_mainimg()).placeholder(R.drawable.no_img)
//                            .error(R.drawable.no_img).into(mainImg);

                }
            }

            @Override
            public void onFailure(Call<FoodStoreVO> call, Throwable t) {
                Log.d("스토어 통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
            }
        });
    }

    private void findid() {
        tvCompOrg = findViewById(R.id.tv_comp_org);           //업체명
        tvCompName = findViewById(R.id.tv_comp_name);         //업체명2
        tvCompHp = findViewById(R.id.tv_comp_hp);             //업체번호
        tvAddress = findViewById(R.id.tv_address);            //업체주소
        tvName = findViewById(R.id.edit_name);                   //주문자명
        tvHp = findViewById(R.id.edit_hp);                       //핸드폰번호
        tvFcnt = findViewById(R.id.tv_fCnt);                  //인원수
        editMessage = findViewById(R.id.edit_message);       //요청사항
        tvOpen = findViewById(R.id.tv_open);                  //운영시간(평일)
        tvOpenWeek = findViewById(R.id.tv_open_week);        //운영시간(주말)
        tvAmount = findViewById(R.id.tv_amount);             //주문금액
        tvSalePrice = findViewById(R.id.tv_sale_price);     //할인금액
        tvTotPayPrice = findViewById(R.id.tv_tot_pay_price);//최종 결제 금액

        btMinusBtn = findViewById(R.id.bt_minusBtn);
        btPlusBtn = findViewById(R.id.bt_plusBtn);

        //소요시간(스피너 세팅)
        spinArriveTime = (Spinner)findViewById(R.id.spinArrivedTime);
        String[] strList =  getResources().getStringArray(R.array.spinner_counts);
        myAdapter = new ArrayAdapter(FoodOrderActivity.this,android.R.layout.simple_spinner_dropdown_item,strList);
        spinArriveTime.setAdapter(myAdapter);
        spinArriveTime.setSelection(0);

        paymentBtn = findViewById(R.id.paymentBtn); //결제하기 버튼

        /*값 세팅 */
        tvCompOrg.setText(foodVo.get(0).getComp_org());
        tvOpen.setText("24시간");
        tvOpenWeek.setText("18시간");
    }

    /* 금액 계산 */
    private void calcPrice() {
        sum = 0;
        try {
            if (foodVo != null) {
                for (int i = 0; i < foodVo.size(); i++) {
                    sum += foodVo.get(i).getF_cnt() * Integer.parseInt(foodVo.get(i).getF_price());
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "금액설정오류", Toast.LENGTH_LONG).show();
        }

        //할인액
        if(salePrice > 0) {
            sum = sum - salePrice;
            tvSalePrice.setText(salePrice);
        } else {
            tvSalePrice.setText("0원");
        }
        tvAmount.setText(numberComma.format(sum)+"원");//주문 금액
        tvTotPayPrice.setText(numberComma.format(sum)+"원"); //최종 결제금액

    }

    //리스너 이벤트 모아놓음
    private void configuListner() {
//음식점 페이지 이동
        btMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cnt > 1) {
                    cnt--;
                    tvFcnt.setText(String.valueOf(cnt)); //수량변경
                }
            }
        });

        btPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt < 10) {
                    cnt++;
                    tvFcnt.setText(String.valueOf(cnt)); //수량변경
                }
            }
        });

        paymentBtn.setOnClickListener(view -> {

            //파라미터 세팅
            String f_cnt = (String)tvFcnt.getText();
            String f_receive_time = spinArriveTime.getSelectedItem().toString();

            FoodOrderVO orderinfoVO = new FoodOrderVO();
            orderinfoVO.setComp_seq(comp_seq);
            orderinfoVO.setUserid(userid);
            orderinfoVO.setF_name(tvName.getText().toString());
            orderinfoVO.setF_hp(tvHp.getText().toString());
            orderinfoVO.setF_person_num(f_cnt);
            orderinfoVO.setF_receive_time(f_receive_time);
            orderinfoVO.setF_message(editMessage.getText().toString());
            //orderinfoVO.setF_serial("asd-asd-asd"); //시리얼
            orderinfoVO.setF_sale_price(String.valueOf(salePrice));
            orderinfoVO.setF_amount(String.valueOf(sum));


            //검증
            if(tvName.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(),"주문자명을 입력하세요", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                tvName.requestFocus();
                return;
            } else if(tvHp.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(),"휴대번호를 입력하세요", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                tvHp.requestFocus();
                return;
            }

            //카카오페이 액티비티 이동
            Intent intent = new Intent(getApplicationContext(), AKakaoTestActivity.class);
            intent.putExtra("orderMenuList", foodVo);
            intent.putExtra("orderInfo", orderinfoVO);

            this.startActivity(intent);

        });
    }
}
