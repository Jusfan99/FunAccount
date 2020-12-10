package com.example.funaccount.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.funaccount.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SettingItemAdapter extends RecyclerView.Adapter<SettingItemAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<SettingItem> settingItems;

    public SettingItemAdapter(Context context,ArrayList<SettingItem> settingItems){
        this.context = context;
        this.settingItems = settingItems;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context,R.layout.setting_item,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SettingItem data = settingItems.get(position);
        holder.settingName.setText(data.itemName);
        holder.imageView.setImageResource(data.imgId);
    }

    @Override
    public int getItemCount() {
        return settingItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView settingName;
        public ImageView imageView;
        public MyViewHolder(View itemView){
            super(itemView);
            settingName = (TextView)itemView.findViewById(R.id.setting_name);
            imageView = (ImageView)itemView.findViewById(R.id.setting_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //暂时不写
                }
            });
        }

    }



}
