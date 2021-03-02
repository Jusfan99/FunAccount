package com.example.funaccount;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import cn.bmob.v3.Bmob;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.funaccount.bill_page.AddOneFragment;
import com.example.funaccount.util.BillShowHelper;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String mUserName;
    private String mUserId;
    private int mLoginStatus;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "dd84103ea5c4f10cf1777f78c54580ad");
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.test));
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("userName");
        mUserId = intent.getStringExtra("userId");
        mLoginStatus = intent.getIntExtra("status", 0);
        //隐藏标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        NavController navController = Navigation.findNavController(this, R.id.main_nav_host);
        BottomNavigationView navigationView = findViewById(R.id.main_nav_view);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserId() {
        return mUserId;
    }

    public int getLoginStatus() {
        return mLoginStatus;
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof AddOneFragment) {
                BillShowHelper.addingOne = false;
                break;
            }
        }
        super.onBackPressed();
    }
}