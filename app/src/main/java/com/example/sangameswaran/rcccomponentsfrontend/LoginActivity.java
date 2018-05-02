package com.example.sangameswaran.rcccomponentsfrontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.LoginEntity;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class LoginActivity extends AppCompatActivity {
    EditText etLoginUserName,etLoginPassword;
    FloatLabeledEditText flt1,flt2;
    Button btnLogin;
    ProgressBar loginProgress;
    TextView signupEt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        flt1=(FloatLabeledEditText) findViewById(R.id.flt1);
        flt2=(FloatLabeledEditText) findViewById(R.id.flt2);
        etLoginUserName = (EditText) findViewById(R.id.etLoginUserName);
        etLoginPassword= (EditText)findViewById(R.id.etLoginPassword);
        signupEt=(TextView)findViewById(R.id.signupEt);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginProgress = (ProgressBar) findViewById(R.id.loginProgress);
        signupEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLoginUserName.setVisibility(View.GONE);
                etLoginPassword.setVisibility(View.GONE);
                btnLogin.setVisibility(View.GONE);
                flt1.setVisibility(View.GONE);
                flt2.setVisibility(View.GONE);
                loginProgress.setVisibility(View.VISIBLE);
                String uname = etLoginUserName.getText().toString();
                String upassword = etLoginPassword.getText().toString();
                LoginEntity entity=new LoginEntity();
                entity.setUname(uname);
                entity.setUpassword(upassword);
                if(uname.equals("")||upassword.equals("")){
                    CommonFunctions.toastString("Login Credentials cant be empty I guess!",LoginActivity.this);
                    flt1.setVisibility(View.VISIBLE);
                    flt2.setVisibility(View.VISIBLE);
                    etLoginUserName.setVisibility(View.VISIBLE);
                    etLoginPassword.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.GONE);
                }else {
                    RestClientImplementation.loginApi(entity, new LoginEntity.RCCRCI() {
                        @Override
                        public void onLogin(LoginEntity entity, VolleyError error) {
                            if (error == null) {
                                //Login Successful
                                SharedPreferences sp = getSharedPreferences("LOGIN_CREDS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("uid", entity.getUid());
                                editor.putString("urole", entity.getUrole());
                                editor.commit();
                                CommonFunctions.toastString("Logged in!", LoginActivity.this);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                flt1.setVisibility(View.VISIBLE);
                                flt2.setVisibility(View.VISIBLE);
                                etLoginUserName.setVisibility(View.VISIBLE);
                                etLoginPassword.setVisibility(View.VISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);
                                loginProgress.setVisibility(View.GONE);
                            }
                        }
                    }, LoginActivity.this);
                }
            }
        });

    }
}
