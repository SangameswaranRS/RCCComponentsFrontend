package com.example.sangameswaran.rcccomponentsfrontend.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sangameswaran.rcccomponentsfrontend.Entities.ComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.InventoryComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;

import java.util.List;

/**
 * Created by Sangameswaran on 02-05-2018.
 */

public class ViewInventoryAdapter extends RecyclerView.Adapter<ViewInventoryAdapter.ViewInventoryVH>{

    List<InventoryComponentEntity> componentEntities;
    Context activityContext;
    public ViewInventoryAdapter(List<InventoryComponentEntity> componentEntities,Context context){
        activityContext=context;
        this.componentEntities=componentEntities;
    }
    @Override
    public ViewInventoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_inventory_card_layout,parent,false);
        return new ViewInventoryVH(v);
    }

    @Override
    public void onBindViewHolder(ViewInventoryVH holder, int position) {
        holder.t1.setText(componentEntities.get(position).getComponent_name());
        holder.t2.setText("Current Available Count: "+componentEntities.get(position).getCurrent_available_count());
        holder.t3.setText("Total Count: "+componentEntities.get(position).getTotal_count());
        holder.t4.setText("Rough price: "+componentEntities.get(position).getRough_price());
    }

    @Override
    public int getItemCount() {
        return componentEntities.size();
    }
    public class ViewInventoryVH extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t4;
        public ViewInventoryVH(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.t1);
            t2=(TextView)itemView.findViewById(R.id.t2);
            t3=(TextView)itemView.findViewById(R.id.t3);
            t4=(TextView)itemView.findViewById(R.id.t4);

        }
    }
}
