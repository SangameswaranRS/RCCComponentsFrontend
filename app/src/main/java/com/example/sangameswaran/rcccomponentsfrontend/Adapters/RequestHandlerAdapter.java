package com.example.sangameswaran.rcccomponentsfrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ErrorEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetComponentApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.UpdateRequestStatusEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Fragments.RequestApproveFragment;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class RequestHandlerAdapter extends RecyclerView.Adapter<RequestHandlerAdapter.ViewHolder> {
    List<RequestEntity> allRequests=new ArrayList<>();
    Context context;
    RequestApproveFragment baseFragment;
    public RequestHandlerAdapter(List<RequestEntity> allRequests,Context activity,RequestApproveFragment fragment){
        this.allRequests=allRequests;
        this.context=activity;
        this.baseFragment = fragment;
    }

    @Override
    public RequestHandlerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_approve_fragment_card_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RequestHandlerAdapter.ViewHolder holder, final int position) {
        holder.tex1.setText(""+allRequests.get(position).getRstatus());
        holder.tex2.setText("RequestId:"+allRequests.get(position).getRid()+"\n"+"Username:"+allRequests.get(position).getUname());
        holder.tex3.setText("Loading Component Info..");
        if(allRequests.get(position).getRstatus().equals("REQUESTED")){
            holder.tex1.setTextColor(Color.parseColor("#dcdc39"));
        }else if(allRequests.get(position).getRstatus().equals("GRANTED")){
            holder.tex1.setTextColor(Color.parseColor("#64a844"));
        }else if (allRequests.get(position).getRstatus().equals("REJECTED")){
            holder.tex1.setTextColor(Color.parseColor("#ff0000"));
        }else{
            holder.tex1.setTextColor(Color.parseColor("#0000ff"));
        }
        RestClientImplementation.getComponentsForRid(allRequests.get(position).getRid(), new GetComponentApiEntity.RCCRCI() {
            @Override
            public void onGetComponentInfo(GetComponentApiEntity entity, VolleyError error) {
                if(error==null){
                    List<ComponentEntity> componentEntities=entity.getMessage();
                    String componentString="";
                    for(ComponentEntity e : componentEntities){
                        componentString+=e.getComponent_name()+" - "+e.getCount()+"\n";
                    }
                    holder.tex3.setText(componentString);
                }else{
                    holder.tex3.setText("Oops! Something went wrong");
                }
            }
        },context);
        holder.callUserIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunctions.toastString("Calling user",context);
            }
        });
        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allRequests.get(position).getRstatus().equals("REQUESTED")){
                    //CommonFunctions.toastString("Approving Request",context);
                    baseFragment.requestApproveFragmentLoader.setVisibility(View.VISIBLE);
                    baseFragment.rvRequestApproval.setVisibility(View.GONE);
                    RestClientImplementation.grantRequestAPI(allRequests.get(position).getRid(), new ErrorEntity.RCCRCI() {
                        @Override
                        public void onGetMessage(ErrorEntity errorEntity, VolleyError error) {
                            if(error==null){
                                CommonFunctions.toastString(errorEntity.getMessage(),context);
                                holder.tex1.setText("GRANTED");
                                holder.tex1.setTextColor(Color.parseColor("#64a844"));
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                            }else{
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                            }
                        }
                    },context);
                }else{
                    CommonFunctions.toastString("You cannot approve a request which as already been accepted/Terminated",context);
                }
            }
        });
        holder.returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allRequests.get(position).getRstatus().equals("GRANTED")){
                    //CommonFunctions.toastString("Returning Components",context);
                    baseFragment.requestApproveFragmentLoader.setVisibility(View.VISIBLE);
                    baseFragment.rvRequestApproval.setVisibility(View.GONE);
                    RestClientImplementation.returnRequestAPI(allRequests.get(position).getRid(), new ErrorEntity.RCCRCI() {
                        @Override
                        public void onGetMessage(ErrorEntity errorEntity, VolleyError error) {
                            if(error==null){
                                CommonFunctions.toastString(errorEntity.getMessage(),context);
                                holder.tex1.setText("RETURNED");
                                holder.tex1.setTextColor(Color.parseColor("#0000ff"));
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                            }else{
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                            }
                        }
                    },context);
                }else{
                    CommonFunctions.toastString("Invalid Action",context);
                }
            }
        });
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allRequests.get(position).getRstatus().equals("REQUESTED")) {
                    UpdateRequestStatusEntity entity = new UpdateRequestStatusEntity();
                    entity.setRid(allRequests.get(position).getRid());
                    entity.setRstatus("REJECTED");
                    baseFragment.requestApproveFragmentLoader.setVisibility(View.VISIBLE);
                    baseFragment.rvRequestApproval.setVisibility(View.GONE);
                    RestClientImplementation.rejectAPI(entity, new ErrorEntity.RCCRCI() {
                        @Override
                        public void onGetMessage(ErrorEntity errorEntity, VolleyError error) {
                            if (error == null) {
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                                CommonFunctions.toastString("Request Rejected", context);
                                holder.tex1.setText("REJECTED");
                                holder.tex1.setTextColor(Color.parseColor("#ff0000"));
                            }else{
                                baseFragment.rvRequestApproval.setVisibility(View.VISIBLE);
                                baseFragment.requestApproveFragmentLoader.setVisibility(View.GONE);
                            }
                        }
                    }, context);
                }else{
                    CommonFunctions.toastString("A Request can't be rejected in this status",context);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allRequests.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tex1,tex2,tex3;
        Button approveBtn,returnBtn,rejectBtn;
        ImageView callUserIv;
        public ViewHolder(View itemView) {
            super(itemView);
            tex1=(TextView)itemView.findViewById(R.id.tex1);
            tex2=(TextView)itemView.findViewById(R.id.tex2);
            tex3=(TextView)itemView.findViewById(R.id.tex3);
            approveBtn=(Button)itemView.findViewById(R.id.approveBtn);
            returnBtn=(Button)itemView.findViewById(R.id.returnBtn);
            rejectBtn=(Button)itemView.findViewById(R.id.terminateBtn);
            callUserIv=(ImageView)itemView.findViewById(R.id.callUserIv);
        }
    }
}
