package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 30-04-2018.
 */

public class ViewInventoryAPIEntity {
    int statusCode;
    List<InventoryComponentEntity> message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<InventoryComponentEntity> getMessage() {
        return message;
    }

    public void setMessage(List<InventoryComponentEntity> message) {
        this.message = message;
    }
    public interface RCCRCI{
        void onGetInventoryDetails(ViewInventoryAPIEntity entity, VolleyError error);
    }
}
