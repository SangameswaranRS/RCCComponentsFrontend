package com.example.sangameswaran.rcccomponentsfrontend.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sangameswaran.rcccomponentsfrontend.Constants.CommonFunctions;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.ComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.InventoryComponentEntity;
import com.example.sangameswaran.rcccomponentsfrontend.Entities.RequestComponentPostEntity;
import com.example.sangameswaran.rcccomponentsfrontend.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sangameswaran on 30-04-2018.
 */

public class RaiseRequestRVAdapter extends RecyclerView.Adapter<RaiseRequestRVAdapter.RaiseRequestRVHolder> {

    List<InventoryComponentEntity> components;
    Map<Integer,Integer> countOfComponents = new HashMap<>();
    Context activityContext;
    public RaiseRequestRVAdapter(List<InventoryComponentEntity> componentEntities, Context activityContext){
        this.components=componentEntities;
        this.activityContext=activityContext;
        SharedPreferences sp=activityContext.getSharedPreferences("HASHMAP",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("MAP","[]");
        editor.commit();
    }
    @Override
    public RaiseRequestRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raise_request_rv_card_layout,parent,false);
        return new RaiseRequestRVHolder(view);
    }

    @Override
    public void onBindViewHolder(final RaiseRequestRVHolder holder, final int position) {
        holder.tvComponentName.setText(components.get(position).getComponent_name());
        holder.tvItemCount.setText("0");
        holder.IvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.tvItemCount.getText().toString());
                if(count == components.get(position).getCurrent_available_count())
                    CommonFunctions.toastString("Not Available",activityContext);
                else {
                    count++;
                    countOfComponents.put(components.get(position).getCid(),count);
                    writeMap(countOfComponents);
                }
                holder.tvItemCount.setText(""+count);
            }
        });
        holder.IvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=Integer.parseInt(holder.tvItemCount.getText().toString());
                if(count==0){
                    countOfComponents.remove(components.get(position).getCid());
                    writeMap(countOfComponents);
                }else{
                    count--;
                    countOfComponents.put(components.get(position).getCid(),count);
                    writeMap(countOfComponents);
                    if(count==0){
                        countOfComponents.remove(components.get(position).getCid());
                        writeMap(countOfComponents);
                    }
                    holder.tvItemCount.setText(""+count);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return components.size();
    }
    public class RaiseRequestRVHolder extends RecyclerView.ViewHolder {
        ImageView IvPlus,IvMinus;
        TextView tvComponentName,tvItemCount;
        public RaiseRequestRVHolder(View itemView) {
            super(itemView);
            IvPlus=(ImageView)itemView.findViewById(R.id.IvPlus);
            IvMinus=(ImageView)itemView.findViewById(R.id.IvMinus);
            tvComponentName=(TextView)itemView.findViewById(R.id.tvComponentname);
            tvItemCount=(TextView)itemView.findViewById(R.id.tvItemCount);
        }
    }

    public void writeMap(Map<Integer,Integer> map){
        SharedPreferences sp=activityContext.getSharedPreferences("HASHMAP",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        Gson gson=new Gson();
        List<RequestComponentPostEntity> componentList=new ArrayList<>();
        for(Map.Entry m:map.entrySet()){
            RequestComponentPostEntity entity=new RequestComponentPostEntity();
            entity.setCid((Integer) m.getKey());
            entity.setCount((Integer) m.getValue());
            componentList.add(entity);
        }
        String jsonString=gson.toJson(componentList);
        editor.putString("MAP",jsonString);
        editor.commit();
    }
}
