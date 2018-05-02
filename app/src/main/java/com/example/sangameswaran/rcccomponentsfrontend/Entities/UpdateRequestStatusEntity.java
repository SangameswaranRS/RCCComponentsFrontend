package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class UpdateRequestStatusEntity {
    int rid;
    String rstatus;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }

    public interface RCCRCI{
        void onUpdate(ErrorEntity errorEntity, VolleyError error);
    }
}
