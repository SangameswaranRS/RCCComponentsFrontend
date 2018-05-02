package com.example.sangameswaran.rcccomponentsfrontend.Constants;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class CommonFunctions {
    public static void toastString(String message, Context context){
        Toast t=Toast.makeText(context,message,Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();
    }
}
