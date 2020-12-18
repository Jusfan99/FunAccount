package com.example.funaccount.setting_page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funaccount.R;
import com.example.funaccount.other_pages.login_page.LoginActivity;
import com.example.funaccount.util.SettingItemFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingFragment extends SettingItemFragment {
    private SharedPreferences mLoginSp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_frag,null);

        SettingItemAdapter settingItemAdapter = new SettingItemAdapter(getActivity(), initData());
        initRecyclerView(view,settingItemAdapter);

        TextView login = view.findViewById(R.id.user_name);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                updateUserInfo(view.findViewById(R.id.user_name));
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void updateUserInfo(TextView userName){
        mLoginSp = getActivity().getSharedPreferences("userInfo", 0);
        String name= mLoginSp.getString("USER_NAME", "");
        userName.setText(name);
    }
}