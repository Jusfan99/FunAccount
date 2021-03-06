package com.example.funaccount.setting_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.other_pages.login_page.LoginActivity;
import com.example.funaccount.util.SettingItemFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingFragment extends SettingItemFragment {
  private String mUserName;
  private String mUSerId;

  public boolean mIsLogin;
  public SettingItemAdapter mSettingItemAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.setting_frag, null);
    mSettingItemAdapter = new SettingItemAdapter(getActivity(), initData());
    initRecyclerView(view, mSettingItemAdapter);
    TextView login = view.findViewById(R.id.user_name);
    TextView mId = view.findViewById(R.id.user_id);
    if (!mIsLogin) {
      login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(getActivity(), LoginActivity.class);
          startActivity(intent);
        }
      });
    } else {
      login.setText(mUserName);
      mId.setText(mUSerId);
    }
    return view;
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mUSerId = ((MainActivity) context).getUserId();
    mUserName = ((MainActivity) context).getUserName();
    mIsLogin = ((MainActivity) context).getLoginStatus();
  }
}