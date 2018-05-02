package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 30-04-2018.
 */

public class InventoryComponentEntity {
    int cid,current_available_count;
    int total_count;
    String component_name;
    String rough_price;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCurrent_available_count() {
        return current_available_count;
    }

    public void setCurrent_available_count(int current_available_count) {
        this.current_available_count = current_available_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getComponent_name() {
        return component_name;
    }

    public void setComponent_name(String component_name) {
        this.component_name = component_name;
    }

    public String getRough_price() {
        return rough_price;
    }

    public void setRough_price(String rough_price) {
        this.rough_price = rough_price;
    }

    public interface RCCRCI{
        void onResult(ErrorEntity errorEntity, VolleyError error);
    }
}
