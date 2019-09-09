package com.team.smart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

//import android.widget.TextView;

public class RoomContractActivity extends AppCompatActivity implements
        View.OnClickListener {

    private String b_area1,b_area2,b_address,b_year,b_landarea,b_buildarea,b_buildscale
                  ,r_price,r_deposit
                  ,name,email,hp,comp_seq;

    TextView tv_b_address,tv_b_year,tv_b_landarea,tv_b_buildscale,tv_b_buildarea
            ,tv_r_price2,tv_r_price3,tv_r_deposit3,tv_r_deposit4,tv_total_price
            ,tv_name,tv_comp_seq,tv_hp,tv_email;

    final Context context = this;
    private Button finishContract, cancelContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract);

        findid();

        finishContract = findViewById(R.id.finishContract);
        cancelContract = findViewById(R.id.cancelContract);

        finishContract.setOnClickListener(this);
        cancelContract.setOnClickListener(this);
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

        r_price = intent.getExtras().getString("r_price");
        r_deposit = intent.getExtras().getString("r_deposit");

        name = intent.getExtras().getString("name");
        email = intent.getExtras().getString("email");
        hp = intent.getExtras().getString("hp");
        comp_seq = intent.getExtras().getString("comp_seq");

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

        tv_name = findViewById(R.id.tv_name);
        tv_comp_seq = findViewById(R.id.tv_comp_seq);
        tv_hp = findViewById(R.id.tv_hp);
        tv_email = findViewById(R.id.tv_email);

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
        tv_total_price.setText(r_price + r_deposit);

        tv_name.setText(name);
        tv_comp_seq.setText(comp_seq);
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
}
