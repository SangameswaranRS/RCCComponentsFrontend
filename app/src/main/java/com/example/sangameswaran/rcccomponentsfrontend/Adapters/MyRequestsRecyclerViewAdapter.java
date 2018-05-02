package com.example.sangameswaran.rcccomponentsfrontend.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.GetComponentApiEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.example.sangameswaran.rcccomponentsfrontend.RestCalls.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sangameswaran on 29-04-2018.
 */

public class MyRequestsRecyclerViewAdapter extends RecyclerView.Adapter<MyRequestsRecyclerViewAdapter.MyRequestsRecyclerViewHolder> {
    List<RequestEntity> myRequests=new ArrayList<>();
    Context context;
    public MyRequestsRecyclerViewAdapter(List<RequestEntity> requestEntities,Context activityContext){
        myRequests=requestEntities;
        context=activityContext;
    }
    @Override
    public MyRequestsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_card_layout,parent,false);
        return new MyRequestsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRequestsRecyclerViewHolder holder, int position) {
            holder.t1.setText(""+myRequests.get(position).getRstatus());
            holder.t2.setText("RequestId:"+myRequests.get(position).getRid());
            holder.t3.setText("Loading Component Info..");
        if(myRequests.get(position).getRstatus().equals("REQUESTED")){
            holder.t1.setTextColor(Color.parseColor("#ff0000"));
        }else if(myRequests.get(position).getRstatus().equals("GRANTED")){
            holder.t1.setTextColor(Color.parseColor("#64a844"));
        }else{
            holder.t1.setTextColor(Color.parseColor("#0000ff"));
        }
        RestClientImplementation.getComponentsForRid(myRequests.get(position).getRid(), new GetComponentApiEntity.RCCRCI() {
            @Override
            public void onGetComponentInfo(GetComponentApiEntity entity, VolleyError error) {
                if(error==null){
                    List<ComponentEntity> componentEntities=entity.getMessage();
                    String componentString="";
                    for(ComponentEntity e : componentEntities){
                        componentString+=e.getComponent_name()+" - "+e.getCount()+"\n";
                    }
                    holder.t3.setText(componentString);
                }else{
                    holder.t3.setText("Oops! Something went wrong");
                }
            }
        },context);
    }

    @Override
    public int getItemCount() {
        return myRequests.size();
    }
    public class MyRequestsRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3;
        public MyRequestsRecyclerViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.tv1);
            t2=(TextView)itemView.findViewById(R.id.tv2);
            t3=(TextView)itemView.findViewById(R.id.tv3);
        }
    }
}
