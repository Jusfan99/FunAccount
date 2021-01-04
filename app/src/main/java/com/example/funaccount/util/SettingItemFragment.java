package com.example.funaccount.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.funaccount.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingItemFragment extends Fragment {
    String[] mTitle = {"预算中心", "高级功能", "其他设置", "账号设置", "常见问题", "好评鼓励", "关于我们"};
    int[] mImages = {R.drawable.setting4, R.drawable.setting2, R.drawable.setting1, R.drawable.setting3, R.drawable.setting5, R.drawable.setting6, R.drawable.setting7};
    public static class SettingItemAdapter extends RecyclerView.Adapter<SettingItemAdapter.MyViewHolder> {
        private final Context mContext;
        private final ArrayList<SettingItem> mSettingItems;
        public SettingItemAdapter(Context context, ArrayList<SettingItem> settingItems) {
            this.mContext = context;
            this.mSettingItems = settingItems;
        }
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mItemName;
            public ImageView mItemImage;
            public MyViewHolder(View itemView) {
                super(itemView);
                mItemName = (TextView)itemView.findViewById(R.id.setting_name);
                mItemImage = (ImageView)itemView.findViewById(R.id.setting_image);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //可以在这写监听事件
                    }
                });
            }
        }

        //设置item的监听接口
        public interface OnItemClickListener {
            //点击每一项的实现方法
            public void OnItemClick(View view, SettingItem settingItem);
        }
        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = View.inflate(mContext,R.layout.setting_item,null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            SettingItem data = mSettingItems.get(position);
            holder.mItemName.setText(data.mItemName);
            holder.mItemImage.setImageResource(data.mImagId);
        }

        @Override
        public int getItemCount() {
            return mSettingItems.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public ArrayList<SettingItem> initData(){
        ArrayList<SettingItem> settingItems = new ArrayList<SettingItem>();
        for(int i = 0; i < 7; i++){
            SettingItem item = new SettingItem(mImages[i], mTitle[i]);
            settingItems.add(item);
        }
        return settingItems;
    }
    public void initRecyclerView(View view,SettingItemAdapter settingItemAdapter){
        RecyclerView recyclerView = view.findViewById(R.id.setting_recycleview);
        recyclerView.setAdapter(settingItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    public void initListener(SettingItemAdapter settingItemAdapter){
        settingItemAdapter.setOnItemClickListener(new SettingItemAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view, SettingItem settingItem) {
                //监听事件
            }
        });
    }
}
