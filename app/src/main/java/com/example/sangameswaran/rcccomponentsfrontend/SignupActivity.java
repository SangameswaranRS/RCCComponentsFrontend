package com.example.sangameswaran.rcccomponentsfrontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ErrorEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.UserSignupAPIEntity;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class SignupActivity extends AppCompatActivity {
    EditText username,password,contactNumber,emailId;
    Button btnSignup;
    ProgressBar signupProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=(EditText)findViewById(R.id.etSignupName);
        password=(EditText)findViewById(R.id.etSignupPassword);
        contactNumber=(EditText)findViewById(R.id.etContactNumber);
        emailId=(EditText)findViewById(R.id.etSignupEmailId);
        btnSignup=(Button)findViewById(R.id.btnSignup);
        signupProgress=(ProgressBar)findViewById(R.id.signupProgress);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=username.getText().toString();
                String upassword=password.getText().toString();
                String ucontact=contactNumber.getText().toString();
                String email=emailId.getText().toString();
                if(uname.equals("")||upassword.equals("")||ucontact.equals("")||email.equals("")){
                    CommonFunctions.toastString("Enter every detail!",SignupActivity.this);
                }else{
                    UserSignupAPIEntity entity=new UserSignupAPIEntity();
                    entity.setUname(uname);
                    entity.setUpassword(upassword);
                    entity.setUcontact(ucontact);
                    entity.setUemailid(email);
                    entity.setUrole("STUDENT");
                    signupProgress.setVisibility(View.VISIBLE);
                    RestClientImplementation.signUpAPI(entity, new UserSignupAPIEntity.RCCRCI() {
                        @Override
                        public void onSubmitDetails(ErrorEntity response, VolleyError error) {
                            if(error==null){
                                CommonFunctions.toastString("Login to Continue",SignupActivity.this);
                                Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                signupProgress.setVisibility(View.GONE);
                            }
                        }
                    },SignupActivity.this);
                }
            }
        });
    }
}
