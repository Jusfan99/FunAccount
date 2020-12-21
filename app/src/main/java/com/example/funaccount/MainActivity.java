package com.example.funaccount;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.funaccount.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private String mUserName;
    private String mUserId;
    private int mLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userName");
        mUserId = intent.getStringExtra("userId");
        mLoginStatus = intent.getIntExtra("status",0);
        //隐藏标题
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        NavController navController = Navigation.findNavController(this,R.id.main_nav_host);
        BottomNavigationView navigationView = findViewById(R.id.main_nav_view);
        NavigationUI.setupWithNavController(navigationView,navController);
    }
    public String getUserName(){
        return mUserName;
    }
    public String getUserId(){
        return mUserId;
    }
    public int getLoginStatus(){
        return mLoginStatus;
    }
}