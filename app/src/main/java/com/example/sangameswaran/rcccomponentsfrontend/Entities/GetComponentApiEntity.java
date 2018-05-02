package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class GetComponentApiEntity {
    int statusCode;
    List<ComponentEntity> message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ComponentEntity> getMessage() {
        return message;
    }

    public void setMessage(List<ComponentEntity> message) {
        this.message = message;
    }

    public interface RCCRCI{
        void onGetComponentInfo(GetComponentApiEntity entity, VolleyError error);
    }
}
