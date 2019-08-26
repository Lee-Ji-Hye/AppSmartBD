package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.team.smart.R;

public class SignUpActivity extends AppCompatActivity {

    TextView btnSignIn;
    EditText userHp, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignIn = findViewById(R.id.btnSignIn);
        userHp = findViewById(R.id.userHp);
        userEmail = findViewById(R.id.userEmail);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        userHp.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        userEmail.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    }
}
