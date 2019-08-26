package com.team.smart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.team.smart.R;
import com.team.smart.adapter.StartAdapter;
import com.team.smart.blockchain.Wallet;

import java.util.ArrayList;
import java.util.List;

public class Start extends Activity {

    private List<String> names = new ArrayList<>();
    private List<String> passwords = new ArrayList<>();
    private List<String> filepaths = new ArrayList<>();
    private StartAdapter startAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);

        initData();

        if (names.size() == 0){
            Intent intent = new Intent();
            intent.setClass(this,WalletCreateActivity.class);
            startActivity(intent);
        } else {
            ListView listView = findViewById(R.id.listview);
            startAdapter = new StartAdapter(this, R.layout.wallet_item, names);
            listView.setAdapter(startAdapter);

            View footerView  = getLayoutInflater().inflate(R.layout.start_footer_btn,null);
            Button btn= footerView.findViewById(R.id.btn_import);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(Start.this,WalletCreateActivity.class);
                    startActivity(intent);
                }
            });
            listView.addFooterView(footerView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //뷰를 통해 내부 구성 요소를 가져온 다음 작동
                    final String choice = names.get(i);
                    final String pass = passwords.get(i);
                    final String fi = filepaths.get(i);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Start.this);
                    final EditText et = new EditText(Start.this);
                    builder.setMessage("비밀번호를 입력하세요").
                        setView(et).
                        setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            String inputPass = et.getText().toString();
                            if (inputPass.equals(pass)) {
                                Toast.makeText(getApplicationContext(), "지갑 정보 관리 페이지 이동", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MyWalletActivity.class); //MyWalletActivity 이동할 준비
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_LONG).show();
                            }
                            }
                        }).
                        setNegativeButton("취소", null);
                    builder.create().show();
                }
            });
        }
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
        filepaths = wallet.getFilepaths();
    }


    public void startUp(String name, String pass, String file) {
        Wallet wallet = Wallet.getInstance();
        wallet.useWallet(name,pass,file);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
