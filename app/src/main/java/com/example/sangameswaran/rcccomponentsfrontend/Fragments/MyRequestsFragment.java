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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Adapters.MyRequestsRecyclerViewAdapter;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetUserRequestsApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class MyRequestsFragment extends Fragment {
    RecyclerView myRequestsRecyclerView;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    List<RequestEntity> requestList;
    TextView tvNoRequests;
    Context context;
    ProgressBar myRequestsLoader;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.my_requests_fragment,container,false);
        requestList=new ArrayList<>();
        context=getContext();
        myRequestsRecyclerView=(RecyclerView)v.findViewById(R.id.myRequestsRecyclerView);
        tvNoRequests=(TextView)v.findViewById(R.id.tvNoRequests);
        myRequestsLoader=(ProgressBar)v.findViewById(R.id.myRequestsLoader);
        manager=new LinearLayoutManager(getContext());
        myRequestsRecyclerView.setLayoutManager(manager);
        RestClientImplementation.getMyRequestsApi(new GetUserRequestsApiEntity.RCCRCI() {
            @Override
            public void onGetRequestList(GetUserRequestsApiEntity entity, VolleyError error) {
                if(error==null) {
                    myRequestsLoader.setVisibility(View.GONE);
                    requestList = entity.getMessage();
                    adapter = new MyRequestsRecyclerViewAdapter(requestList, context);
                    myRequestsRecyclerView.setAdapter(adapter);
                    myRequestsRecyclerView.setLayoutManager(manager);
                    adapter.notifyDataSetChanged();
                    if(requestList.size() ==0){
                        myRequestsRecyclerView.setVisibility(View.GONE);
                        tvNoRequests.setVisibility(View.VISIBLE);
                    }
                }else{
                    //Do Nothing.
                }
            }
        },getContext());
        return v;
    }
}
