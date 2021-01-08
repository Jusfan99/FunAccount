package com.example.funaccount.other_pages.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.setting_page.SettingFragment;
import com.example.funaccount.util.UserData;
import com.example.funaccount.util.UserDataViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class UserActivity extends AppCompatActivity {
    private TextView mResponseId;
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        Button mReturnButton = (Button) findViewById(R.id.returnback);

        UserDataViewModel model = new ViewModelProvider(this).get(UserDataViewModel.class);
        model.getUser(this).observe(this, userData -> {
            mResponseId = findViewById(R.id.response_id);
            String userId = "id:" + userData.getUserId() + " name:" + userData.getUserName();
            mResponseId.setText(userId);
            mUserData = userData;
        });
    }

    public void backToLogin(View view) {
        Intent intent3 = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent3);
        finish();
    }

    public void backToSetting(View view) {
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        intent.putExtra("userName", mUserData.getUserName());
        intent.putExtra("userId", mUserData.getUserId());
        intent.putExtra("status", 1);
        startActivity(intent);
        finish();
    }
}