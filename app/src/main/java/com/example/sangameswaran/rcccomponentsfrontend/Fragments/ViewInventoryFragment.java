package com.example.sangameswaran.rcccomponentsfrontend.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Adapters.ViewInventoryAdapter;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ErrorEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.InventoryComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ViewInventoryAPIEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 01-05-2018.
 */

public class ViewInventoryFragment extends Fragment {
    RecyclerView RvViewInventory;
    ProgressBar viewInventoryLoader;
    RecyclerView.LayoutManager manager;
    RecyclerView.Adapter adapter;
    Context activityContext;
    Button btnAddItem;
    List<InventoryComponentEntity> components;
    AlertDialog.Builder addComponentDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_inventory_fragment,container,false);
        RvViewInventory=(RecyclerView)v.findViewById(R.id.RvViewInventory);
        viewInventoryLoader=(ProgressBar)v.findViewById(R.id.viewInventoryLoader);
        btnAddItem=(Button)v.findViewById(R.id.btnAddItem);
        addComponentDialog=new AlertDialog.Builder(getContext());
        activityContext = getContext();
        components=new ArrayList<>();
        manager=new LinearLayoutManager(getContext());
        RestClientImplementation.getInventoryApi(new ViewInventoryAPIEntity.RCCRCI() {
            @Override
            public void onGetInventoryDetails(ViewInventoryAPIEntity entity, VolleyError error) {
                if(error==null){
                    viewInventoryLoader.setVisibility(View.GONE);
                    components=entity.getMessage();
                    adapter=new ViewInventoryAdapter(components,activityContext);
                    RvViewInventory.setAdapter(adapter);
                    RvViewInventory.setLayoutManager(manager);
                    adapter.notifyDataSetChanged();
                }else{
                    //do nothing
                }
            }
        },activityContext);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater dialogInflator= (LayoutInflater) activityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView=dialogInflator.inflate(R.layout.add_inventory_view,null,false);
                final EditText etComponentName=(EditText)dialogView.findViewById(R.id.etComponentName);
                final EditText etCurrentAvailableCount=(EditText)dialogView.findViewById(R.id.etCurrentAvailableCount);
                final EditText etRoughPrice=(EditText)dialogView.findViewById(R.id.etRoughPrice);
                addComponentDialog.setView(dialogView);
                addComponentDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String etCN=etComponentName.getText().toString();
                        String etCAC=etCurrentAvailableCount.getText().toString();
                        String etR=etRoughPrice.getText().toString();
                        if(etCN.equals("")||etCAC.equals("")||etR.equals("")){
                            CommonFunctions.toastString("Enter All the fields",activityContext);
                        }else{
                            InventoryComponentEntity entity=new InventoryComponentEntity();
                            try {
                                entity.setComponent_name(etCN);
                                entity.setCurrent_available_count(Integer.parseInt(etCAC));
                                entity.setRough_price(etR+"");
                                entity.setTotal_count(Integer.parseInt(etCAC));
                                RestClientImplementation.addComponentApi(entity, new InventoryComponentEntity.RCCRCI() {
                                    @Override
                                    public void onResult(ErrorEntity errorEntity, VolleyError error) {
                                        if (error ==null){
                                            CommonFunctions.toastString(errorEntity.getMessage(),activityContext);
                                        }else {
                                        }
                                    }
                                },activityContext);

                            }catch (Exception e){
                                CommonFunctions.toastString("Something went wrong.",activityContext);
                            }

                        }
                    }
                });
                addComponentDialog.setNegativeButton("CANCEL",null);
                addComponentDialog.show();
            }
        });
        return v;
    }
}
