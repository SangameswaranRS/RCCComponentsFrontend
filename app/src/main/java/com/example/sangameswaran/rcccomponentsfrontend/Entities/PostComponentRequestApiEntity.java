package com.example.sangameswaran.rcccomponentsfrontend.Entities;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Sangameswaran on 30-04-2018.
 */

public class PostComponentRequestApiEntity {
     List<RequestComponentPostEntity> requestedComponents;

    public List<RequestComponentPostEntity> getRequestedComponents() {
        return requestedComponents;
    }

    public void setRequestedComponents(List<RequestComponentPostEntity> requestedComponents) {
        this.requestedComponents = requestedComponents;
    }

    public interface RCCRCI{
        public void onSubmitComponentRequest(ErrorEntity errorEntity, VolleyError error);
    }
}
