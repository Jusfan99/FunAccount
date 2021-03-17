package com.example.funaccount.other_pages.login_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.funaccount.MainActivity;
import com.example.funaccount.R;
import com.example.funaccount.util.UserData;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserActivity extends AppCompatActivity {
    private TextView mResponseId;
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        Button mReturnButton = (Button) findViewById(R.id.returnback);
        mResponseId = findViewById(R.id.response_id);

        Intent intent = getIntent();
        mUserData = new UserData();
        mUserData.setUserName(intent.getStringExtra("userName"));
        BmobQuery<UserData> bmobQuery = new BmobQuery<UserData>();
        bmobQuery.addWhereEqualTo("userName", mUserData.getUserName());
        bmobQuery.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                for (UserData userData : list) {
                    mUserData = userData;
                    String showUserMsg = "id:" + userData.getUserId() + " name:" + userData.getUserName();
                    mResponseId.setText(showUserMsg);
                }
            }
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
        intent.putExtra("status", true);
        intent.putExtra("userId", mUserData.getUserId());
        startActivity(intent);
        finish();
    }
}