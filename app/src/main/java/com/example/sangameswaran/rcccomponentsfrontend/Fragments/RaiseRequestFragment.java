package com.example.sangameswaran.rcccomponentsfrontend.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Adapters.RaiseRequestRVAdapter;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ErrorEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.InventoryComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.PostComponentRequestApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestComponentPostEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ViewInventoryAPIEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 30-04-2018.
 */

public class RaiseRequestFragment extends Fragment {
    RecyclerView raiseRequestRecyclerView;
    ProgressBar raiseRequestLoader;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    Context activityContext;
    List<InventoryComponentEntity> list;
    Button btnRaiseRequest;
    AlertDialog.Builder alert;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.raise_request_fragment,container,false);
        raiseRequestRecyclerView=(RecyclerView)v.findViewById(R.id.raiseRequestRecyclerView);
        raiseRequestLoader=(ProgressBar) v.findViewById(R.id.raiseRequestLoader);
        btnRaiseRequest=(Button)v.findViewById(R.id.btnRaiseRequest);
        btnRaiseRequest.setVisibility(View.GONE);
        list=new ArrayList<>();
        alert=new AlertDialog.Builder(getContext());
        manager=new LinearLayoutManager(getContext());
        activityContext=getContext();
        RestClientImplementation.getComponentsForRaisingRequest(new ViewInventoryAPIEntity.RCCRCI() {
            @Override
            public void onGetInventoryDetails(ViewInventoryAPIEntity entity, VolleyError error) {
                if(error ==null){
                    list=entity.getMessage();
                    adapter=new RaiseRequestRVAdapter(list,activityContext);
                    raiseRequestRecyclerView.setAdapter(adapter);
                    raiseRequestRecyclerView.setLayoutManager(manager);
                    raiseRequestLoader.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    btnRaiseRequest.setVisibility(View.VISIBLE);
                }else{

                }
            }
        },activityContext);
        btnRaiseRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=activityContext.getSharedPreferences("HASHMAP",Context.MODE_PRIVATE);
                String jsonString = sp.getString("MAP","[]");
                Gson gson=new Gson();
                try{
                    final List<RequestComponentPostEntity> componentList=gson.fromJson(jsonString,new TypeToken<List<RequestComponentPostEntity>>(){}.getType());
                    if (componentList.size()==0){
                        CommonFunctions.toastString("Select atleast a single item to raise request",activityContext);
                    }else{
                        alert.setTitle("Are you sure?").setMessage(" Please Double Check all the items you need once the request is raised, It cannot be undone\n\n Please wait for the seniors to approve your request.").setPositiveButton("RAISE REQUEST", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PostComponentRequestApiEntity entity=new PostComponentRequestApiEntity();
                                entity.setRequestedComponents(componentList);
                                //call rest
                                raiseRequestLoader.setVisibility(View.VISIBLE);
                                raiseRequestRecyclerView.setVisibility(View.GONE);
                                RestClientImplementation.postComponentRequest(entity, new PostComponentRequestApiEntity.RCCRCI() {
                                    @Override
                                    public void onSubmitComponentRequest(ErrorEntity errorEntity, VolleyError error) {
                                        if(error==null){
                                            CommonFunctions.toastString(errorEntity.getMessage(),activityContext);
                                            RaiseRequestFragment fragment=new RaiseRequestFragment();
                                            getFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
                                        }else{
                                            raiseRequestLoader.setVisibility(View.GONE);
                                            raiseRequestLoader.setVisibility(View.VISIBLE);
                                            CommonFunctions.toastString("Oops! Try Again",activityContext);
                                        }
                                    }
                                },activityContext);
                            }
                        }).setNegativeButton("RECHECK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setCancelable(false).show();
                    }
                }catch (Exception e){
                    CommonFunctions.toastString("Something went wrong! Try Again",activityContext);
                }
            }
        });
        return v;
    }
}
