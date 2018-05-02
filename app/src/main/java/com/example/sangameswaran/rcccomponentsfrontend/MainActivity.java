package com.example.sangameswaran.rcccomponentsfrontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Fragments.MyRequestsFragment;
import com.example.sangameswaran.rcccomponentsfrontend.Fragments.RaiseRequestFragment;
import com.example.sangameswaran.rcccomponentsfrontend.Fragments.RequestApproveFragment;
import com.example.sangameswaran.rcccomponentsfrontend.Fragments.ViewInventoryFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    String accessRole;
    AlertDialog.Builder quitDialog;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MyRequestsFragment fragment=new MyRequestsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    RaiseRequestFragment fragment1=new RaiseRequestFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment1).commit();
                    return true;
                case R.id.menu_inventory:
                    ViewInventoryFragment fragment2=new ViewInventoryFragment();
                    if(accessRole.equals("ADMIN")){
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment2).commit();
                    }else{
                        CommonFunctions.toastString("Oops! You are blocked out",MainActivity.this);
                        return false;
                    }
                    return true;
                case R.id.requestApprover:
                    RequestApproveFragment fragment3=new RequestApproveFragment();
                    if(accessRole.equals("ADMIN")){
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment3).commit();
                    }else{
                        CommonFunctions.toastString("Oops! You are blocked out",MainActivity.this);
                        return false;
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp=getSharedPreferences("LOGIN_CREDS",MODE_PRIVATE);
        accessRole=sp.getString("urole","STUDENT");
        quitDialog=new AlertDialog.Builder(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MyRequestsFragment fragment=new MyRequestsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        quitDialog.setTitle("Are you sure to quit?").setMessage("By clicking quit you will be logged out and you should login again for further use.").setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }
}
