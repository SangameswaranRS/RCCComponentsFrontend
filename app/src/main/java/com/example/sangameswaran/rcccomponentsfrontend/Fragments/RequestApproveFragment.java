package com.example.sangameswaran.rcccomponentsfrontend.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Adapters.RequestHandlerAdapter;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetUserRequestsApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class RequestApproveFragment extends Fragment {
    public ProgressBar requestApproveFragmentLoader;
    public RecyclerView rvRequestApproval;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    Context activityContext;
    List<RequestEntity> allRequests;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.request_approve_fragment,container,false);
        activityContext=getContext();
        allRequests=new ArrayList<>();
        requestApproveFragmentLoader=(ProgressBar) v.findViewById(R.id.requestApproveFragmentLoader);
        rvRequestApproval=(RecyclerView)v.findViewById(R.id.rvRequestApproval);
        manager=new LinearLayoutManager(getContext());
        RestClientImplementation.getAllRequests(new GetUserRequestsApiEntity.RCCRCI() {
            @Override
            public void onGetRequestList(GetUserRequestsApiEntity entity, VolleyError error) {
                if(error==null){
                    allRequests=entity.getMessage();
                    adapter=new RequestHandlerAdapter(allRequests,activityContext,RequestApproveFragment.this);
                    rvRequestApproval.setLayoutManager(manager);
                    rvRequestApproval.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    requestApproveFragmentLoader.setVisibility(View.GONE);
                }
            }
        },activityContext);
        return v;
    }
}
