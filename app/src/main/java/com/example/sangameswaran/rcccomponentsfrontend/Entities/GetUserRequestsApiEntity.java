package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class GetUserRequestsApiEntity {
    List<RequestEntity> message;
    int statusCode;

    public List<RequestEntity> getMessage() {
        return message;
    }

    public void setMessage(List<RequestEntity> message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public interface RCCRCI{
        void onGetRequestList(GetUserRequestsApiEntity entity, VolleyError error);
    }
}
