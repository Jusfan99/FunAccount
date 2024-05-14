package com.example.funaccount;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.funaccount.bill_page.AddOneFragment;
import com.example.funaccount.util.BillShowHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
  private String mUserName;
  private String mUserId;
  private boolean mLoginStatus;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bmob.initialize(this, "232c7eca04f00e567ab848ecaf33b19e");
    setContentView(R.layout.activity_main);
    getWindow().setStatusBarColor(getResources().getColor(R.color.test));
    Intent intent = getIntent();
    mUserName = intent.getStringExtra("userName");
    mUserId = intent.getStringExtra("userId");
    mLoginStatus = intent.getBooleanExtra("status", false);
    //隐藏标题
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    NavController navController = Navigation.findNavController(this, R.id.main_nav_host);
    BottomNavigationView navigationView = findViewById(R.id.main_nav_view);
    NavigationUI.setupWithNavController(navigationView, navController);
  }

  @Override
  protected void onResume() {
    mLoginStatus = getIntent().getBooleanExtra("status", false);
    super.onResume();
  }

  public String getUserName() {
    return mUserName;
  }

  public String getUserId() {
    return mUserId;
  }

  public boolean getLoginStatus() {
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