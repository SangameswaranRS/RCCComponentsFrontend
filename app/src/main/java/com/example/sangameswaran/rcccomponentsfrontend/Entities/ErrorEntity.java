package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class ErrorEntity {
    int statusCode;
    String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface RCCRCI{
        public void onGetMessage(ErrorEntity errorEntity, VolleyError error);
    }
}
