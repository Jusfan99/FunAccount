package com.example.funaccount.setting_page;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.funaccount.R;
import com.example.funaccount.util.SettingItem;
import com.example.funaccount.util.SettingItemAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingFragment extends Fragment {

    private String[] title = {"预算中心","高级功能","其他设置","账号设置","常见问题","好评鼓励","关于我们"};
    private int[] images = {R.drawable.setting4,R.drawable.setting2,R.drawable.setting1,R.drawable.setting3,R.drawable.setting5,R.drawable.setting6,R.drawable.setting7};
    private RecyclerView recyclerView;
    private SettingItemAdapter settingItemAdapter;
    private ArrayList<SettingItem> settingItems = new ArrayList<SettingItem>();

    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_frag,container,false);
        initRecyclerView(view);
        initData();
        return view;
    }

    private void initRecyclerView(View view){
        recyclerView = view.findViewById(R.id.setting_recycleview);
        settingItemAdapter = new SettingItemAdapter(getActivity(),settingItems);
        recyclerView.setAdapter(settingItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //待更改
    }
    private void initData(){
        for(int i = 0; i < 7; i++){
            SettingItem item = new SettingItem(images[i],title[i]);
            settingItems.add(item);
        }
    }
}