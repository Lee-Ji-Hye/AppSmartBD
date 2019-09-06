package com.team.smart.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

public class RoomContractActivity extends AppCompatActivity implements
        View.OnClickListener {

    final Context context = this;
    private Button finishContract, cancelContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract);

        finishContract = findViewById(R.id.finishContract);
        cancelContract = findViewById(R.id.cancelContract);

        finishContract.setOnClickListener(this);
        cancelContract.setOnClickListener(this);
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
