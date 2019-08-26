package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.team.smart.R;

import java.text.DecimalFormat;

public class DetailFoodPopupActivity extends AppCompatActivity {
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마

    private String fcode, comp_seq, comp_org, fprice, fname, f_img; /*Intent Parameter Data*/
    TextView tvCompOrg, tvFname, tvFprice, tvTotPrice, tvFcnt, cartBtn;
    EditText btMinusBtn, btPlusBtn;
    ImageView backBtn, ivFimg;

    int cnt = 1; //수량 관리

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_popup);

        findid();
        configuListner();

    }

    private void findid() {
        /*Intent Parameter Data*/
        Intent intent = getIntent(); /*데이터 수신*/
        fcode = intent.getExtras().getString("fcode");       /*String형*/
        comp_seq = intent.getExtras().getString("comp_seq"); /*String형*/
        comp_org = intent.getExtras().getString("comp_org"); /*String형*/
        fprice = intent.getExtras().getString("fprice");     /*String형*/
        fname = intent.getExtras().getString("fname");       /*String형*/
        f_img = intent.getExtras().getString("f_img");       /*String형*/


        /* find id */
        tvCompOrg = findViewById(R.id.tv_comp_org);   //업체명
        tvFname = findViewById(R.id.tv_fname);        //메뉴명
        tvFprice = findViewById(R.id.tv_fprice);      //가격
        tvTotPrice = findViewById(R.id.tv_tot_price); //총가격
        btMinusBtn = findViewById(R.id.bt_minusBtn);  //마이너스 edit버튼
        btPlusBtn = findViewById(R.id.bt_plusBtn);  //마이너스 edit버튼
        tvFcnt = findViewById(R.id.tv_fCnt);          //수량
        cartBtn = findViewById(R.id.cartBtn);         //1개 담기 버튼
        cartBtn = findViewById(R.id.cartBtn);         //1개 담기 버튼
        ivFimg = findViewById(R.id.iv_f_img);
        Glide.with(getApplicationContext()).load(f_img).placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).into(ivFimg);

        backBtn = findViewById(R.id.backBtn);

        /* find id -> text setting */
        tvCompOrg.setText(comp_org);
        tvFname.setText(fname);
        tvFprice.setText(numberComma.format(Integer.parseInt(fprice)));
        tvTotPrice.setText("결제금액: " + numberComma.format(Integer.parseInt(fprice))+"원");

        /* 수량 가져와서 세팅 */
        cnt = Integer.parseInt((String) tvFcnt.getText()); //수량 세팅

    }

    //클릭 리스너 일괄 세팅
    private void configuListner() {
        //음식점 페이지 이동
        btMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cnt > 1) {
                    cnt--;
                    int totPrice = Integer.parseInt(fprice) * cnt;
                    //tvTotPrice.setText(numberComma.format(totPrice));//주문금액 UPDATE
                    tvTotPrice.setText("결제금액: " + numberComma.format(totPrice)+"원");
                    tvFcnt.setText(String.valueOf(cnt)); //수량변경
                    cartBtn.setText(String.valueOf(cnt) + "개 담기");
                }
            }
        });

        btPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt < 10) {
                    cnt++;
                    int totPrice = Integer.parseInt(fprice) * cnt;
                    //tvTotPrice.setText(numberComma.format(totPrice));//주문금액 UPDATE
                    tvTotPrice.setText("결제금액: " + numberComma.format(totPrice)+"원");
                    tvFcnt.setText(String.valueOf(cnt)); //수량변경
                    cartBtn.setText(String.valueOf(cnt) + "개 담기");
                }
            }
        });

        //메뉴 담기버튼 이벤트
        cartBtn.setOnClickListener((view)->{
            if(cnt > 0) {
                Intent intent = new Intent();
                intent.putExtra("foodcnt",cnt);
                intent.putExtra("fcode",fcode);
                intent.putExtra("comp_seq",comp_seq);
                intent.putExtra("comp_org",comp_org);
                intent.putExtra("fprice",fprice);
                intent.putExtra("fname",fname);
                setResult(RESULT_OK,intent); //OK
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "선택된 메뉴 없음", Toast.LENGTH_SHORT).show();
            }

        });

        //백 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}